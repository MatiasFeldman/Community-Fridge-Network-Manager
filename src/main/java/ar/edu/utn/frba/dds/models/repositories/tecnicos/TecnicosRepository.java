package ar.edu.utn.frba.dds.models.repositories.tecnicos;

import ar.edu.utn.frba.dds.models.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.models.repositories.tecnicos.dao.TecnicosDAO;

import java.util.Optional;

public class TecnicosRepository {
    TecnicosDAO tecnicosDAO;
    public void guardar(Tecnico creado) {
    }

    public Optional<Tecnico> buscarMasCercano(Direccion origen) {
        return tecnicosDAO.buscarMasCercano(origen);
    }
}
