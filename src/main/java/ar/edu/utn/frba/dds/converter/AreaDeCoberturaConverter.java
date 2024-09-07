package ar.edu.utn.frba.dds.converter;

import ar.edu.utn.frba.dds.models.entities.tecnicos.AreaCobertura;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Calle;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.models.entities.ubicacion.DireccionDTO;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class AreaDeCoberturaConverter implements AttributeConverter<AreaCobertura, String> {
    @Override
    public String convertToDatabaseColumn(AreaCobertura areaCobertura) {
        DireccionConverter converter = new DireccionConverter();
        return areaCobertura != null? converter.convertToDatabaseColumn(areaCobertura.getDireccionRaiz()) + "," + areaCobertura.getMaxDistanciaEnMetros() : null;
    }

    @Override
    public AreaCobertura convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }
        String[] parts = s.split(",");
        Calle calle = new Calle(parts[0]);
        Integer altura = Integer.valueOf(parts[1]);
        Integer comuna = Integer.valueOf(parts[2]);
        Coordenada coord = new Coordenada(Double.valueOf(parts[3]), Double.valueOf(parts[4]));
        Double distancia = Double.valueOf(parts[5]);
        Direccion raiz = Direccion.of(new DireccionDTO(calle, altura, comuna, coord));
        return new AreaCobertura(raiz, distancia);

    }
}
