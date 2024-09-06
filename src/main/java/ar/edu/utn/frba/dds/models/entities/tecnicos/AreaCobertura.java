package ar.edu.utn.frba.dds.models.entities.tecnicos;

import ar.edu.utn.frba.dds.models.entities.helpers.distancia_entre_coordenadas.CalculadoraDistancia;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AreaCobertura {
    private Direccion direccionRaiz;
    private Double maxDistanciaEnMetros;

    public boolean seEncuentraEnRango(Direccion direc) {
        return this.distanciaA(direc) <= maxDistanciaEnMetros;
    }

    public Double distanciaA(Direccion direccion) {
        return CalculadoraDistancia.calcularDistancia(direccionRaiz.getCoordenadas(), direccion.getCoordenadas());
    }
}
