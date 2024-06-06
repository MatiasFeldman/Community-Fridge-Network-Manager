package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.exceptions.OfertaAgotadaException;
import lombok.Getter;

public class Oferta {
    @Getter
    private String nombre;
    @Getter
    private double puntosNecesarios;
    @Getter
    private Rubro rubro;
    private Integer canjesTotales;
    private Integer canjesUsados;

    public Oferta(String nombre, double puntosNecesarios, Rubro rubro, Integer canjesTotales) {
        this.nombre = nombre;
        this.puntosNecesarios = puntosNecesarios;
        this.rubro = rubro;
        this.canjesTotales = canjesTotales;
        this.canjesUsados = 0;
    }

    public Integer canjesRestantes() {
        return canjesTotales - canjesUsados;
    }

    public void serCanjeada() {
        if (canjesRestantes() > 0) {
            canjesUsados++;
        }
        else{
            throw new OfertaAgotadaException("No hay canjes disponibles para esta oferta");
        }
    }
}
