package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dtos.AtributoOutputDTO;
import ar.edu.utn.frba.dds.dtos.direccion.DireccionInputDTO;
import ar.edu.utn.frba.dds.dtos.humanos.HumanoInputDTO;
import ar.edu.utn.frba.dds.exceptions.SolicitudIncorrectaException;
import ar.edu.utn.frba.dds.exceptions.registro_usuario.ContraseniaHumanoInseguraException;
import ar.edu.utn.frba.dds.exceptions.registro_usuario.ContraseniaJuridicaInseguraException;
import ar.edu.utn.frba.dds.exceptions.registro_usuario.UsuarioHumanoExistenteException;
import ar.edu.utn.frba.dds.exceptions.registro_usuario.UsuarioJuridicaExistenteException;
import ar.edu.utn.frba.dds.models.entities.helpers.conversor_json.ConversorJSON;
import ar.edu.utn.frba.dds.models.entities.personas.Atributo;
import ar.edu.utn.frba.dds.models.entities.personas.AtributoHumanoRespondido;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.models.entities.usuarios.TipoRol;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.factories.direcciones.DireccionFactory;
import ar.edu.utn.frba.dds.models.repositories.atributos_humano.AtributosHumanoRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.juridicas.JuridicasRepository;
import ar.edu.utn.frba.dds.models.repositories.usuarios.UsuariosRepository;
import ar.edu.utn.frba.dds.server.Server;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import ar.edu.utn.frba.dds.utils.MapeadorAtributos;
import ar.edu.utn.frba.dds.utils.RenderUtils;
import ar.edu.utn.frba.dds.utils.ValidadorUsernames;
import ar.edu.utn.frba.dds.utils.seguridad.HashPassword;
import ar.edu.utn.frba.dds.utils.seguridad.ValidadorDeContrasenias;
import com.fasterxml.jackson.databind.JsonNode;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.*;
import java.util.stream.Collectors;

public class HumanosController {
    public Object crear(Object solicitud){
        HumanoInputDTO dto = (HumanoInputDTO) solicitud;

        return ColaboradorHumano.create(dto);
    }

    public void formRegistroHumano(Context context){
        List<Atributo> atributos = ServiceLocator.instanceOf(AtributosHumanoRepository.class).buscarTodas();
        //sacamos los atributos que no van en el formulario
        List<Atributo> atributosFiltrados = atributos.stream()
                .filter(atributo ->
                        !atributo.getNombre().equalsIgnoreCase("Tipo Documento") &&
                                !atributo.getNombre().equalsIgnoreCase("Documento"))
                .collect(Collectors.toList());

        List<AtributoOutputDTO> dtos = new ArrayList<>();

        atributosFiltrados.forEach(a -> dtos.add(AtributoOutputDTO.of(a)));

        Map<String, Object> model = new HashMap<>();
        model.put("campos", dtos);

        RenderUtils.renderizar(context,"registro-usuario/registro-humano.hbs", model);
    }

    public void camposFormHumano(Context context){
        List<Atributo> atributos = ServiceLocator.instanceOf(AtributosHumanoRepository.class).buscarTodas();
        List<AtributoOutputDTO> dtos = new ArrayList<>();

        atributos.forEach(a -> dtos.add(AtributoOutputDTO.of(a)));

        Map<String, Object> model = new HashMap<>();
        model.put("campos", dtos);

        RenderUtils.renderizar(context,"registro-usuario/modif-registro-humano.hbs", model);
    }

    public void save(Context context){

        String password = context.formParam("password");

        ValidadorDeContrasenias validador = ServiceLocator.instanceOf(ValidadorDeContrasenias.class);

        if (!validador.esValida(password)){
            context.status(HttpStatus.BAD_REQUEST);
            String motivoInvalidez = validador.condicionQueNoCumple(password).get().getMensaje();

            throw new ContraseniaHumanoInseguraException(motivoInvalidez);
        }

        String username = context.formParam("user");

        if(ValidadorUsernames.existe(username, "Humano")){
            context.status(HttpStatus.BAD_REQUEST);

            System.out.println("El nombre de usuario ya existe");

            throw new UsuarioHumanoExistenteException("El nombre de usuario ya existe");
        }

        List<Atributo> atributos = ServiceLocator.instanceOf(AtributosHumanoRepository.class).buscarTodas();
        List<AtributoHumanoRespondido> atributosRespondidos = new ArrayList<>();
        String direccionValor = null;
        String provinciaValor = null;

        for (Atributo atributo : atributos) {

            String nombreAtributo = atributo.getNombre();
            String valorFormulario = context.formParam(nombreAtributo);

            AtributoHumanoRespondido atributoRespondido = MapeadorAtributos.mapear(valorFormulario, nombreAtributo);
            atributosRespondidos.add(atributoRespondido);

            if ("direccion".equalsIgnoreCase(nombreAtributo)) {
                direccionValor = valorFormulario;
            }
            if ("provincia".equalsIgnoreCase(nombreAtributo)) {
                provinciaValor = valorFormulario;
            }
        }

        HashPassword hash = ServiceLocator.instanceOf(HashPassword.class);
        String passwordHashed = hash.hashPassword(password);

        Usuario user = new Usuario(username, passwordHashed, List.of(TipoRol.HUMANO));


        HumanoInputDTO dto = HumanoInputDTO.create(user,  atributosRespondidos.toArray(new AtributoHumanoRespondido[0]));
        if (direccionValor != null && !direccionValor.isEmpty() && provinciaValor != null && !provinciaValor.isEmpty()) {
            Direccion direccion = DireccionFactory.create(new DireccionInputDTO(direccionValor, provinciaValor));
            dto.setDireccion(direccion);
        } else {
            dto.setDireccion(null);
        }

        ColaboradorHumano colaborador = ColaboradorHumano.create(dto);
        ServiceLocator.instanceOf(UsuariosRepository.class).guardar(user);
        ServiceLocator.instanceOf(HumanosRepository.class).guardar(colaborador);
        System.out.print("recibimos el formulario");
        context.redirect("/");
    }

}
