package ar.edu.utn.frba.dds.models.entities.usuarios;

import ar.edu.utn.frba.dds.converter.SendingStrategyConverter;
import ar.edu.utn.frba.dds.exceptions.ContraseniaInseguraException;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.Mensaje;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.SendingStrategy;
import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import ar.edu.utn.frba.dds.utils.seguridad.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.persistence.*;

@NoArgsConstructor(force = true)
@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario extends Persistente {

    @Column(name = "usuario", unique = true, nullable = false)
    private String user;
    @Column(name = "contrasenia", nullable = false)
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "rol_de_usuario",
            joinColumns = @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")
    )
    private List<Rol> roles;

    @Convert(converter = SendingStrategyConverter.class)
    @Column(name = "estrategia_de_envio")
    private SendingStrategy strategiaDeEnvio = null;

    @SneakyThrows
    public Usuario(String user, String password, List<Rol> roles) {
            this.user = user;
            this.password = password;
            this.roles = roles;
            this.strategiaDeEnvio = null;
    }

    @SneakyThrows
    public Usuario(String user, String password) {
        this.user = user;
        this.password = password;
        this.roles = new ArrayList<>();
        this.strategiaDeEnvio = null;
    }

    public boolean tienePermiso(String permiso) {
        for (Rol rol : roles) {
            if (rol.tienePermiso(permiso)) {
                return true;
            }
        }
        return false;
    }

    public void serNotificado(Mensaje mensaje) throws MessagingException, IOException {
        strategiaDeEnvio.enviarMensaje(mensaje);
    }


}
