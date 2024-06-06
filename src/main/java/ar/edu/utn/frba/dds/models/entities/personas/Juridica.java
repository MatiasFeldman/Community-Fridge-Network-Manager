package ar.edu.utn.frba.dds.models.entities.personas;

import ar.edu.utn.frba.dds.exceptions.PuntosInsuficientesException;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionJuridica;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.Oferta;
import ar.edu.utn.frba.dds.models.repositories.imp.OfertasRepository;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;

import java.util.ArrayList;

public class Juridica {
    private String razonSocial;
    private Tipo tipo;
    private String rubro;
    private ArrayList<Contacto> mediosDeContacto;
    private Direccion direccion;
    private double puntosCanjeados;
    private ArrayList<ContribucionJuridica> contribuciones;
    private OfertasRepository ofertasDisponibles;

    public Juridica(OfertasRepository ofertas){
        this.ofertasDisponibles = ofertas;
    }

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
        if (oferta.getPuntosNecesarios() > this.calcularPuntaje()) {
            throw new PuntosInsuficientesException("No tiene los puntos necesarios para canjear la oferta");
        }
        ofertasDisponibles.canjearOferta(oferta);
        this.puntosCanjeados += oferta.getPuntosNecesarios();

    }

}
