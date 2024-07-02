package ar.edu.utn.frba.dds.models.repositories.tecnicos.dao;

import ar.edu.utn.frba.dds.models.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;

import java.util.List;
import java.util.Optional;

public class TecnicosCollection {
    private List<Tecnico> tecnicos;
    public void guardar(Tecnico creado) {
    }

    public Optional<Tecnico> buscarMasCercano(Direccion origen) {
        Optional<Tecnico> tecnicoConMismaDirec = tecnicos.stream().filter(tecnico -> tecnico.getAreaCobertura().equals(origen)).findFirst();
        if (tecnicoConMismaDirec.isPresent()) {
            return tecnicoConMismaDirec;
        } else{
            return tecnicos.stream().filter(tecnico -> tecnico.puedeIrA(origen.getComuna())).findFirst();
        }
    }
}
