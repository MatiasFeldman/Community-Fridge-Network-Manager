package ar.edu.utn.frba.dds.models.repositories.intentos_de_apertura;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Oferta;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.IntentoAperturaResuelto;

import java.util.List;

public interface IntentosDeAperturaDAO {
    public void guardar(IntentoAperturaResuelto intento);

    public List<IntentoAperturaResuelto> buscarTodos();

    public void eliminar(IntentoAperturaResuelto intento);

    void modficar(IntentoAperturaResuelto intento);
}
