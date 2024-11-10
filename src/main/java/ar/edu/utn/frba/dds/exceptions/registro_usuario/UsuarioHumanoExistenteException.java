package ar.edu.utn.frba.dds.exceptions.registro_usuario;

public class UsuarioHumanoExistenteException extends RuntimeException {
    public UsuarioHumanoExistenteException(String message) {
        super(message);
    }
}
