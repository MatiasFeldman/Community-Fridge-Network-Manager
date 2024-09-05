package ar.edu.utn.frba.dds.utils.permisos;

import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.repositories.permisos.PermisosRepository;

public class VerificadorDePermisos {

    private PermisosRepository permisosRepository;

    public VerificadorDePermisos(PermisosRepository permisosRepository) {
        this.permisosRepository = permisosRepository;
    }

    public static void tienePermiso(Usuario usuario, String permiso) {
        if (!usuario.tienePermiso(permiso)) {
            throw new PermisoDenegadoException("El usuario no tiene permisos para realizar esta acción");
        }
    }
}
