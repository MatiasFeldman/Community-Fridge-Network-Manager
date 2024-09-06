package ar.edu.utn.frba.dds.converter;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Frecuencia;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Converter(autoApply = true)
public class FrecuenciaConverter implements AttributeConverter<Frecuencia, String>{
    @Override
    public String convertToDatabaseColumn(Frecuencia frecuencia) {
        if (frecuencia == null) return null;
        ChronoUnitConverter conversor_unidad = new ChronoUnitConverter();
        String unidad_string = conversor_unidad.convertToDatabaseColumn(frecuencia.getUnidad());
        return unidad_string + "," + frecuencia.getFrecuencia() + "," + frecuencia.getFechaUltimaDonacion();
    }

    @Override
    public Frecuencia convertToEntityAttribute(String s) {
        if (s == null) return null;
        ChronoUnitConverter conversor_unidad = new ChronoUnitConverter();
        String[] partes = s.split(",");
        ChronoUnit unidad = conversor_unidad.convertToEntityAttribute(partes[0]);
        Integer frecuencia = Integer.parseInt(partes[1]);
        LocalDate fecha = LocalDate.parse(partes[2]);
        return new Frecuencia(unidad, frecuencia, fecha);
    }
}
