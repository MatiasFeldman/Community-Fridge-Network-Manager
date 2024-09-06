package ar.edu.utn.frba.dds.converter;

import ar.edu.utn.frba.dds.models.entities.tecnicos.AreaCobertura;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class AreaDeCoberturaConverter implements AttributeConverter<AreaCobertura, String> {
    @Override
    public String convertToDatabaseColumn(AreaCobertura areaCobertura) {
        return areaCobertura != null? areaCobertura.getDireccionRaiz() + "," + areaCobertura.getMaxDistanciaEnMetros() : null;
    }

    @Override
    public AreaCobertura convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }
        String[] parts = s.split(",");
        return new AreaCobertura(parts[0], Double.parseDouble(parts[1]));
    }
}
