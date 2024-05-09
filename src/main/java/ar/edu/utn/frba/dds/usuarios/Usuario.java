package ar.edu.utn.frba.dds.usuarios;

import ar.edu.utn.frba.dds.seguridad.*;

public class Usuario {
    private String user;
    private String password;

    public Usuario(String user, String password) {
        try {
            ValidadorDeContrasenias validador = new ValidadorDeContrasenias();
            validador.agregarCondiciones(new CumpleLongitud(8,64),
                                         new TieneMayuscula(),
                                         new TieneMinuscula(),
                                         new TieneNumero(),
                                         new TieneCaracterEspecial(),
                                         new NoEstaDentroDeLasComunes());

            if (!validador.esValida(password)) {
                throw new Exception("La contraseña no cumple con los requisitos mínimos");
            } else{
                this.user = user;
                this.password = password;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
