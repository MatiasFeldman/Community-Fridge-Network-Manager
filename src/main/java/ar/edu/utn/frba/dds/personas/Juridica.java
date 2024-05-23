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

    public ArrayList<Coordenada> solicitarRecomendacionParaHeladera(Coordenada coordenada, Float radio) throws IOException, InterruptedException {

        String url = "https://2c238370-e089-4c6c-8806-ede2b26642e4.mock.pstmn.io/api/ubicacion/lat=-34.58&lon=-58.43&radio=3";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            String body = response.body();

            System.out.println("Respuesta de la API: " + body);

        } else {
            System.out.println("Error al obtener la recomendacion de heladeras");
        }

        return new ArrayList<Coordenada>();
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
