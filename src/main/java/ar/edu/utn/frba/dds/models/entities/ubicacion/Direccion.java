package ar.edu.utn.frba.dds.models.entities.ubicacion;

import ar.edu.utn.frba.dds.models.entities.helpers.distancia_entre_coordenadas.CalculadoraDistancia;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Direccion {
    private Calle calle;
    private Integer altura;
    private Coordenada coordenadas;
    private Integer comuna;

    public static Direccion of(DireccionDTO dto){
        return Direccion
                .builder()
                .calle(dto.getCalle())
                .altura(dto.getAltura())
                .comuna(dto.getComuna())
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

