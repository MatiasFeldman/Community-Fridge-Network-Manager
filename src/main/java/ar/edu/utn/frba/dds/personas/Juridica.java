package ar.edu.utn.frba.dds.personas;

import ar.edu.utn.frba.dds.colaboraciones.*;
import ar.edu.utn.frba.dds.exceptions.PuntosInsuficientesException;
import ar.edu.utn.frba.dds.ubicacion.Direccion;

import java.util.ArrayList;

public class Juridica {
    private String razonSocial;
    private Tipo tipo;
    private String rubro;
    private ArrayList<Contacto> mediosDeContacto;
    private Direccion direccion;
    private double puntosCanjeados;
    private ArrayList<ContribucionJuridica> contribuciones;

    public void colaborar(ContribucionJuridica contribucion){
        contribucion.contribuir();
        contribuciones.add(contribucion);
    }



    public double calcularPuntaje() {
        return this.puntosGanados() - puntosCanjeados;
    }

    public double puntosGanados(){
        return contribuciones.stream().mapToDouble(ContribucionJuridica::calcularPuntaje).sum();
    }

    public void canjearOferta(Oferta oferta) {
        OfertasDisponibles ofertasDisponibles = new OfertasDisponibles(); // TODO: está mal
        if (oferta.getPuntosNecesarios() > this.calcularPuntaje()) {
            throw new PuntosInsuficientesException("No tiene los puntos necesarios para canjear la oferta");
        }
        oferta.serCanjeada();
        this.puntosCanjeados += oferta.getPuntosNecesarios();


    }
}
