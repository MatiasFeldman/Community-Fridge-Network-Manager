package ar.edu.utn.frba.dds.models.entities.suscripciones.tipo_suscripciones;

import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.Mensaje;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SufrioDesperfecto implements Suscripcion {
    public String cuerpo = "La heladera sufrió un desperfecto";
    public  String destinatario;

    public static SufrioDesperfecto of(String destinatario) {
        return SufrioDesperfecto
                .builder()
                .destinatario(destinatario)
                .build();
    }

    // falta implementar el metodo de getMensaje
    @Override
    public Mensaje getMensaje() {
        return new Mensaje(destinatario, cuerpo);
    }

    @Override
    public boolean verificarCondicion(Integer capActual, Integer cantActual) {
        return capActual == -1 || cantActual == -1;
    }
}