package ar.edu.utn.frba.dds.models.entities.suscripciones.tipo_suscripciones;


import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.Mensaje;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
@Entity
@DiscriminatorValue("heladera_llena")
public class HeladeraLlena extends Suscripcion {

    @Column(name="cantidad_de_viandas_faltantes")
    private Integer cantidadViandasFaltantes;

    public static HeladeraLlena of(String destinatario, int cantidadViandasFaltantes) {
        return HeladeraLlena
                .builder()
                .destinatario(destinatario)
                .cuerpo("La heladera tiene la cantidad de viandas esperadas")
                .cantidadViandasFaltantes(cantidadViandasFaltantes)
                .build();
    }

    @Override
    public Boolean verificarCondicion(Integer capActual, Integer cantActual) {
        return Objects.equals(cantidadViandasFaltantes, capActual);
    }
}
