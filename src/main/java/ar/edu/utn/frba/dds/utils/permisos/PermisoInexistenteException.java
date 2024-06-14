package ar.edu.utn.frba.dds.utils.permisos;

public class PermisoInexistenteException extends RuntimeException {
    public PermisoInexistenteException(String message) {
        super(message);
    }
}
