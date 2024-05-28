package ar.edu.utn.frba.dds.personas;

import ar.edu.utn.frba.dds.colaboraciones.ColaboracionesRealizadas;
import ar.edu.utn.frba.dds.colaboraciones.ContribucionHumana;
import ar.edu.utn.frba.dds.colaboraciones.Oferta;
import ar.edu.utn.frba.dds.colaboraciones.OfertasDisponibles;
import ar.edu.utn.frba.dds.exceptions.PuntosInsuficientesException;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Humano {
    private ArrayList<AtributoHumano> atributosObligatorios;
    private ArrayList<AtributoHumano> atributosOpcionales;
    private double puntosCanjeados;
    private ColaboracionesRealizadas colaboracionesRealizadas;


    public void colaborar(ContribucionHumana contribucion) {
        contribucion.contribuir(this.colaboracionesRealizadas);
    }

    public void generarAtributo(TipoAtributo tipo, String nombreAtributo) {
        if (tipo == TipoAtributo.OBLIGATORIO) {
            this.atributosObligatorios.add(new AtributoHumano(nombreAtributo));
        } else {
            this.atributosOpcionales.add(new AtributoHumano(nombreAtributo));
        }
    }

    public double calcularPuntaje() {
        double puntosDisponibles = this.colaboracionesRealizadas.calcularPuntaje();
        return puntosDisponibles - puntosCanjeados;
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
