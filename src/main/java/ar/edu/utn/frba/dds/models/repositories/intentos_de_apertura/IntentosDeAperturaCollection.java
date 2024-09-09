package ar.edu.utn.frba.dds.models.repositories.intentos_de_apertura;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.IntentoAperturaResuelto;

import java.util.List;

public class IntentosDeAperturaCollection implements IntentosDeAperturaDAO{
    private List<IntentoAperturaResuelto> intentos;

    @Override
    public void guardar(IntentoAperturaResuelto intento){
        intentos.add(intento);
    }

    @Override
    public List<IntentoAperturaResuelto> buscarTodos(){return intentos;}

    @Override
    public void eliminar(IntentoAperturaResuelto intento){
        intentos.remove(intento);
    }

    @Override
    public void modficar(IntentoAperturaResuelto intento) {

    }
}
