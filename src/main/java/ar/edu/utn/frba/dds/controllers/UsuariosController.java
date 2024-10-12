package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dtos.direccion.DireccionInputDTO;
import ar.edu.utn.frba.dds.dtos.humanos.HumanoOutputDTO;
import ar.edu.utn.frba.dds.dtos.juridico.JuridicaOutpuDTO;
import ar.edu.utn.frba.dds.exceptions.login.ContraseniaIncorrectaException;
import ar.edu.utn.frba.dds.exceptions.login.UsuarioIncorrectoException;
import ar.edu.utn.frba.dds.models.entities.helpers.conversor_json.ConversorJSON;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.entities.personas.Contacto;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import ar.edu.utn.frba.dds.models.entities.usuarios.Rol;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.factories.direcciones.DireccionFactory;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.juridicas.JuridicasRepository;
import ar.edu.utn.frba.dds.models.repositories.usuarios.UsuariosRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import com.fasterxml.jackson.databind.JsonNode;
import io.javalin.http.Context;

import java.util.*;
import java.util.stream.Collectors;

public class UsuariosController {
    public void handleLogin(Context ctx) {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");

        UsuariosRepository usuariosRepository = ServiceLocator.instanceOf(UsuariosRepository.class);
        Optional<Usuario> user = usuariosRepository.buscarPorUsername(username);

        if (user.isPresent()) {
            Usuario usuarioEncontrado = user.get();
            if (usuarioEncontrado.getPassword().equals(password)) {
                ctx.sessionAttribute("user", usuarioEncontrado.getId());

                List<String> nombresRoles = Optional.ofNullable(usuarioEncontrado.getRoles())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(Rol::getNombre)
                        .collect(Collectors.toList());

                // Guardar roles en la sesión
                ctx.sessionAttribute("roles", nombresRoles); // me guardo los roles del usuario

                // Guardo el id en la sesion
                ctx.sessionAttribute("id", usuarioEncontrado.getId());

                ctx.redirect("/");
            } else {
                throw new ContraseniaIncorrectaException("La contraseña es incorrecta");
            }
        } else {
            throw new UsuarioIncorrectoException("El usuario no existe");
        }
    }

    public void handleLogout(Context ctx) {
        ctx.req().getSession().invalidate();
        ctx.redirect("/login");
    }

    public void handlePerfil(Context ctx) {
        UsuariosRepository usuariosRepository = ServiceLocator.instanceOf(UsuariosRepository.class);
        Long id = ctx.sessionAttribute("user");
        List<String> roles = ctx.sessionAttribute("roles");
        Optional<Usuario> usuario = usuariosRepository.buscarPorId(id);


        if (usuario.isPresent()) {
            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Perfil");
            model.put("roles", roles);
            model.put("id", id);
            model.put("usuario", usuario.get().getUser());


            if (roles.contains("HUMANO")) {
                ColaboradorHumano humano = ServiceLocator.instanceOf(HumanosRepository.class).buscarPorIdUsuario(id).get();
                System.out.println(humano.getDireccion().getDireccion());
                HumanoOutputDTO dto = HumanoOutputDTO.of(humano);
                model.put("puntos", humano.calcularPuntaje());
                model.put("humano", dto);
            } else if (roles.contains("JURIDICA")) {
                Juridica juridica = ServiceLocator.instanceOf(JuridicasRepository.class).buscarPorId(id).get();
                JuridicaOutpuDTO dto = JuridicaOutpuDTO.of(juridica);
                model.put("puntos", juridica.calcularPuntaje());
                model.put("juridica", dto);
                model.put("esJuridica", true);
            }

            ctx.render("perfil.hbs", model);
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
            String body = ctx.body();
            JsonNode json = ConversorJSON.convertir(body);

            Long idUsuario = json.get("id").asLong();

            if (idUsuario != id) {
                ctx.status(401);
                return;
            } else if (roles.contains("HUMANO")) {
                String direccion = json.get("direccion").asText();
                String provincia = json.get("provincia").asText();
                String mail = json.get("mail").asText();
                String telegram = json.get("telegram").asText();
                String whatsapp = json.get("whatsapp").asText();
                List<Contacto> mediosDeContacto = new ArrayList<>();

                ColaboradorHumano humano = ServiceLocator.instanceOf(HumanosRepository.class).buscarPorIdUsuario(id).get();
                if (direccion != null && provincia != null) {
                    humano.setDireccion(DireccionFactory.create(new DireccionInputDTO(direccion, provincia)));
                } else {
                    humano.setDireccion(null);
                }

                if (!mail.isEmpty()) mediosDeContacto.add(Contacto.of("Mail", mail));
                if (!telegram.isEmpty()) mediosDeContacto.add(Contacto.of("Telegram", telegram));
                if (!whatsapp.isEmpty()) mediosDeContacto.add(Contacto.of("WhatsApp", whatsapp));
                humano.setMediosDeContacto(mediosDeContacto);
                ServiceLocator.instanceOf(HumanosRepository.class).actualizar(humano);

            } else if (roles.contains("JURIDICA")) {
                String direccion = json.get("direccion").asText();
                String provincia = json.get("provincia").asText();
                String mail = json.get("mail").asText();
                String telegram = json.get("telegram").asText();
                String whatsapp = json.get("whatsapp").asText();
                List<Contacto> mediosDeContacto = new ArrayList<>();

                Juridica juridica = ServiceLocator.instanceOf(JuridicasRepository.class).buscarPorId(id).get();
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
        }
    }
}
