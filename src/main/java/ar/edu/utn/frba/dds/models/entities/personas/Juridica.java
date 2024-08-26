package ar.edu.utn.frba.dds.models.entities.personas;

import ar.edu.utn.frba.dds.exceptions.PuntosInsuficientesException;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionJuridica;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.Oferta;
import ar.edu.utn.frba.dds.models.entities.suscripciones.ObserverSuscripcion;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.models.entities.ubicacion.RecomendarPuntos;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.repositories.ofertas.imp.OfertasRepository;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Juridica extends ObserverSuscripcion {
    private String razonSocial;
    private Tipo tipo;
    private String Rubro;
    private ArrayList<Contacto> mediosDeContacto;
    private Direccion direccion;
    private double puntosCanjeados;
    private ArrayList<ContribucionJuridica> contribuciones;
    private OfertasRepository ofertasDisponibles;
    private RecomendarPuntos recomendador;
    private double puntosGanados;
    private Usuario user;

    public Juridica(OfertasRepository ofertas, RecomendarPuntos recomendador) {
        this.ofertasDisponibles = ofertas;
        this.recomendador = recomendador;
    }

    public List<Coordenada> solicitarRecomendacionParaHeladera(Coordenada coord, double radio) throws IOException, InterruptedException {
        return recomendador.solicitarRecomendacionParaHeladera(coord, radio);
    }

    public double calcularPuntaje() {
        return puntosGanados - puntosCanjeados;
    }

    public void canjearOferta(Oferta oferta) {
        if (oferta.getPuntosNecesarios() > this.calcularPuntaje()) {
            throw new PuntosInsuficientesException("No tiene los puntos necesarios para canjear la oferta");
        }
        ofertasDisponibles.canjearOferta(oferta);
        this.puntosCanjeados += oferta.getPuntosNecesarios();

    }

    public void agregarContribucion(ContribucionJuridica contribucion) {
        this.contribuciones.add(contribucion);
        puntosGanados += contribucion.calcularPuntaje();
    }

    public UUID getId() {
        return user.getId();
    }

    public String getMedioDeContacto(String medio){
        return this.mediosDeContacto
                .stream()
                .filter(contacto -> contacto.getTipoContacto().equals(medio))
                .findFirst()
                .get()
                .getValorContacto();
    }
}
