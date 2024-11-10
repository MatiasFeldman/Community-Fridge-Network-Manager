package ar.edu.utn.frba.dds.exceptions.registroPersonaVulnerable;

public class RegistroTarjetaInexistenteException extends RuntimeException{
    public RegistroTarjetaInexistenteException(String message) {
        super(message);
    }
}
