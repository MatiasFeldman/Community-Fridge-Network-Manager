package ar.edu.utn.frba.dds.models.entities.usuarios;
import ar.edu.utn.frba.dds.exceptions.ContraseniaInseguraException;
import ar.edu.utn.frba.dds.utils.seguridad.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import java.io.IOException;
import java.util.Random;

import javax.persistence.*;

@NoArgsConstructor(force = true)
@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario {
    @Column(name = "usuario",unique = true, nullable = false)
    private final String user;
    @Column(name = "contrasenia", nullable = false)
    private String password;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "rol_de_usuario",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_rol")
    )
    private List<Rol> roles;

    public Usuario(String user, String password ,List<Rol> roles) throws IOException {
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

            }

    }

    public boolean tienePermiso(String permiso) {
        for (Rol rol : roles) {
            if (rol.tienePermiso(permiso)) {
                return true;
            }
        }
        return false;
    }



}
