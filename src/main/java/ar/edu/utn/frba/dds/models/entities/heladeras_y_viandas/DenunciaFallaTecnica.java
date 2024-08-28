package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DenunciaFallaTecnica {
    private UUID denunciante;
    private String descripcion = null;
    private String foto = null;
    private LocalDateTime fecha;
    @Setter
    private Heladera heladera;



    public static DenunciaFallaTecnica of(UUID denunciante, String descripcion, String foto, LocalDateTime fecha, Heladera heladera){
        return DenunciaFallaTecnica
                .builder()
                .denunciante(denunciante)
                .fecha(fecha)
                .descripcion(descripcion)
                .foto(foto)
                .heladera(heladera)
                .build();
    }

    public static DenunciaFallaTecnica of(UUID denunciante, String descripcion, String foto, LocalDateTime fecha){
        return DenunciaFallaTecnica
                .builder()
                .denunciante(denunciante)
                .fecha(fecha)
                .descripcion(descripcion)
                .foto(foto)
                .build();
    }

}
