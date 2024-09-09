package ar.edu.utn.frba.dds.utils.permisos;

import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.repositories.permisos.dao.PermisosDAO;

public class VerificadorDePermisos {

    private PermisosDAO permisosRepository;

    public VerificadorDePermisos(PermisosDAO permisosRepository) {
        this.permisosRepository = permisosRepository;
    }

    public static void tienePermiso(Usuario usuario, String permiso) {
        if (!usuario.tienePermiso(permiso)) {
            throw new PermisoDenegadoException("El usuario no tiene permisos para realizar esta acción");
        }
    }
}
