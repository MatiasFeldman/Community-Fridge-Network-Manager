package ar.edu.utn.frba.dds.converter;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.PuntoDeHeladera;

import javax.persistence.AttributeConverter;

public class PuntoDeHeladeraConverter implements AttributeConverter<PuntoDeHeladera, String> {
    @Override
    public String convertToDatabaseColumn(PuntoDeHeladera puntoDeHeladera) {
        return (puntoDeHeladera != null) ? puntoDeHeladera.getNombreDePunto() : null;
    }

    @Override
    public PuntoDeHeladera convertToEntityAttribute(String dbData) {
        return (dbData != null) ? new PuntoDeHeladera(dbData) : null;
    }
}
