package ar.edu.utn.frba.dds.models.entities.ubicacion;

import ar.edu.utn.frba.dds.models.entities.helpers.distancia_entre_coordenadas.CalculadoraDistancia;
import ar.edu.utn.frba.dds.services.georef.GobiernoAPI;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Builder
@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Direccion {

    @Column(name = "direc")
    private String direccion;

    @Embedded
    private Comuna comuna;

    @Embedded
    private Provincia provincia;

    @Embedded
    @Setter
    private Coordenada coordenadas;

    public static Direccion of(String direccion, String provincia){
        GobiernoAPI api = new GobiernoAPI();
        GeoRefDeDirecc geoRefDeDirecc = api.getCoordYComuna(direccion, provincia);

        if (geoRefDeDirecc == null) {
            return null;
        }

        return Direccion
                .builder()
                .direccion(direccion)
                .coordenadas(geoRefDeDirecc.getCoords())
                .provincia(geoRefDeDirecc.getProvincia())
                .comuna(geoRefDeDirecc.getComuna())
                .build();
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

