package ar.edu.utn.frba.dds.models.entities.colaboraciones.carga_masiva;

import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import lombok.Getter;

@Getter
public class UsuarioConPassword {
    private Usuario usuario;
    private String passwordSinHash;

    public UsuarioConPassword(Usuario usuario, String passwordSinHash) {
        this.usuario = usuario;
        this.passwordSinHash = passwordSinHash;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getPasswordSinHash() {
        return passwordSinHash;
    }
}