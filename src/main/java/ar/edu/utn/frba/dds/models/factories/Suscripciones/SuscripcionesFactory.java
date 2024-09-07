package ar.edu.utn.frba.dds.models.factories.Suscripciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import ar.edu.utn.frba.dds.models.entities.suscripciones.SuscripcionAHeladera;
import ar.edu.utn.frba.dds.models.entities.suscripciones.TIPOSUSCRIPCION;
import ar.edu.utn.frba.dds.models.entities.suscripciones.tipo_suscripciones.HeladeraLlena;
import ar.edu.utn.frba.dds.models.entities.suscripciones.tipo_suscripciones.SufrioDesperfecto;
import ar.edu.utn.frba.dds.models.entities.suscripciones.tipo_suscripciones.Suscripcion;
import ar.edu.utn.frba.dds.models.entities.suscripciones.tipo_suscripciones.ViandasDisponibles;
import com.fasterxml.jackson.databind.JsonNode;

public class SuscripcionesFactory {

    public static void crearSuscripcion(Humano humano, Heladera heladera, JsonNode suscripcionNode, String medioDeContacto){
        TIPOSUSCRIPCION tipo = TIPOSUSCRIPCION.valueOf(suscripcionNode.get("tipo").asText());
        Integer cantidad = suscripcionNode.get("cantidad").asInt();
        String valorMedioDeContacto = humano.getMedioDeContacto(medioDeContacto);

        if (medioDeContacto == null){
            throw new IllegalArgumentException("Medio de contacto no valido");
        }

        Suscripcion suscripcion = create(tipo, cantidad, valorMedioDeContacto);
        SuscripcionAHeladera suscripcionAHeladera = new SuscripcionAHeladera(humano.getUser(), suscripcion);

        heladera.suscribir(suscripcionAHeladera);

    }

    public static void crearSuscripcion(Juridica juridica, Heladera heladera, JsonNode suscripcionNode, String medioDeContacto){
        TIPOSUSCRIPCION tipo = TIPOSUSCRIPCION.valueOf(suscripcionNode.get("tipo").asText());
        Integer cantidad = suscripcionNode.get("cantidad").asInt();
        String valorMedioDeContacto = juridica.getMedioDeContacto(medioDeContacto);

        if (medioDeContacto == null){
            throw new IllegalArgumentException("Medio de contacto no valido");
        }

        Suscripcion suscripcion = create(tipo, cantidad, valorMedioDeContacto);
        SuscripcionAHeladera suscripcionAHeladera = new SuscripcionAHeladera(juridica.getUser(), suscripcion);

        heladera.suscribir(suscripcionAHeladera);

    }

    public static Suscripcion create(TIPOSUSCRIPCION tipo, Integer cantidad, String destinatario) {
        switch (tipo){
            case DESPERFECTO -> {
                return SufrioDesperfecto.of(destinatario);
            }
            case VIANDAS_DISPONIBLES -> {
                return ViandasDisponibles.of(destinatario, cantidad);
            }
            case VIANDAS_PARA_LLENADO -> {
                return HeladeraLlena.of(destinatario, cantidad);
            }
            default -> {
                return null;
            }
        }
    }


}
