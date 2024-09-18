package ar.edu.utn.frba.dds.models.entities.ubicacion;

import ar.edu.utn.frba.dds.models.entities.helpers.distancia_entre_coordenadas.CalculadoraDistancia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Builder
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Direccion {

    @Embedded
    private Calle calle;

    @Column(name = "altura")
    private Integer altura;

    @Embedded
    private Coordenada coordenadas;

    public static Direccion of(DireccionDTO dto){
        return Direccion
                .builder()
                .calle(dto.getCalle())
                .altura(dto.getAltura())
                .coordenadas(dto.getCoordenada())
                .build();
    }

    public static Direccion of(String calle, Integer altura){
        return Direccion
                .builder()
                .calle(new Calle(calle))
                .altura(altura)
                .build();
    }

    public String direccionCompleta() {
        return calle.getNombre() + " " + altura;
    }

    public boolean esCercaDe(Direccion dire) {
        return CalculadoraDistancia.calcularDistancia(this.coordenadas, dire.coordenadas) <= 100.0;
    }

    public Double getLatitud() {
        return coordenadas.getLatitud();
    }

    public Double getLongitud() {
        return coordenadas.getLongitud();
    }

}

