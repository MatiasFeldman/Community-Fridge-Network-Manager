package ar.edu.utn.frba.dds.utils.permisos;

import ar.edu.utn.frba.dds.models.entities.usuarios.Permiso;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.repositories.IPermisosRepository;

import java.util.Optional;

public class VerificadorDePermisos {

    private IPermisosRepository permisosRepository;

    public VerificadorDePermisos(IPermisosRepository permisosRepository) {
        this.permisosRepository = permisosRepository;
    }

    public void verificarSiUsuarioPuede(String accion, Usuario usuario){
        Optional<Permiso> permisoBuscado = this.permisosRepository.buscarPorNombre(accion);

        if(permisoBuscado.isEmpty())
            throw new PermisoInexistenteException("El permiso " + accion + " no existe");

        Permiso permiso = permisoBuscado.get();

        if(!usuario.getRol().tienePermiso(permiso))
            throw new PermisoDenegadoException("El usuario no tiene permisos para realizar la accion " + accion);
    }
}
