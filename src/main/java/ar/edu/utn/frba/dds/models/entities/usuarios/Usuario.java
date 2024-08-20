package ar.edu.utn.frba.dds.models.entities.usuarios;
import ar.edu.utn.frba.dds.exceptions.ContraseniaInseguraException;
import ar.edu.utn.frba.dds.utils.seguridad.*;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

import java.io.IOException;

@Getter
public class Usuario {
    private final String user;
    private String password;
    private UUID id;
    private List<Rol> roles;

    public Usuario(String user, String password, UUID id ,List<Rol> roles) throws IOException {
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
                this.roles = roles;
                this.id = id;
            }

    }
    public boolean tienePermiso(Permiso permiso) {
        for (Rol rol : roles) {
            if (rol.tienePermiso(permiso)) {
                return true;
            }
        }
        return false;
    }

}
