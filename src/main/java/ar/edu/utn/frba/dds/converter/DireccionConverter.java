package ar.edu.utn.frba.dds.converter;

import ar.edu.utn.frba.dds.models.entities.ubicacion.Calle;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.models.entities.ubicacion.DireccionDTO;

import javax.persistence.AttributeConverter;

public class DireccionConverter implements AttributeConverter<Direccion, String> {
    @Override
    public String convertToDatabaseColumn(Direccion direccion) {
        if (direccion == null) {
            return null;
        }
        return direccion.getCalle() + "," + direccion.getAltura() + "," +
                direccion.getComuna();
    }

    @Override
    public Direccion convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        String[] parts = dbData.split(",");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Datos de dirección inválidos");
        }

        Calle calle = new Calle(parts[0]);
        int altura = Integer.parseInt(parts[1]);
        int comuna = Integer.parseInt(parts[2]);

        Direccion direccion = Direccion.of(new DireccionDTO(calle, altura, comuna));

        return direccion;
    }
}
