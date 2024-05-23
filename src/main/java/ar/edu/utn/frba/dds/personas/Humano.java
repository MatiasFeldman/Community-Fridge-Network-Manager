package ar.edu.utn.frba.dds.personas;

import ar.edu.utn.frba.dds.colaboraciones.ColaboracionesRealizadas;
import ar.edu.utn.frba.dds.colaboraciones.ContribucionHumana;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
public class Humano {
    private ArrayList<AtributoHumano> atributosObligatorios;
    private ArrayList<AtributoHumano> atributosOpcionales;
    @Setter
    private double puntosDisponibles;
    private ColaboracionesRealizadas colaboracionesRealizadas;


    public void colaborar(ContribucionHumana contribucion){
        contribucion.contribuir(this.colaboracionesRealizadas);
    }

    public void generarAtributo(TipoAtributo tipo, String nombreAtributo){
        if (tipo == TipoAtributo.OBLIGATORIO){
            this.atributosObligatorios.add(new AtributoHumano(nombreAtributo));
        } else {
            this.atributosOpcionales.add(new AtributoHumano(nombreAtributo));
        }
    }

    public double calcularPuntaje(){
        return this.colaboracionesRealizadas.calcularPuntaje();
    }

}
