package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dtos.ofertas.OfertaOutputDTO;
import ar.edu.utn.frba.dds.exceptions.PuntosInsuficientesException;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.Oferta;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.Rubro;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.juridicas.JuridicasRepository;
import ar.edu.utn.frba.dds.models.repositories.ofertas.OfertasRepository;
import ar.edu.utn.frba.dds.models.repositories.rubros.RubrosRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import ar.edu.utn.frba.dds.utils.RenderUtils;
import io.javalin.http.Context;

import java.util.*;
import java.util.stream.Collectors;

public class OfertasController {
    private OfertasRepository ofertas;
    private HumanosRepository humanos;
    private JuridicasRepository juridicas;

    /*
    public void canjearOferta(String json){
        JsonNode node = ConversorJSON.convertir(json);
        Long id = Long.parseLong(node.get("id_usuario").asText());
        Long idOferta = node.get("id_oferta").asLong();
        String rol = node.get("rol").asText();

        Optional<Oferta> posibleOferta = ofertas.buscarPorId(idOferta);
        if (posibleOferta.isEmpty()){
            throw new RuntimeException("No se encontro la oferta");
        }
        Oferta oferta = posibleOferta.get();

        if (rol.equals("HUMANO")){
            Optional<ColaboradorHumano> posibleHumano = humanos.buscarPorId(id);
            if (posibleHumano.isEmpty()){
                throw new RuntimeException("No se encontro el colaboradorHumano");
            }

            ColaboradorHumano colaboradorHumano = posibleHumano.get();
            colaboradorHumano.canjearOferta(oferta);
        } else if (rol.equals("JURIDICA")){
            Optional<Juridica> posibleJuridica = juridicas.buscarPorId(id);
            if (posibleJuridica.isEmpty()){
                throw new RuntimeException("No se encontro el humano");
            }

            Juridica juridica = posibleJuridica.get();
            juridica.canjearOferta(oferta);
        }

        oferta.serCanjeada();
    }
    */


    public void showOfertas( Context ctx) {
        List<Oferta> ofertas = ServiceLocator.instanceOf(OfertasRepository.class).buscarTodos();
        List<OfertaOutputDTO> dtos = new ArrayList<>();
        Long idUsuario = ctx.sessionAttribute("id");
        List<String> rolUsuario = ctx.sessionAttribute("roles");
        Double misPuntos;
        if(rolUsuario.get(0).contains("HUMANO")){
            Optional<ColaboradorHumano> Humano = ServiceLocator.instanceOf(HumanosRepository.class).buscarPorIdUsuario(idUsuario);
            misPuntos = Humano.get().calcularPuntaje();
        }else if (rolUsuario.get(0).contains("JURIDICA")){
            Optional<Juridica> juridica = ServiceLocator.instanceOf(JuridicasRepository.class).buscarPorIdUsuario(idUsuario);
            misPuntos = juridica.get().calcularPuntaje();
        }else{
            misPuntos = 0.0;
        }
        List<String> rubroIds = ctx.queryParams("filtrar");
        System.out.println("Rubros seleccionados: " + rubroIds);
        if(rubroIds != null && !rubroIds.isEmpty()){
            List<Long> rubroIdsLong = rubroIds.stream()
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            // Filtrar las ofertas por rubro
            ofertas = ofertas.stream()
                    .filter(oferta -> rubroIdsLong.contains(oferta.getRubro().getId()))
                    .collect(Collectors.toList());
        }
        ofertas.forEach(h -> {
            dtos.add(OfertaOutputDTO.of(h));
        });
        List<Rubro> rubros = ServiceLocator.instanceOf(RubrosRepository.class).buscarTodos();

        Map<String, Object> model = new HashMap<>();

        model.put("success", Boolean.parseBoolean(ctx.queryParam("success")));
        model.put("titulo", "Productos y Servicios");
        model.put("ofertas",dtos);
        model.put("rubros",rubros);
        model.put("misPuntos",misPuntos);

        RenderUtils.renderizar(ctx,"colaboraciones/productos.hbs", model);
    }

    public void showOferta(Context ctx) {
        Long ofertaId = Long.valueOf(ctx.pathParam("id"));
        Optional<Oferta> ofertaOptional = ServiceLocator.instanceOf(OfertasRepository.class).buscarPorId(ofertaId);

        if (ofertaOptional.isPresent()) {
            Oferta oferta = ofertaOptional.get();
            Map<String, Object> model = new HashMap<>();
            String error = ctx.queryParam("error");
            if (error != null) {
                model.put("error", error);
            }
            model.put("oferta", oferta);
            model.put("titulo",oferta.getNombre());
            model.put("ofertasRestantes",oferta.canjesRestantes());
            RenderUtils.renderizar(ctx,"colaboraciones/detalle_oferta.hbs",model);
        } else {
            ctx.status(404).result("Oferta no encontrada");
        }
    }


    public void canjearOferta(Context ctx) {
        try {
            Long ofertaId;
            try {
                ofertaId = Long.parseLong(ctx.pathParam("id"));
            } catch (NumberFormatException e) {
                Map<String, Object> model = new HashMap<>();
                model.put("error","ID de oferta inválido. Debe ser un número.");
                model.put("paginaAnterior","/ofertas");
                ctx.status(400).render("400Personalizado.hbs",model);
                return;
            }
            Long usuarioId = ctx.sessionAttribute("id");
            List<String> rolUsuario = ctx.sessionAttribute("roles");
            Optional<Oferta> ofertaOptional = ServiceLocator.instanceOf(OfertasRepository.class).buscarPorId(ofertaId);

            if (ofertaOptional.isPresent() && ofertaOptional.get().getPresente()) {
                Oferta oferta = ofertaOptional.get();
                // Verificamos si el rol del usuario es HUMANO
                if (rolUsuario.get(0).contains("HUMANO")) {
                    Optional<ColaboradorHumano> usuarioOptional = ServiceLocator.instanceOf(HumanosRepository.class).buscarPorIdUsuario(usuarioId);

                    if (usuarioOptional.isPresent()) {
                        ColaboradorHumano usuario = usuarioOptional.get();

                        usuario.canjearOferta(oferta);
                        ServiceLocator.instanceOf(OfertasRepository.class).canjearOferta(oferta);
                        ServiceLocator.instanceOf(HumanosRepository.class).actualizar(usuario);

                        ctx.redirect("/ofertas?success=true");//TODO por ahroa klo dejamos asi podriamos hacer uan apgina de canjeo exitoso mas linda
                    } else {

                        ctx.status(404).result("Usuario no encontrado");
                    }
                }else if (rolUsuario.get(0).contains("JURIDICA")) {
                    Optional<Juridica> usuarioOptional = ServiceLocator.instanceOf(JuridicasRepository.class).buscarPorIdUsuario(usuarioId);

                    if (usuarioOptional.isPresent()) {
                        Juridica usuario = usuarioOptional.get();

                        usuario.canjearOferta(oferta);
                        ServiceLocator.instanceOf(OfertasRepository.class).canjearOferta(oferta);

                        ServiceLocator.instanceOf(JuridicasRepository.class).modificar(usuario);

                        ctx.redirect("/ofertas?success=true");
                    } else {

                        ctx.status(404).result("Usuario no encontrado");
                    }
                }
            } else {
                ctx.status(404).result("Oferta no encontrada o ya no está presente");
            }
        }catch (PuntosInsuficientesException e) {
            throw e;
        } catch (Exception e) {
            ctx.status(500).result("Ocurrió un error al intentar canjear la oferta: " + e.getMessage());
            e.printStackTrace();
        }
    }




}
