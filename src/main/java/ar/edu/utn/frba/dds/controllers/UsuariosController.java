package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dtos.humanos.HumanoOutputDTO;
import ar.edu.utn.frba.dds.exceptions.ContraseniaIncorrectaException;
import ar.edu.utn.frba.dds.exceptions.UsuarioIncorrectoException;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import ar.edu.utn.frba.dds.models.entities.usuarios.Rol;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.juridicas.JuridicasRepository;
import ar.edu.utn.frba.dds.models.repositories.usuarios.UsuariosRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import io.javalin.http.Context;

import java.util.*;
import java.util.stream.Collectors;

public class UsuariosController {
    public void handleLogin(Context ctx){
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");

        UsuariosRepository usuariosRepository = ServiceLocator.instanceOf(UsuariosRepository.class);
        Optional<Usuario> user = usuariosRepository.buscarPorUsername(username);

        if(user.isPresent()) {
            Usuario usuarioEncontrado = user.get();
            if(usuarioEncontrado.getPassword().equals(password)){
                ctx.sessionAttribute("user", usuarioEncontrado.getId());

                List<String> nombresRoles = Optional.ofNullable(usuarioEncontrado.getRoles())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(Rol::getNombre)
                        .collect(Collectors.toList());

                // Guardar roles en la sesión
                ctx.sessionAttribute("roles", nombresRoles); // me guardo los roles del usuario

                ctx.redirect("/");
            } else {
                throw new ContraseniaIncorrectaException("La contraseña es incorrecta");
            }
        } else {
            throw new UsuarioIncorrectoException("El usuario no existe");
        }
    }

    public void handleLogout(Context ctx){
        ctx.sessionAttribute("user", null);
        ctx.sessionAttribute("roles", null);
        ctx.redirect("/");
    }

    public void handlePerfil(Context ctx){
        UsuariosRepository usuariosRepository = ServiceLocator.instanceOf(UsuariosRepository.class);
        Long id = ctx.sessionAttribute("user");
        List<String> roles = ctx.sessionAttribute("roles");
        Optional<Usuario> usuario = usuariosRepository.buscarPorId(id);


        if (usuario.isPresent()){
            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Perfil");
            model.put("roles", roles);
            model.put("usuario", usuario.get().getUser());


            if (roles.contains("HUMANO")){
                ColaboradorHumano humano = ServiceLocator.instanceOf(HumanosRepository.class).buscarPorId(id).get();
                HumanoOutputDTO dto = HumanoOutputDTO.of(humano);
                model.put("humano", dto);
            } else if (roles.contains("JURIDICA")) {
                Juridica juridica = ServiceLocator.instanceOf(JuridicasRepository.class).buscarPorId(id).get();
                model.put("juridica", juridica);
            }

            ctx.render("perfil.hbs", model);
        } else{
            ctx.redirect("/login");
        }
    }
}
