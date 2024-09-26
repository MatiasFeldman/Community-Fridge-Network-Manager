package ar.edu.utn.frba.dds.models.repositories.intentos_de_apertura;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.IntentoAperturaResuelto;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class IntentosDeAperturaRepository {
    private IntentosDeAperturaDAO intentos;

    public void guardar(IntentoAperturaResuelto intento){intentos.guardar(intento);}

    public List<IntentoAperturaResuelto> buscarTodos(){return intentos.buscarTodos();}

    public void eliminar(IntentoAperturaResuelto intento){intentos.eliminar(intento);}

    void modficar(IntentoAperturaResuelto intento){intentos.modficar(intento);}
}
