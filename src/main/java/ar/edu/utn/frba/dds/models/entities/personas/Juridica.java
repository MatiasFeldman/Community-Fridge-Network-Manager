package ar.edu.utn.frba.dds.models.entities.personas;

import ar.edu.utn.frba.dds.exceptions.PuntosInsuficientesException;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionJuridica;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.Oferta;
import ar.edu.utn.frba.dds.models.entities.suscripciones.ObserverSuscripcion;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.models.entities.ubicacion.RecomendarPuntos;
import ar.edu.utn.frba.dds.models.repositories.ofertas.imp.OfertasRepository;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public Juridica(OfertasRepository ofertas, RecomendarPuntos recomendador){
        this.ofertasDisponibles = ofertas;
        this.recomendador = recomendador;
    }

    public void colaborar(ContribucionJuridica contribucion){
        contribucion.contribuir();
        contribuciones.add(contribucion);
    }

    public List<Coordenada> solicitarRecomendacionParaHeladera(Coordenada coord, double radio) throws IOException, InterruptedException {
        return recomendador.solicitarRecomendacionParaHeladera(coord, radio);
    }

    public double calcularPuntaje() {
        return this.puntosGanados() - puntosCanjeados;
    }

    public double puntosGanados(){
        return contribuciones.stream().mapToDouble(ContribucionJuridica::calcularPuntaje).sum();
    }

    public void canjearOferta(Oferta oferta) {
        if (oferta.getPuntosNecesarios() > this.calcularPuntaje()) {
            throw new PuntosInsuficientesException("No tiene los puntos necesarios para canjear la oferta");
        }
        ofertasDisponibles.canjearOferta(oferta);
        this.puntosCanjeados += oferta.getPuntosNecesarios();

    }
}
