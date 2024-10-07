package ar.edu.utn.frba.dds.models.entities.usuarios;

import io.javalin.security.RouteRole;

public enum TipoRol implements RouteRole {
    ADMIN,
    HUMANO,
    JURIDICA
}
