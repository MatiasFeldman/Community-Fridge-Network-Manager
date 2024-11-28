package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dtos.direccion.DireccionInputDTO;
import ar.edu.utn.frba.dds.dtos.humanos.UsuarioHumanoOutputDTO;
import ar.edu.utn.frba.dds.dtos.juridico.JuridicaOutpuDTO;
import ar.edu.utn.frba.dds.dtos.juridico.UsuarioJuridicaOutputDTO;
import ar.edu.utn.frba.dds.exceptions.SolicitudIncorrectaException;
import ar.edu.utn.frba.dds.exceptions.login.ContraseniaIncorrectaException;
import ar.edu.utn.frba.dds.exceptions.login.UsuarioIncorrectoException;
import ar.edu.utn.frba.dds.models.entities.personas.AtributoHumanoRespondido;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.entities.personas.Contacto;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.factories.direcciones.DireccionFactory;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.juridicas.JuridicasRepository;
import ar.edu.utn.frba.dds.models.repositories.usuarios.UsuariosRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import ar.edu.utn.frba.dds.utils.RenderUtils;
import ar.edu.utn.frba.dds.utils.seguridad.HashPassword;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

public class UsuariosController {
    public void handleLogin(Context ctx) {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");

        UsuariosRepository usuariosRepository = ServiceLocator.instanceOf(UsuariosRepository.class);
        Optional<Usuario> user = usuariosRepository.buscarPorUsername(username);

        System.out.println("Usuario: " + user.get().getUser());

        if (user.isPresent()) {
            HashPassword hash = ServiceLocator.instanceOf(HashPassword.class);
            String passwordHashed = hash.hashPassword(password);
            Usuario usuarioEncontrado = user.get();
            if (usuarioEncontrado.getPassword().equals(passwordHashed)) {
                ctx.sessionAttribute("user", usuarioEncontrado.getId());

                List<String> nombresRoles = Optional.ofNullable(usuarioEncontrado.getRoles())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(Enum::name)  // Convierte cada enum Rol a su nombre de cadena
                        .collect(Collectors.toList());

                // Guardar roles en la sesión
                ctx.sessionAttribute("roles", nombresRoles); // me guardo los roles del usuario

                // Guardo el id en la sesion
                ctx.sessionAttribute("id", usuarioEncontrado.getId());

                String originalUrl = ctx.sessionAttribute("originalUrl");
                if (originalUrl != null) {
                    ctx.sessionAttribute("originalUrl", null);
                    ctx.redirect(originalUrl);
                } else {
                    ctx.redirect("/");
                }
            } else {
                throw new ContraseniaIncorrectaException("La contraseña es incorrecta");
            }
        } else {
            throw new UsuarioIncorrectoException("El usuario no existe");
        }
        String nombreUsuario = user.get().getUser();
        String rolUsuario = String.valueOf(user.get().getRoles().get(0)); // Si deseas obtener roles personalizados
        String fotoUsuario = user.get().getFoto();
        System.out.print(nombreUsuario);

        // Guardar la información del usuario en el contexto para usar en las vistas
        ctx.sessionAttribute("nombreUsuario", nombreUsuario);
        ctx.sessionAttribute("rolUsuario", rolUsuario);
        ctx.sessionAttribute("fotoUsuario", fotoUsuario);
    }

    public void handleLogout(Context ctx) {
        ctx.req().getSession().invalidate();
        ctx.redirect("/login");
    }

    public void handlePerfil(Context ctx) {
        UsuariosRepository usuariosRepository = ServiceLocator.instanceOf(UsuariosRepository.class);

        Long id = ctx.sessionAttribute("user");
        if (id == null) {
            ctx.redirect("/login");
            return;
        }

        List<String> roles = ctx.sessionAttribute("roles");
        if (roles == null) {
            roles = new ArrayList<>(); // O manejar de otra forma si roles es null
        }

        Optional<Usuario> usuario = usuariosRepository.buscarPorId(id);

        if (usuario.isPresent()) {
            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Perfil");
            model.put("roles", roles);
            model.put("id", id); // Si no quieres mostrar el id, puedes quitar esta línea
            model.put("usuario", usuario.get().getUser());

            if (roles.contains("ADMIN")) {
                model.put("esAdmin", true);
            } else if (roles.contains("HUMANO")) {
                ColaboradorHumano humano = ServiceLocator.instanceOf(HumanosRepository.class).buscarPorIdUsuario(id).get();
                model.put("puntos", humano.calcularPuntaje());
                model.put("humano", humano.getAllAtributos());
            } else if (roles.contains("JURIDICA")) {
                Juridica juridica = ServiceLocator.instanceOf(JuridicasRepository.class).buscarPorIdUsuario(id).get();
                JuridicaOutpuDTO dto = JuridicaOutpuDTO.of(juridica);
                model.put("puntos", juridica.calcularPuntaje());
                model.put("juridica", dto);
                model.put("esJuridica", true);
            }

            RenderUtils.renderizar(ctx, "perfil.hbs", model);
        } else {
            ctx.redirect("/login");
        }
    }


    public void handleUpdate(Context ctx) {
        UsuariosRepository usuariosRepository = ServiceLocator.instanceOf(UsuariosRepository.class);
        Long id = ctx.sessionAttribute("user");
        List<String> roles = ctx.sessionAttribute("roles");
        Optional<Usuario> usuario = usuariosRepository.buscarPorId(id);

        if (usuario.isPresent()) {
            if (roles.contains("HUMANO")) {
                Optional<ColaboradorHumano> humano = ServiceLocator.instanceOf(HumanosRepository.class).buscarPorIdUsuario(id);
                List<AtributoHumanoRespondido> atributos = humano.get().getAllAtributos();
                String direccionValor = null;
                String provinciaValor = null;
                // Iterar sobre los atributos y asignarles el valor correspondiente
                for (AtributoHumanoRespondido atributoRespondido : atributos) {
                    String nombreAtributo = atributoRespondido.getNombreAtributo();

                    String nuevoValor = ctx.formParam(nombreAtributo);

                    if (nuevoValor != null && !nuevoValor.isEmpty()) {
                        atributoRespondido.setValor(nuevoValor);
                    }
                    // Verificar si el atributo es "direccion" o "provincia"
                    if ("direccion".equalsIgnoreCase(nombreAtributo)) {
                        direccionValor = nuevoValor;
                    }
                    if ("provincia".equalsIgnoreCase(nombreAtributo)) {
                        provinciaValor = nuevoValor;
                    }
                }
                if (direccionValor != null && !direccionValor.isEmpty() && provinciaValor != null && !provinciaValor.isEmpty()) {
                    Direccion direccion = DireccionFactory.create(new DireccionInputDTO(direccionValor, provinciaValor));
                    humano.get().setDireccion(direccion);
                } else {
                    humano.get().setDireccion(null);
                }
                ServiceLocator.instanceOf(HumanosRepository.class).actualizar(humano.get());


            } else if (roles.contains("JURIDICA")) {
                String direccion = ctx.formParam("direccion");
                String provincia = ctx.formParam("provincia");
                String mail = ctx.formParam("mail");
                String telegram = ctx.formParam("telegram");
                String whatsapp = ctx.formParam("whatsapp");
                List<Contacto> mediosDeContacto = new ArrayList<>();

                Juridica juridica = ServiceLocator.instanceOf(JuridicasRepository.class).buscarPorIdUsuario(id).get();
                if (direccion != null && provincia != null) {
                    juridica.setDireccion(DireccionFactory.create(new DireccionInputDTO(direccion, provincia)));
                } else {
                    juridica.setDireccion(null);
                }
                if (!mail.isEmpty()) mediosDeContacto.add(Contacto.of("Mail", mail));
                if (!telegram.isEmpty()) mediosDeContacto.add(Contacto.of("Telegram", telegram));
                if (!whatsapp.isEmpty()) mediosDeContacto.add(Contacto.of("WhatsApp", whatsapp));
                juridica.setMediosDeContacto(mediosDeContacto);
                ServiceLocator.instanceOf(JuridicasRepository.class).modificar(juridica);
            }

            UploadedFile archivoImagen = ctx.uploadedFile("imagen");

            if (archivoImagen != null && archivoImagen.size() > 0) {
                // Definir la carpeta donde se guardará la imagen
                String nombreArchivo = id.toString() + "_" + "perfil";
                String directorioCompleto = Paths.get("src", "main", "java", "ar", "edu", "utn", "frba", "dds", "imagenesDinamicas", "fotosUsuarios", nombreArchivo).toString();

                if (usuario.get().getFoto() != null && !usuario.get().getFoto().isEmpty()) {
                    String foto = usuario.get().getFoto(); // Obtiene la ruta de la foto
                    String nombreArchivoU = foto.replaceFirst("/imagenes/", ""); // Elimina el prefijo "/imagenes/"
                    String rutaFotoAnterior = Paths.get("src", "main", "java", "ar", "edu", "utn", "frba", "dds", "imagenesDinamicas", nombreArchivoU).toString();

                    // Verificar si la foto anterior existe y borrarla
                    File fotoAnterior = new File(rutaFotoAnterior);
                    if (fotoAnterior.exists() && !fotoAnterior.getName().equalsIgnoreCase("user.png")) {
                        if (fotoAnterior.delete()) {
                            System.out.println("Foto anterior borrada correctamente.");
                        } else {
                            System.out.println("No se pudo borrar la foto anterior.");
                        }
                    }
                }

                try (InputStream inputStream = archivoImagen.content()) {
                    Files.copy(inputStream, Paths.get(directorioCompleto), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new SolicitudIncorrectaException();
                }

                usuario.get().setFoto("/imagenes/fotosUsuarios/" + nombreArchivo); // Guardar la ruta de la imagen en el usuario
                ctx.sessionAttribute("fotoUsuario", usuario.get().getFoto());
                ServiceLocator.instanceOf(UsuariosRepository.class).modificar(usuario.get()); // Actualizar el usuario en la base de datos

            }

            ctx.redirect("/perfil");
        }
    }

    public void showUsuarios(Context ctx) {
        HumanosRepository humanosRepository = ServiceLocator.instanceOf(HumanosRepository.class);
        List<ColaboradorHumano> humanosR = humanosRepository.buscarTodos();

        List<UsuarioHumanoOutputDTO> humanos = new ArrayList<>();

        for (ColaboradorHumano humano : humanosR) {
            humanos.add(UsuarioHumanoOutputDTO.of(humano));
        }

        JuridicasRepository juridicasRepository = ServiceLocator.instanceOf(JuridicasRepository.class);
        List<Juridica> juridicasR = juridicasRepository.buscarTodos();

        List<UsuarioJuridicaOutputDTO> juridicas = new ArrayList<>();

        for (Juridica juridica : juridicasR) {
            juridicas.add(UsuarioJuridicaOutputDTO.of(juridica));
        }

        Map<String, Object> model = new HashMap<>();
        model.put("humanos", humanos);
        model.put("juridicas", juridicas);
        RenderUtils.renderizar(ctx, "colaboradores.hbs", model);
    }
}
