package ar.edu.utn.frba.dds.models.repositories.intentos_de_apertura;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.IntentoAperturaResuelto;

import java.util.List;

public class IntentosDeAperturaRepository {
    private List<IntentoAperturaResuelto> intentos;

    public void guardar(IntentoAperturaResuelto intento){
        intentos.add(intento);
    }
}
