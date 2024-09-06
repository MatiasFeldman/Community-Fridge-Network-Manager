package ar.edu.utn.frba.dds.converter;

import lombok.NoArgsConstructor;

import javax.persistence.AttributeConverter;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor
public class ChronoUnitConverter implements AttributeConverter<ChronoUnit, String> {

    @Override
    public String convertToDatabaseColumn(ChronoUnit chronoUnit) {
        return switch (chronoUnit) {
            case DAYS -> "Diario";
            case WEEKS -> "Semanal";
            case MONTHS -> "Mensual";
            case YEARS -> "Anual";
            default -> null;
        };
    }

    @Override
    public ChronoUnit convertToEntityAttribute(String s) {
        return switch (s) {
            case "Diario" -> ChronoUnit.DAYS;
            case "Semanal" -> ChronoUnit.WEEKS;
            case "Mensual" -> ChronoUnit.MONTHS;
            case "Anual" -> ChronoUnit.YEARS;
            default -> null;
        };
    }
}