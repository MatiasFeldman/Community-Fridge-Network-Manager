package ar.edu.utn.frba.dds.models.entities.tecnicos;

import ar.edu.utn.frba.dds.models.entities.helpers.distancia_entre_coordenadas.CalculadoraDistancia;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AreaCobertura {
    @Embedded
    private Direccion direccionRaiz;

    @Column(name = "max_distancia_en_metros")
    private Double maxDistanciaEnMetros;

    public boolean seEncuentraEnRango(Direccion direc) {
        return this.distanciaA(direc) <= maxDistanciaEnMetros;
    }

    public Double distanciaA(Direccion direccion) {
        return CalculadoraDistancia.calcularDistancia(direccionRaiz.getCoordenadas(), direccion.getCoordenadas());
    }
}
