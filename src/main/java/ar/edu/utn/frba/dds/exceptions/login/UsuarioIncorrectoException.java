package ar.edu.utn.frba.dds.exceptions.login;

public class UsuarioIncorrectoException extends RuntimeException {
    public UsuarioIncorrectoException(String message) {
        super(message);
    }
}
