package ar.edu.utn.frba.dds.exceptions.registro_usuario;

public class ContraseniaHumanoInseguraException extends RuntimeException {
    public ContraseniaHumanoInseguraException(String message) {
        super(message);
    }
}
