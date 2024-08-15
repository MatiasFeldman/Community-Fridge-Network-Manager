package ar.edu.utn.frba.dds.exceptions;

public class UsuarioSinTarjetaException extends RuntimeException {
    public UsuarioSinTarjetaException(String mensaje) {
        super(mensaje);
    }
}
