package ar.edu.utn.frba.dds.converter;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Rubro;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RubroConverter implements AttributeConverter<Rubro, String> {

    @Override
    public String convertToDatabaseColumn(Rubro rubro) {
        return rubro != null? rubro.getNombre() : null;
    }

    @Override
    public Rubro convertToEntityAttribute(String s) {
        return new Rubro(s);
    }
}
