package ar.edu.utn.frba.dds.personas;

import ar.edu.utn.frba.dds.colaboraciones.ContribucionJuridica;
import ar.edu.utn.frba.dds.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.ubicacion.Direccion;

import java.util.ArrayList;
import java.util.HashMap;

public class Juridica {
    private String razonSocial;
    private Tipo tipo;
    private String rubro;
    private HashMap<String, String> contacto;
    private Direccion direccion;
    private double puntosDisponibles;
    private ArrayList<ContribucionJuridica> colaboracionesRealizadas;

    public void colaborar(ContribucionJuridica contribucion){
        contribucion.contribuir();
    }

    public ArrayList<Coordenada> solicitarRecomendacionParaHeladera(Coordenada coordenada, Float radio){
        // TODO: implementacion
        return new ArrayList<Coordenada>();
    }

}
