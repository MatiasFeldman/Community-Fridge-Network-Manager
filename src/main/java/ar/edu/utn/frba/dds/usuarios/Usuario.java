package ar.edu.utn.frba.dds.usuarios;

import ar.edu.utn.frba.dds.utils.ValidadorDeContrasenias;

public class Usuario {
    private String user;
    private String password;

    public Usuario(String user, String password) {
        try {
            if(ValidadorDeContrasenias.esValida(password)){
                this.user = user;
                this.password = password;
            }
        } catch (ValidadorDeContrasenias.ContraseniaInvalidaException e) {
            System.out.println("Contraseña invalida: " + e.getMessage());
        }
    }
}
