package ar.edu.utn.frba.dds.controllers;
import ar.edu.utn.frba.dds.dtos.ContactosDTO;
import ar.edu.utn.frba.dds.dtos.incidentes.IncidenteOutputDTO;
import ar.edu.utn.frba.dds.dtos.suscripcion.SuscripcionOutputDTO;
import ar.edu.utn.frba.dds.exceptions.suscripcion.HeladeraNoEncontradaException;
import ar.edu.utn.frba.dds.exceptions.suscripcion.InputValidationException;
import ar.edu.utn.frba.dds.exceptions.suscripcion.UsuarioNoEncontradoException;
import ar.edu.utn.frba.dds.models.entities.personas.*;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import ar.edu.utn.frba.dds.dtos.heladeras.HeladeraOutputDTO;
import ar.edu.utn.frba.dds.exceptions.HeladeraInexistenteException;
import ar.edu.utn.frba.dds.exceptions.UsuarioSinTarjetaException;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaColaborador;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.*;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.IntentoAperturaResuelto;
import ar.edu.utn.frba.dds.models.entities.helpers.conversor_json.ConversorJSON;
import ar.edu.utn.frba.dds.models.entities.helpers.json_to_entidad.JSONtoDenunciaFallaTecnica;
import ar.edu.utn.frba.dds.models.entities.suscripciones.SuscripcionAHeladera;
import ar.edu.utn.frba.dds.models.entities.suscripciones.tipo_suscripciones.HeladeraLlena;
import ar.edu.utn.frba.dds.models.entities.suscripciones.tipo_suscripciones.SufrioDesperfecto;
import ar.edu.utn.frba.dds.models.entities.suscripciones.tipo_suscripciones.Suscripcion;
import ar.edu.utn.frba.dds.models.entities.suscripciones.tipo_suscripciones.ViandasDisponibles;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.intentos_de_apertura.IntentosDeAperturaRepository;
import ar.edu.utn.frba.dds.models.repositories.juridicas.JuridicasRepository;
import ar.edu.utn.frba.dds.models.repositories.solicitudes_de_apertura_de_heladera.SolicitudesDeAperturaRepository;
import ar.edu.utn.frba.dds.models.repositories.suscripciones.SuscripcionesRepository;
import ar.edu.utn.frba.dds.models.repositories.tarjetas_colaboradores.TarjetasColaboradoresRepository;
import ar.edu.utn.frba.dds.models.repositories.usuarios.UsuariosRepository;
import ar.edu.utn.frba.dds.services.receptores.MqttReceptorIntento;
import ar.edu.utn.frba.dds.utils.RenderUtils;
import ar.edu.utn.frba.dds.utils.permisos.PermisoDenegadoException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@AllArgsConstructor
public class HeladerasController {
    private Accionador accionador;
    private SolicitudesDeAperturaRepository solicitudes;
    private IntentosDeAperturaRepository intentos;
    private TarjetasColaboradoresRepository tarjetas;
    private HeladerasRepository heladeras;
    private HumanosRepository humanos;
    private JuridicasRepository juridicas;


    public void reportarFallaTecnica(String json) {
        JsonNode node = ConversorJSON.convertir(json);
        Long idDenunciante = Long.parseLong(node.get("id_usuario").asText());
        String rol = node.get("rol").asText();
        Usuario usuario;

        if (Objects.equals(rol, "HUMANO")) usuario = humanos.buscarPorIdUsuario(idDenunciante).get().getUser();
        else usuario = juridicas.buscarPorIdUsuario(idDenunciante).get().getUser();

        String nombreHeladera = node.get("heladera").asText();
        if (heladeras.buscarPorNombre(nombreHeladera).isEmpty()) {
            throw new HeladeraInexistenteException("No se encontro la heladera");
        } else {
            DenunciaFallaTecnica denuncia = JSONtoDenunciaFallaTecnica.convertir(node, usuario);
            Heladera heladera = heladeras.buscarPorNombre(nombreHeladera).get();
            heladera.desactivar();

            denuncia.setHeladera(heladera);
            heladera.notificarFallaTecnica();

            accionador.sucedeFallaTecnica(denuncia, heladera);
        }


    }



    public void registrarIntentoDeApertura(IntentoAperturaResuelto intento) {
        intentos.guardar(intento);
    }

    public void mostrarHeladeras(Context ctx) {
        List<Heladera> heladeras = obtenerHeladerasFiltradas(ctx);  // Mover la lógica de filtrado a un método separado

        List<HeladeraOutputDTO> dtos = generarDTOsDeHeladeras(heladeras, ctx);

        Map<String, Object> model = new HashMap<>();
        model.put("heladeras", dtos);
        model.put("titulo", "Heladeras");

        boolean permisoSuscripcion = verificarPermisoSuscripcion(ctx);
        boolean permisoTecnico = verificarPermisoTecnico(ctx);

        model.put("permisoSuscripcion", permisoSuscripcion);
        model.put("permisoTecnico", permisoTecnico);

        RenderUtils.renderizar(ctx,"heladeras/mapa-de-heladeras-user.hbs", model);
    }

    private List<Heladera> obtenerHeladerasFiltradas(Context ctx) {
        List<Heladera> heladeras;

        // Verificar si es una solicitud POST con filtros
        if ("POST".equalsIgnoreCase(String.valueOf(ctx.method()))) {
            String filtroActivo = ctx.formParam("activo");
            String filtroInactivo = ctx.formParam("inactivo");
            List<Heladera> todasHeladeras = this.heladeras.buscarTodos();

            // Filtrar según los parámetros
            heladeras = filtrarPorEstado(todasHeladeras, filtroActivo, filtroInactivo);

            // Aplicar otros tipos de búsqueda
            String tipoBusqueda = ctx.formParam("busqueda");
            String valorBusqueda = ctx.formParam("valor");

            heladeras = buscarPorTipo(heladeras, tipoBusqueda, valorBusqueda);
        } else {
            // Si es GET, mostrar todas las heladeras
            heladeras = this.heladeras.buscarTodos();
        }

        return heladeras;
    }

    private List<Heladera> filtrarPorEstado(List<Heladera> todasHeladeras, String filtroActivo, String filtroInactivo) {
        if (filtroActivo != null && filtroInactivo != null) {
            return todasHeladeras;  // Mostrar todas si ambos filtros están presentes
        } else if (filtroActivo != null) {
            return todasHeladeras.stream()
                    .filter(Heladera::getActiva)
                    .collect(Collectors.toList());
        } else if (filtroInactivo != null) {
            return todasHeladeras.stream()
                    .filter(h -> !h.getActiva())
                    .collect(Collectors.toList());
        }
        return todasHeladeras;  // Si no hay filtros, devolver todas las heladeras
    }

    private List<Heladera> buscarPorTipo(List<Heladera> heladeras, String tipoBusqueda, String valorBusqueda) {
        if (tipoBusqueda == null || valorBusqueda == null) return heladeras;  // Si no hay parámetros de búsqueda


        return switch (tipoBusqueda) {
            case "direccion" -> ServiceLocator.instanceOf(HeladerasRepository.class).buscarHeladerasPorDireccion(valorBusqueda);
            case "comuna" -> ServiceLocator.instanceOf(HeladerasRepository.class).buscarPorComuna(valorBusqueda);
            case "todas" -> ServiceLocator.instanceOf(HeladerasRepository.class).buscarTodos();
            default -> heladeras;
        };
    }

    private List<HeladeraOutputDTO> generarDTOsDeHeladeras(List<Heladera> heladeras, Context ctx) {
        List<SuscripcionAHeladera> suscripciones = ServiceLocator.instanceOf(SuscripcionesRepository.class).buscarTodos();
        Long idUsuario = ctx.sessionAttribute("id");

        List<SuscripcionAHeladera> suscripcionesUsuario = suscripciones.stream()
                .filter(s -> s.getObserverSuscripcion().getId().equals(idUsuario))
                .collect(Collectors.toList());

        return heladeras.stream().map(h -> {
            HeladeraOutputDTO dto = HeladeraOutputDTO.of(h);
            boolean estaSuscrito = suscripcionesUsuario.stream()
                    .anyMatch(s -> s.getHeladera().getId().equals(h.getId()));
            dto.setEstaSuscrito(estaSuscrito);
            return dto;
        }).collect(Collectors.toList());
    }

    private boolean verificarPermisoSuscripcion(Context ctx) {
        List<String> rolesUsuario = ctx.sessionAttribute("roles");
        if (rolesUsuario == null || rolesUsuario.isEmpty()) return false;

        return rolesUsuario.stream()
                .anyMatch(rol -> rol.equals("HUMANO") || rol.equals("JURIDICA"));
    }

    private boolean verificarPermisoTecnico(Context ctx) {
        List<String> rolesUsuario = ctx.sessionAttribute("roles");
        if (rolesUsuario == null || rolesUsuario.isEmpty()) return false;

        return rolesUsuario.stream()
                .anyMatch(rol -> rol.equals("TECNICO"));
    }

    private void agregarContactosUsuarioAlModelo(Map<String, Object> model, Context ctx) {
        List<String> rolesUsuario = ctx.sessionAttribute("roles");
        Long idUsuario = ctx.sessionAttribute("id");
        List<ContactosDTO> contactosDTO = new ArrayList<>();


        if (rolesUsuario == null || rolesUsuario.isEmpty())  return;

        if (rolesUsuario.get(0).contains("HUMANO")) {

            ColaboradorHumano humano = ServiceLocator.instanceOf(HumanosRepository.class)
                    .buscarPorIdUsuario(idUsuario).get();

            for (AtributoHumanoRespondido nombreContacto : humano.getMediosDeContacto()) {
                if(!nombreContacto.getValor().isEmpty()){
                    String tipoContacto = nombreContacto.getNombreAtributo();
                    String valor = nombreContacto.getValor();
                    contactosDTO.add(new ContactosDTO(tipoContacto, valor));
                }
            }

        } else if (rolesUsuario.get(0).contains("JURIDICA")) {

            Juridica juridica = ServiceLocator.instanceOf(JuridicasRepository.class)
                    .buscarPorIdUsuario(idUsuario).get();

            for(Contacto contacto: juridica.getMediosDeContacto()){
                String tipoContacto = contacto.getTipoContacto().getNombre();
                String valor = contacto.getValorContacto();
                contactosDTO.add(new ContactosDTO(tipoContacto, valor));
            }
        }

        contactosDTO.forEach(c -> System.out.println(c.getTipoContacto() + " " + c.getValor()));

        model.put("contactos",contactosDTO);
    }

    public void create(Context ctx) {

    }

    public void editarHeladera(Context ctx) {
        List<Heladera> heladeras = this.heladeras.buscarTodos();
        List<HeladeraOutputDTO> dtos = new ArrayList<>();

        heladeras.forEach(h -> {
            dtos.add(HeladeraOutputDTO.of(h));
        });

        Map<String, Object> model = new HashMap<>(); // sirve para pasar parámetros a la vista
        model.put("heladeras", dtos);

        RenderUtils.renderizar(ctx,"heladeras/modificacion-heladera.hbs", model);
    }

    @SneakyThrows
    public void eliminarHeladera(Context ctx) {
        String body = ctx.body();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(body, Map.class);

        // Obtener el id del JSON
        Long heladeraId = Long.valueOf(jsonMap.get("id"));

        Optional<Heladera> heladera = heladeras.buscarPorId(heladeraId);
        if (heladera.isEmpty()) {
            ctx.redirect("/not-found");
        } else {
            heladeras.eliminar(heladera.get());
            ctx.status(HttpStatus.OK).result("Heladera eliminada");
        }
    }

    @SneakyThrows
    public void modificarEstadoHeladera(Context ctx) {
        String body = ctx.body();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(body, Map.class);

        // Obtener el id del JSON
        Long heladeraId = Long.valueOf(jsonMap.get("id"));
        Boolean activa = Boolean.valueOf(jsonMap.get("activa"));

        Optional<Heladera> heladera = heladeras.buscarPorId(heladeraId);
        if (heladera.isEmpty()) {
            ctx.redirect("/not-found");
        } else {
            heladera.get().setActiva(activa);
            heladeras.modificar(heladera.get());
            ctx.status(HttpStatus.OK).result("Heladera actualizada");
        }

    }

    @SneakyThrows
    public void actualizarHeladera(@NotNull Context ctx) {
        String body = ctx.body();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(body, Map.class);

        Long heladeraId = Long.valueOf(jsonMap.get("id"));
        String nombre = jsonMap.get("nombre");
        Integer capacidadMaxima = Integer.valueOf(jsonMap.get("capacidadMaxima"));
        Integer capacidadActual = Integer.valueOf(jsonMap.get("capacidadMaxima"));
        String direccionString = jsonMap.get("direccion");
        String provinciaString = jsonMap.get("provincia");

        Direccion direccionNueva = Direccion.of(direccionString, provinciaString);

        Optional<Heladera> heladeraBuscada = heladeras.buscarPorId(heladeraId);
        if (heladeraBuscada.isEmpty()) {
            ctx.redirect("/not-found");
        } else {
            Heladera heladera = heladeraBuscada.get();
            heladera.setNombre(nombre);
            heladera.setCapacidadMaxima(capacidadMaxima);
            heladera.setCapActual(capacidadActual);
            heladera.setDireccion(direccionNueva);
            heladeras.modificar(heladera);
            ctx.status(HttpStatus.OK).result("Heladera actualizada");
        }

    }

    @SneakyThrows
    public void reporteFallaTecnicaView(Context ctx) {

        Map<String, Object> model = new HashMap<>(); // sirve para pasar parámetros a la vista
        model.put("titulo", "Reporte falla técnica");


        String idParam = ctx.pathParam("id");
        Long id = Long.parseLong(idParam);
        Optional<Heladera> buscada = heladeras.buscarPorId(id);
        if (buscada.isPresent()) {
            Heladera h = buscada.get();
            HeladeraOutputDTO dto = HeladeraOutputDTO.of(h);
            model.put("heladera", dto);
            RenderUtils.renderizar(ctx,"heladeras/form-falla-tecnica.hbs", model);
        } else{
            ctx.redirect("/not-found");
        }
    }

    public void registrarFallaTecnica(Context ctx) {
        try{
        String body = ctx.body();

        JsonNode node = ConversorJSON.convertir(body);

        Long id_heladera = Long.parseLong(node.get("id_heladera").asText());
        LocalDateTime fecha = LocalDateTime.parse(node.get("fecha").asText());
        String descripcion = node.get("descripcion").asText();
        String foto = node.get("foto").asText();
        Long idUsuario = obtenerUsuarioId(ctx);
        Optional<Usuario> usuario = ServiceLocator.instanceOf(UsuariosRepository.class).buscarPorId(idUsuario);
        if(!usuario.isPresent()){
            throw new UsuarioNoEncontradoException("usuario no econtrado");
        }

        Optional<Heladera> heladera_buscada = heladeras.buscarPorId(id_heladera);
        if (heladera_buscada.isEmpty()) {
            ctx.status(HttpStatus.NOT_FOUND).result("Heladera no encontrada");
        } else {
            Heladera heladera = heladera_buscada.get();
            DenunciaFallaTecnica denuncia = DenunciaFallaTecnica.of(usuario.get(), descripcion, foto, fecha, heladera);
            accionador.sucedeFallaTecnica(denuncia, heladera);
            heladera.notificarFallaTecnica();
            ServiceLocator.instanceOf(HeladerasRepository.class).modificar(heladera);
            ctx.status(HttpStatus.OK).result("Falla técnica registrada");
        }
        }catch ( InputValidationException e) {
            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Error en la solicitud");
            model.put("error",  e.getMessage());
            model.put("paginaAnterior", "/heladeras");
            ctx.status(400).render("400Personalizado.hbs", model);
        } catch (Exception e) {
            ctx.status(500).result("Error en el servidor: " + e.getMessage());
        }

    }

    public void suscribirse(Context ctx) {
        try {
            Long heladeraId = validarHeladeraId(ctx);
            Long usuarioId = obtenerUsuarioId(ctx);
            List<String> rolUsuario = ctx.sessionAttribute("roles");
            String tipoSuscripcion = ctx.formParam("tipo_suscripcion");
            Integer cantidad = !Objects.equals(ctx.formParam("cantidad"), "") ? Integer.parseInt(ctx.formParam("cantidad")) : null;
            String medioDeNotificacion = ctx.formParam("notificador");
            String nuevoMedioNotificacion = Objects.equals(ctx.formParam("contacto_adicional"), "") ? null : ctx.formParam("contacto_adicional");
            if(tipoSuscripcion == null){
                throw new InputValidationException("tipo de suscripcion invalido o nulo");
            }
            if(cantidad != null && cantidad<=0 ){
                throw new InputValidationException("La cantidad ingresada es invalida");
            }
            if(medioDeNotificacion == null || medioDeNotificacion.equalsIgnoreCase("")){
                throw new InputValidationException("Medio de notificacion invalido");
            }

            if (nuevoMedioNotificacion != null) {
                //validaciones de tipos de datosmedioDeNotificacion
                if (medioDeNotificacion.equalsIgnoreCase("whatsapp") || medioDeNotificacion.equalsIgnoreCase("telegram")) {
                    validarNumeroTelefono(nuevoMedioNotificacion);

                }else if(medioDeNotificacion.equalsIgnoreCase("mail") ){
                    validarCorreoElectronico(nuevoMedioNotificacion);
                }else {
                    throw new InputValidationException("El medio de notificacion recibido es desconocido");
                }

                if (rolUsuario.get(0).contains("HUMANO")) {
                    ColaboradorHumano humano = ServiceLocator.instanceOf(HumanosRepository.class).buscarPorIdUsuario(usuarioId)
                            .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario Humano no encontrado"));
                    humano.actualizarMedioDeContacto(medioDeNotificacion, nuevoMedioNotificacion);
                    ServiceLocator.instanceOf(HumanosRepository.class).actualizar(humano);
                } else if (rolUsuario.get(0).contains("JURIDICA")) {
                    Juridica juridica = ServiceLocator.instanceOf(JuridicasRepository.class).buscarPorIdUsuario(usuarioId)
                            .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario Jurídico no encontrado"));
                    juridica.generarContacto(new Contacto(new TipoContacto(medioDeNotificacion), nuevoMedioNotificacion));
                    ServiceLocator.instanceOf(JuridicasRepository.class).modificar(juridica);
                } else {
                    throw new InputValidationException("Este rol no tiene permitido suscribirse");
                }
            }

            Heladera heladera = ServiceLocator.instanceOf(HeladerasRepository.class)
                    .buscarPorId(heladeraId).orElseThrow(() -> new HeladeraNoEncontradaException("Heladera no encontrada"));

            Object usuario = obtenerUsuarioPorRol(usuarioId, rolUsuario);
            Suscripcion suscripcion = this.crearSuscripcion(tipoSuscripcion, usuario, medioDeNotificacion, cantidad);
            SuscripcionAHeladera nuevaSuscripcion = new SuscripcionAHeladera(
                    ServiceLocator.instanceOf(UsuariosRepository.class).buscarPorId(usuarioId)
                            .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado")),
                    suscripcion, heladera);

            heladera.suscribir(nuevaSuscripcion);

            ServiceLocator.instanceOf(SuscripcionesRepository.class).guardar(nuevaSuscripcion);
            ServiceLocator.instanceOf(HeladerasRepository.class).modificar(heladera);

            ctx.redirect("/heladeras");
        } catch (UsuarioNoEncontradoException | HeladeraNoEncontradaException | InputValidationException e) {
            // Renderizar la vista de error 400 con el mensaje específico
            Map<String, Object> model = new HashMap<>(); // sirve para pasar parámetros a la vista
            model.put("titulo", "Error en la solicitud");
            model.put("error",  e.getMessage());
            model.put("paginaAnterior", "/heladeras");
            ctx.status(400).render("400Personalizado.hbs", model);
        } catch (Exception e) {
            // Otros errores no anticipados se manejan como errores internos
            ctx.status(500).result("Error en el servidor: " + e.getMessage());
        }
    }



    private Object obtenerUsuarioPorRol(Long usuarioId, List<String> rolUsuario) throws Exception {
        if (rolUsuario.get(0).contains("HUMANO")) {
            return ServiceLocator.instanceOf(HumanosRepository.class).buscarPorIdUsuario(usuarioId)
                    .orElseThrow(() -> new Exception("Usuario Humano no encontrado"));
        } else if (rolUsuario.get(0).contains("JURIDICA")) {
            return ServiceLocator.instanceOf(JuridicasRepository.class).buscarPorIdUsuario(usuarioId)
                    .orElseThrow(() -> new Exception("Usuario Jurídico no encontrado"));
        } else {
            throw new Exception("Este rol no tiene permitido suscribirse");
        }
    }

    private Suscripcion crearSuscripcion(String tipoSuscripcion, Object usuario, String medioDeNotificacion, Integer cantidad) throws Exception {
        if (usuario instanceof ColaboradorHumano) {
            ColaboradorHumano colaborador = (ColaboradorHumano) usuario;
            switch (tipoSuscripcion) {
                case "viandas_disponibles":
                    if (cantidad == null) {
                        throw new Exception("Debe especificar la cantidad de viandas disponibles");
                    }
                    return ViandasDisponibles.of(colaborador.getMedioDeContacto(medioDeNotificacion).get().getValor(), cantidad);
                case "heladera_llena":
                    if (cantidad == null) {
                        throw new Exception("Debe especificar la cantidad de viandas faltantes");
                    }
                    return HeladeraLlena.of(colaborador.getMedioDeContacto(medioDeNotificacion).get().getValor(), cantidad);
                case "sufrio_desperfecto":
                    return SufrioDesperfecto.of(colaborador.getMedioDeContacto(medioDeNotificacion).get().getValor());
                default:
                    throw new Exception("Tipo de suscripción no válido");
            }
        } else if (usuario instanceof Juridica) {
            Juridica juridica = (Juridica) usuario;
            switch (tipoSuscripcion) {
                case "viandas_disponibles":
                    if (cantidad == null) {
                        throw new Exception("Debe especificar la cantidad de viandas disponibles");
                    }
                    return ViandasDisponibles.of(juridica.getMedioDeContacto(medioDeNotificacion), cantidad);
                case "heladera_llena":
                    if (cantidad == null) {
                        throw new Exception("Debe especificar la cantidad de viandas faltantes");
                    }
                    return HeladeraLlena.of(juridica.getMedioDeContacto(medioDeNotificacion), cantidad);
                case "sufrio_desperfecto":
                    return SufrioDesperfecto.of(juridica.getMedioDeContacto(medioDeNotificacion));
                default:
                    throw new Exception("Tipo de suscripción no válido");
            }
        } else {
            throw new Exception("Tipo de usuario no reconocido");
        }
    }
    private Long validarHeladeraId(Context ctx) throws InputValidationException {
        try {
            return Long.parseLong(ctx.formParam("heladera_id"));
        } catch (NumberFormatException e) {
            throw new InputValidationException("El ID de la heladera no es válido");
        }
    }

    private Long obtenerUsuarioId(Context ctx) throws InputValidationException {
        Long usuarioId = ctx.sessionAttribute("id");
        if (usuarioId == null) {
            throw new InputValidationException("ID de usuario no encontrado en sesión");
        }
        return usuarioId;
    }

    public static String validarNumeroTelefono(String numero) throws InputValidationException {
            // La expresión regular permite solo dígitos y opcionalmente un "+" al inicio
            if (!numero.matches("^\\+?[0-9]{7,15}$")) {
                throw new InputValidationException("El número de teléfono solo debe contener dígitos y un prefijo opcional '+'.");
            }
        return numero;
    }
    public static String validarCorreoElectronico(String correo) throws InputValidationException {
        // Expresión regular para un correo electrónico básico
        String emailRegex = "^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$";
        if (!correo.matches(emailRegex)) {
            throw new InputValidationException("El correo electrónico no es válido. Asegúrese de que tenga el formato nombre@dominio.extension.");
        }
        return correo;
    }

// Otros métodos de validación similares para cada parámetro que requiera validación...


    public void desuscribirse(Context ctx){
        Long heladeraId = Long.parseLong(ctx.formParam("heladera_id"));
        Long suscripcionId = Long.parseLong(ctx.formParam("suscripcion_id"));

        System.out.println("heladeraId: " + heladeraId);
        System.out.println("suscripcionId: " + suscripcionId);

        Optional<Heladera> heladera = ServiceLocator.instanceOf(HeladerasRepository.class).buscarPorId(heladeraId);
        if(heladera.isPresent()){
            Heladera h = heladera.get();
            SuscripcionAHeladera suscri = h.getSuscriptores().stream().filter(s -> s.getId().equals(suscripcionId)).findFirst().orElse(null);
            h.desuscribir(suscri);
            ServiceLocator.instanceOf(HeladerasRepository.class).modificar(heladera.get());
            ServiceLocator.instanceOf(SuscripcionesRepository.class).eliminar(suscri);
        }

        ctx.redirect("/heladeras");

    }

    public void suscripcionView(Context ctx) {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Suscripción");
        // Añadir la lógica de medios de contacto a un metodo separado
        agregarContactosUsuarioAlModelo(model, ctx);



        String idParam = ctx.pathParam("id");
        Long heladeraId = Long.parseLong(idParam);

        Optional<Heladera> buscada = heladeras.buscarPorId(heladeraId);
        if (buscada.isPresent()) {
            Heladera h = buscada.get();
            HeladeraOutputDTO dto = HeladeraOutputDTO.of(h);
            model.put("heladera", dto);
            RenderUtils.renderizar(ctx,"heladeras/form-suscripcion-heladera.hbs", model);
        } else{
            ctx.redirect("/not-found");
        }
    }

    public void verSuscripcionesAHeladera(Context ctx){
        Long idHeladera = Long.parseLong(ctx.pathParam("id"));
        Long idUsuario = ctx.sessionAttribute("id");
        Optional<Heladera> heladera = heladeras.buscarPorId(idHeladera);
        if(heladera.isEmpty()) {
            ctx.status(404);
            ctx.redirect("/not-found");
        } else{
            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Suscripciones a heladera");
            Heladera h = heladera.get();
            List<SuscripcionOutputDTO> suscripciones = new ArrayList<>();
            h.getSuscriptores().forEach(s -> {
                if(s.getObserverSuscripcion().getId().equals(idUsuario)){
                    SuscripcionOutputDTO dto = SuscripcionOutputDTO.of(s);
                    suscripciones.add(dto);
                }
            });
            model.put("suscripciones", suscripciones);
            model.put("heladera", heladera.get().getNombre());
            model.put("idHeladera", idHeladera);
            RenderUtils.renderizar(ctx,"heladeras/suscripciones.hbs", model);

        }
    }

    public void verSuscripcionesDeUsuario(Context ctx) {
        Long idUsuario = ctx.sessionAttribute("id");
        List<SuscripcionAHeladera> suscripciones = ServiceLocator.instanceOf(SuscripcionesRepository.class).buscarTodos();
        List<SuscripcionOutputDTO> suscripcionesDTO = new ArrayList<>();
        suscripciones.forEach(s -> {
            if(s.getObserverSuscripcion().getId().equals(idUsuario)){
                SuscripcionOutputDTO dto = SuscripcionOutputDTO.of(s);
                suscripcionesDTO.add(dto);
            }
        });

        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Mis suscripciones");
        model.put("suscripciones", suscripcionesDTO);
        RenderUtils.renderizar(ctx,"heladeras/mis-suscripciones.hbs", model);
    }
}

