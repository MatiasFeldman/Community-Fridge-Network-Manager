package ar.edu.utn.frba.dds.personas;

import ar.edu.utn.frba.dds.colaboraciones.ColaboracionesRealizadas;
import ar.edu.utn.frba.dds.colaboraciones.ContribucionJuridica;
import ar.edu.utn.frba.dds.colaboraciones.Oferta;
import ar.edu.utn.frba.dds.colaboraciones.OfertasDisponibles;
import ar.edu.utn.frba.dds.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.ubicacion.Direccion;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class Juridica {
    private String razonSocial;
    private Tipo tipo;
    private String rubro;
    private ArrayList<Contacto> mediosDeContacto;
    private Direccion direccion;
    private double puntosCanjeados;
    private ColaboracionesRealizadas colaboracionesRealizadas;

    public void colaborar(ContribucionJuridica contribucion){
        contribucion.contribuir(this.colaboracionesRealizadas);
    }



    public double calcularPuntaje(){
        double puntosDisponibles =  this.colaboracionesRealizadas.calcularPuntaje();
        return puntosDisponibles - puntosCanjeados;
    }

    public void canjearOferta(Oferta oferta) {
        OfertasDisponibles ofertasDisponibles = new OfertasDisponibles();
        try {
            ofertasDisponibles.estaDisponible(oferta);
            if (oferta.getPuntosNecesarios() > this.calcularPuntaje()) {
                throw new RuntimeException("No tiene los puntos necesarios para canjear la oferta");
            }
            oferta.serCanjeada();
            this.puntosCanjeados += oferta.getPuntosNecesarios();

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

    }

}
