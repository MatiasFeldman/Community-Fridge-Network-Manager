package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.exceptions.ContraseniaIncorrectaException;
import ar.edu.utn.frba.dds.exceptions.UsuarioIncorrectoException;
import ar.edu.utn.frba.dds.models.entities.usuarios.Rol;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.repositories.usuarios.UsuariosRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import io.javalin.http.Context;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UsuariosController {
    public void handleLogin(Context ctx){
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        UsuariosRepository usuariosRepository = ServiceLocator.instanceOf(UsuariosRepository.class);
        Optional<Usuario> user = usuariosRepository.buscarPorEmail(email);

        if(user.isPresent()) {
            Usuario usuarioEncontrado = user.get();
            if(usuarioEncontrado.getPassword().equals(password)){
                ctx.sessionAttribute("user", usuarioEncontrado.getUser()); // me guardo el usuario

                List<String> nombresRoles = Optional.ofNullable(usuarioEncontrado.getRoles())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(Rol::getNombre)
                        .collect(Collectors.toList());

                // Guardar roles en la sesión
                ctx.sessionAttribute("roles", nombresRoles); // me guardo los roles del usuario

                ctx.redirect("/");
            } else {
                throw new ContraseniaIncorrectaException();
            }
        } else {
            throw new UsuarioIncorrectoException();
        }
    }

    public void handleLogout(Context ctx){
        ctx.sessionAttribute("user", null);
        ctx.sessionAttribute("roles", null);
        ctx.redirect("/");
    }
}
