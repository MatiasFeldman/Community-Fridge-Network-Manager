package ar.edu.utn.frba.dds.usuarios;

import ar.edu.utn.frba.dds.utils.ValidadorDeContrasenias;

public class Usuario {
    private String user;
    private String password;

    public Usuario(String user, String password) throws ContraseniaInvalidaException {
        this.user = user;
        if(!ValidadorDeContrasenias.esValida(password)){
            throw new ContraseniaInvalidaException("La contraseña no es segura");
        }
        this.password = password;
    }

    public static class ContraseniaInvalidaException extends Exception {
        public ContraseniaInvalidaException(String message) {
            super(message);
        }
    }
}
