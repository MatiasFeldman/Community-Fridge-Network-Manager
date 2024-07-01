package ar.edu.utn.frba.dds.models.repositories.tecnicos.dao;

import ar.edu.utn.frba.dds.models.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;

import java.util.Optional;

public interface TecnicosDAO {
    Optional<Tecnico> buscarMasCercano(Direccion origen);
}
