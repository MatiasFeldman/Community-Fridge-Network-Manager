package ar.edu.utn.frba.dds.models.entities.usuarios;

import ar.edu.utn.frba.dds.converter.SendingStrategyConverter;
import ar.edu.utn.frba.dds.exceptions.ContraseniaInseguraException;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.Mensaje;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.SendingStrategy;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.whatsapp.WhatsAppSender;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.whatsapp.WhatsAppSendingStrategy;
import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import ar.edu.utn.frba.dds.utils.seguridad.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.security.Provider.Service;

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
    @Column(name = "foto", nullable = false)
    private String foto;


    @ElementCollection(targetClass = TipoRol.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING) // Indica que el enum se almacenará como una cadena
    @CollectionTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "roles")
    private List<TipoRol> roles;

    @Convert(converter = SendingStrategyConverter.class)
    @Column(name = "estrategia_de_envio")
    private SendingStrategy strategiaDeEnvio = null;

    @SneakyThrows
    public Usuario(String user, String password, List<TipoRol> roles) {
            this.user = user;
            this.password = password;
            this.foto = "/imagenes/fotosUsuarios/user.png";
            this.roles = roles;
            this.strategiaDeEnvio = null;
    }

    @SneakyThrows
    public Usuario(String user, String password) {
        this.user = user;
        this.password = password;
        this.foto = "/imagenes/fotosUsuarios/user.png";
        this.roles = new ArrayList<>();
        this.strategiaDeEnvio = null;
    }

    public void serNotificado(Mensaje mensaje) throws MessagingException, IOException {
        WhatsAppSendingStrategy wpp = new WhatsAppSendingStrategy();
        wpp.enviarMensaje(mensaje);
    }


}
