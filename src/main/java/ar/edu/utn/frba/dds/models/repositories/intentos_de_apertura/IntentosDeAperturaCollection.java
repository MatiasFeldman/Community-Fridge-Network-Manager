package ar.edu.utn.frba.dds.models.repositories.intentos_de_apertura;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.IntentoAperturaResuelto;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class IntentosDeAperturaCollection implements IntentosDeAperturaDAO{
    private List<IntentoAperturaResuelto> intentos;
    private Long currentId = 100L;

    @Override
    public void guardar(IntentoAperturaResuelto intento){
        intento.setId(currentId);
        intentos.add(intento);
        currentId++;
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
