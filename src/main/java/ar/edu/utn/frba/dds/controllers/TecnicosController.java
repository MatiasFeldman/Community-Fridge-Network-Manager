package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dtos.direccion.DireccionInputDTO;
import ar.edu.utn.frba.dds.dtos.tecnicos.TecnicoDTO;
import ar.edu.utn.frba.dds.dtos.visita_heladera.VisitaHeladeraOutputDTO;
import ar.edu.utn.frba.dds.exceptions.SolicitudIncorrectaException;
import ar.edu.utn.frba.dds.exceptions.registroPersonaVulnerable.DireccionJuridicaInexsistenteException;
import ar.edu.utn.frba.dds.exceptions.registro_usuario.ContraseniaJuridicaInseguraException;
import ar.edu.utn.frba.dds.exceptions.registro_usuario.UsuarioJuridicaExistenteException;
import ar.edu.utn.frba.dds.exceptions.suscripcion.InputValidationException;
import ar.edu.utn.frba.dds.exceptions.tecnicoDocumentoIncorrectoException;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Incidente;
import ar.edu.utn.frba.dds.models.entities.personas.Contacto;
import ar.edu.utn.frba.dds.models.entities.tecnicos.AreaCobertura;
import ar.edu.utn.frba.dds.models.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.models.entities.tecnicos.Tipo_documento;
import ar.edu.utn.frba.dds.models.entities.tecnicos.VisitaAHeladera;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.models.entities.usuarios.TipoRol;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.factories.direcciones.DireccionFactory;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import ar.edu.utn.frba.dds.models.repositories.tecnicos.TecnicosRepository;
import ar.edu.utn.frba.dds.models.repositories.usuarios.UsuariosRepository;
import ar.edu.utn.frba.dds.models.repositories.visitasHeladeras.VisitaHeladeraRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import ar.edu.utn.frba.dds.utils.RenderUtils;
import ar.edu.utn.frba.dds.utils.ValidadorUsernames;
import ar.edu.utn.frba.dds.utils.seguridad.HashPassword;
import ar.edu.utn.frba.dds.utils.seguridad.ValidadorDeContrasenias;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.UploadedFile;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class TecnicosController {

    public void formSolucionIncidente(Context ctx) {
        Map<String, Object> model = new HashMap<>();
        Long idHeladera;

        try {
            idHeladera = Long.parseLong(ctx.pathParam("id"));
            Optional<Heladera> heladera = ServiceLocator.instanceOf(HeladerasRepository.class).buscarPorId(idHeladera);


            // Si no se encuentra la heladera, redirigir a la página de error
            if (heladera.isEmpty()) {
                Map<String, Object> model2 = new HashMap<>();
                model2.put("error", "La heladera con el ID proporcionado no existe.");
                model2.put("paginaAnterior", "/heladeras");
                ctx.status(404).render("400Personalizado.hbs", model2);
                return;
            }

            // Pasar la heladera al modelo
            model.put("heladera", heladera.get());
            model.put("noIncidentes", Boolean.parseBoolean(ctx.queryParam("noHayIncidentes")));

        } catch (NumberFormatException e) {
            // Manejo de error para un ID no válido
            Map<String, Object> model2 = new HashMap<>();
            model2.put("error", "El ID de la heladera debe ser un número válido.");
            model2.put("paginaAnterior", "/heladeras");
            ctx.status(400).render("400Personalizado.hbs", model2);
            return;

        } catch (Exception e) {
            // Manejo genérico de errores
            Map<String, Object> model2 = new HashMap<>();
            model2.put("error", "Ocurrió un error inesperado. Por favor, inténtelo más tarde.");
            model2.put("paginaAnterior", "/heladeras");
            ctx.status(500).render("400Personalizado.hbs", model2);
            return;
        }

        // Renderizar la vista si todo está bien
        model.put("titulo", "Visita a heladera");
        RenderUtils.renderizar(ctx, "heladeras/form-visita-heladera.hbs", model);
    }

    public void registrarVisita(Context ctx) {
        try {
            // Validar sesión del técnico
            Long usuarioId = ctx.sessionAttribute("id");
            Optional<Tecnico> tecnico = ServiceLocator.instanceOf(TecnicosRepository.class).buscarPorIdUsuario(usuarioId);
            if (!tecnico.isPresent()) {
                throw new IllegalArgumentException("El técnico no fue encontrado.");
            }

            // Validar y parsear el ID de la heladera
            Long heladeraId;
            try {
                heladeraId = Long.parseLong(ctx.pathParam("id"));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("El ID de la heladera debe ser un número válido.");
            }

            Optional<Heladera> heladera = ServiceLocator.instanceOf(HeladerasRepository.class).buscarPorId(heladeraId);
            if (!heladera.isPresent()) {
                throw new IllegalArgumentException("La heladera no fue encontrada.");
            }

            // Buscar incidentes de la heladera
            List<Incidente> incidentes = ServiceLocator.instanceOf(IncidentesRepository.class).buscarTodosPorHeladera(heladera.get());
            Optional<Incidente> incidente = incidentes.stream()
                    .filter(incidente2 -> !incidente2.getResuelto())
                    .findFirst();

            if (!incidente.isPresent()) {
                ctx.redirect("/heladeras/" + heladeraId + "/visita?noHayIncidentes=true");
                return;
            }

            // Validar y parsear fecha y hora
            String fechaForm = ctx.formParam("fechaVisita");
            String horaForm = ctx.formParam("horaVisita");

            if (fechaForm == null || horaForm == null || fechaForm.isEmpty() || horaForm.isEmpty()) {
                throw new IllegalArgumentException("La fecha y la hora de la visita son obligatorias.");
            }

            LocalDate fecha;
            LocalTime hora;
            try {
                fecha = LocalDate.parse(fechaForm);
                hora = LocalTime.parse(horaForm);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("La fecha o la hora de la visita no tienen el formato válido.");
            }

            LocalDateTime fechaYHora = LocalDateTime.of(fecha, hora);
            if (fechaYHora.isAfter(LocalDateTime.now())) {
                throw new IllegalArgumentException("La fecha y la hora de la visita no pueden ser futuras.");
            }

            // Validar resolución y descripción
            Boolean resuelto;
            try {
                resuelto = Boolean.parseBoolean(ctx.formParam("resolucionIncidente"));
            } catch (Exception e) {
                throw new IllegalArgumentException("El campo de resolución debe ser 'true' o 'false'.");
            }

            String descripcion = ctx.formParam("descripcion");
            if (descripcion == null || descripcion.isEmpty()) {
                throw new IllegalArgumentException("La descripción de la visita es obligatoria.");
            }

            // Crear la visita
            VisitaAHeladera visita = VisitaAHeladera.crear(incidente.get(), tecnico.get(), fechaYHora, resuelto, descripcion);

            // Si se resolvió el incidente
            if (resuelto) {
                incidente.get().setResuelto(true);
                incidente.get().setFechaResuelto(LocalDateTime.now());
                ServiceLocator.instanceOf(IncidentesRepository.class).modificar(incidente.get());

                heladera.get().activar();
                ServiceLocator.instanceOf(HeladerasRepository.class).modificar(heladera.get());
            }

            // Guardar la visita
            ServiceLocator.instanceOf(VisitaHeladeraRepository.class).guardar(visita);

            // Manejar la imagen subida
            UploadedFile imagenVisita = ctx.uploadedFile("foto");
            if (imagenVisita != null && imagenVisita.size() > 0) {
                String nombreArchivo = "visita_" + visita.getId() + ".png";
                String directorioCompleto = Paths.get("src", "main", "java", "ar", "edu", "utn", "frba", "dds", "imagenesDinamicas", "fotosVisitas", nombreArchivo).toString();
                String rutaImagen = "/imagenes/fotosVisitas/" + nombreArchivo;

                try (InputStream inputStream = imagenVisita.content()) {
                    Files.copy(inputStream, Paths.get(directorioCompleto), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new RuntimeException("Error al guardar la imagen de la visita.");
                }

                visita.setFoto(rutaImagen);
                ServiceLocator.instanceOf(VisitaHeladeraRepository.class).actualizar(visita);
            }

            ctx.redirect("/");

        } catch (IllegalArgumentException e) {
            // Manejo de errores de datos inválidos
            Map<String, Object> model = new HashMap<>();
            model.put("error", e.getMessage());
            model.put("paginaAnterior", ctx.path());
            ctx.status(400).render("400Personalizado.hbs", model);

        } catch (Exception e) {
            // Manejo de errores inesperados
            ctx.status(500).result("Ocurrió un error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void create(Context ctx) {
        List<Map<String, Object>> tipoDocConNumeros = Arrays.stream(Tipo_documento.values())
                .map(tipo_documento -> {
                    Map<String, Object> tipoDocMap = new HashMap<>();
                    tipoDocMap.put("nombre", tipo_documento.name());
                    tipoDocMap.put("valor", tipo_documento.ordinal());
                    return tipoDocMap;
                })
                .collect(Collectors.toList());

        Map<String, Object> model = new HashMap<>();
        model.put("tipos",tipoDocConNumeros);

        String contraseniaIncorrecta = ctx.queryParam("contrasenia");
        String usuarioExistente = ctx.queryParam("usuarioExistente");
        if(Boolean.parseBoolean(contraseniaIncorrecta)){
            model.put("contraseniaInsegura","Contraseña Insegura");
        }
        if(Boolean.parseBoolean(usuarioExistente)){
            model.put("usuarioExistente","El usuario ingresado ya existe");
        }

        RenderUtils.renderizar(ctx,"registro-usuario/registro-tecnico.hbs",model);

    }

    public void save( Context ctx) {
        try {
            String password = ctx.formParam("password");
            String username = ctx.formParam("user");
            String direccion = ctx.formParam("direccion");
            String provincia = ctx.formParam("provincia");
            String nombre = ctx.formParam("nombre");
            String apellido = ctx.formParam("apellido");
            String tipo_documento = ctx.formParam("tipo_documento");
            String nroDocumento = ctx.formParam("nroDocumento");
            String radio = ctx.formParam("radio");
            String nroCUIL = ctx.formParam("nroCUIL");
            String email = ctx.formParam("Mail");
            String telegram = ctx.formParam("Telegram");
            String whatsapp = ctx.formParam("Whatsapp");
            Contacto medioContactoTecnico = null;

            if (nroDocumento == null || !nroDocumento.matches("\\d+")) {
                throw new tecnicoDocumentoIncorrectoException("El número de documento debe contener solo números.");
            }


            if (nroCUIL == null || !nroCUIL.matches("\\d+")) {
                throw new IllegalArgumentException("El número de CUIL debe contener solo números.");
            }

            if (password == null || username == null || direccion == null || provincia == null ||
                    password.isEmpty() || username.isEmpty() || direccion.isEmpty() || provincia.isEmpty()) {
                throw new IllegalArgumentException("Todos los campos obligatorios deben estar completos.");
            }

            ValidadorDeContrasenias validador = ServiceLocator.instanceOf(ValidadorDeContrasenias.class);
            boolean contraseniaInsegura = !validador.esValida(password);
            boolean usuarioExistente = ValidadorUsernames.existe(username, "tecnico");

            if (usuarioExistente || contraseniaInsegura) {
                String redireccion = "/registro/tecnico?"
                        + "usuarioExistente=" + usuarioExistente
                        + "&contrasenia=" + contraseniaInsegura;
                ctx.redirect(redireccion);
                return;
            }
            Direccion direccionreal = DireccionFactory.create(new DireccionInputDTO(direccion, provincia));

            if (direccionreal == null) {
                throw new DireccionJuridicaInexsistenteException("La dirección ingresada no es válida");
            }
            if (nombre == null || apellido == null || tipo_documento == null || nroDocumento == null ||
                    nroCUIL == null || radio == null ||
                    nombre.isEmpty() || apellido.isEmpty() || tipo_documento.isEmpty() || nroDocumento.isEmpty() ||
                    nroCUIL.isEmpty() || radio.isEmpty()) {
                System.out.println("entre");
                throw new IllegalArgumentException("Todos los datos personales deben estar completos.");
            }
            try {
                Integer numeroEnumTipoDoc = Integer.parseInt(tipo_documento);
                if (numeroEnumTipoDoc < 0 || numeroEnumTipoDoc >= Tipo_documento.values().length) {
                    throw new IllegalArgumentException("El tipo de documento no es válido.");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("El tipo de documento debe ser un número válido.");
            }

            try {
                Double.parseDouble(radio);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("El radio debe ser un número válido.");
            }

            if (email != null && !email.isEmpty()) {
                HeladerasController.validarCorreoElectronico(email);
                medioContactoTecnico = Contacto.of("Email", email);
            } else if (telegram != null && !telegram.isEmpty()) {
                HeladerasController.validarNumeroTelefono(telegram);
                medioContactoTecnico = Contacto.of("Telegram", telegram);
            } else if (whatsapp != null && !whatsapp.isEmpty()) {
                HeladerasController.validarNumeroTelefono(whatsapp);
                medioContactoTecnico = Contacto.of("WhatsApp", whatsapp);
            }

            if (medioContactoTecnico == null) {
                throw new IllegalArgumentException("Debe proporcionar al menos un medio de contacto.");
            }

            HashPassword hash = ServiceLocator.instanceOf(HashPassword.class);
            String passwordHashed = hash.hashPassword(password);

            Usuario usuario = new Usuario(username, passwordHashed, List.of(TipoRol.TECNICO));
            ServiceLocator.instanceOf(UsuariosRepository.class).guardar(usuario);

            TecnicoDTO dto = new TecnicoDTO(
                    nombre,
                    apellido,
                    medioContactoTecnico,
                    Tipo_documento.values()[Integer.parseInt(tipo_documento)],
                    nroDocumento,
                    nroCUIL,
                    new AreaCobertura(direccionreal, Double.parseDouble(radio))
            );

            Tecnico tecnico = Tecnico.create(dto);
            tecnico.setUser(usuario);

            ServiceLocator.instanceOf(TecnicosRepository.class).guardar(tecnico);
            ctx.redirect("/");

        } catch (DireccionJuridicaInexsistenteException e) {
            manejarError(ctx, "Error en la dirección", e.getMessage(), 400);
        } catch (IllegalArgumentException | InputValidationException e) {
            manejarError(ctx, "Error en los datos ingresados", e.getMessage(), 400);
        }
    }

    private void manejarError(Context ctx, String titulo, String mensaje, int status) {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", titulo);
        model.put("error", mensaje);
        model.put("paginaAnterior", "/registro/tecnico");
        ctx.status(status).render("400Personalizado.hbs", model);
    }


    public void viewMisVisitas( Context ctx) {
        Map<String, Object> model = new HashMap<>();
        Long id = ctx.sessionAttribute("id");
        Optional<Tecnico> tecnico = ServiceLocator.instanceOf(TecnicosRepository.class).buscarPorIdUsuario(id);
        List<VisitaAHeladera> visitas = ServiceLocator.instanceOf(VisitaHeladeraRepository.class).buscarPorTecnico(tecnico.get());
        List<VisitaHeladeraOutputDTO> visitasDTO = visitas.stream()
                .map(VisitaHeladeraOutputDTO::of) // Convertir a DTO
                .toList();

        model.put("visitas",visitasDTO);
        RenderUtils.renderizar(ctx,"misVisitas.hbs",model);
    }
}
