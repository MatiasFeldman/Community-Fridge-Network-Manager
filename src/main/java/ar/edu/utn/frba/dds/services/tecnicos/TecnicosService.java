package ar.edu.utn.frba.dds.services.tecnicos;

import ar.edu.utn.frba.dds.dtos.tecnicos.TecnicoDTO;
import ar.edu.utn.frba.dds.models.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.factories.tecnicos.TecnicosFactory;
import ar.edu.utn.frba.dds.models.repositories.tecnicos.TecnicosRepository;
import ar.edu.utn.frba.dds.utils.permisos.VerificadorDePermisos;

public class TecnicosService {
    private TecnicosRepository tecnicosRepository;

    public TecnicosService(VerificadorDePermisos verificadorDePermisos, TecnicosRepository tecnicosRepository){
        this.tecnicosRepository = tecnicosRepository;
    }
    public Object crearTecnico(TecnicoDTO dto, Usuario actual) {
        actual.tienePermiso("CREAR_TECNICO");

        Tecnico creado = (Tecnico) TecnicosFactory.crear(dto);

        tecnicosRepository.guardar(creado);

        return creado;
    }
}
