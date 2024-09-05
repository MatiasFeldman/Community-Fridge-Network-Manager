package ar.edu.utn.frba.dds.models.repositories.tecnicos.dao;

import ar.edu.utn.frba.dds.models.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class TecnicosCollection {
    private List<Tecnico> tecnicos;

    public void guardar(Tecnico creado) {
    }

    public Optional<Tecnico> buscarMasCercano(Direccion origen) {
        Optional<Tecnico> tecnicoConMismaDirec = tecnicos.stream().filter(tecnico -> tecnico.getAreaCobertura().getDireccionRaiz().equals(origen)).findFirst();
        if (tecnicoConMismaDirec.isPresent()) {
            return tecnicoConMismaDirec;
        } else {
            List<Tecnico> tecnicosQuePuedenIr = tecnicos
                    .stream()
                    .filter(t -> t.puedeIrA(origen))
                    .toList();
            if (tecnicosQuePuedenIr.isEmpty()) {
                return Optional.empty();
            } else {
                return tecnicosQuePuedenIr
                        .stream()
                        .min(Comparator.comparing(t -> t.distanciaA(origen)));
            }
        }
    }
}
