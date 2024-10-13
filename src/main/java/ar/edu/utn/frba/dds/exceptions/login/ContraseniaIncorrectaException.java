package ar.edu.utn.frba.dds.exceptions.login;

public class ContraseniaIncorrectaException extends RuntimeException {
    public ContraseniaIncorrectaException(String message) {
        super(message);
    }
}
