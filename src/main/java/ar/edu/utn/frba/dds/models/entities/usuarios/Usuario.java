package ar.edu.utn.frba.dds.models.entities.usuarios;
import ar.edu.utn.frba.dds.exceptions.ContraseniaInseguraException;
import ar.edu.utn.frba.dds.utils.seguridad.*;
import lombok.Getter;
import java.util.UUID;

import java.io.IOException;

public class Usuario {
    @Getter
    private final String user;
    private String password;
    @Getter
    private UUID id;
    @Getter
    private Rol rol;

    public Usuario(String user, String password, UUID id ,Rol rol) throws IOException {
            ValidadorDeContrasenias validador = new ValidadorDeContrasenias();
            validador.agregarCondiciones(new CumpleLongitud(8,64),
                                         new TieneMayuscula(),
                                         new TieneMinuscula(),
                                         new TieneNumero(),
                                         new TieneCaracterEspecial(),
                                         new NoEstaDentroDeLasComunes());

            if (!validador.esValida(password)) {
                throw new ContraseniaInseguraException("La contraseña no cumple con los requisitos mínimos");
            } else{
                this.user = user;
                this.password = password;
                this.rol = rol;
                this.id = id;
            }

    }

}
