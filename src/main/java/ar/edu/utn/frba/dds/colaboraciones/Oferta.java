package ar.edu.utn.frba.dds.colaboraciones;

import lombok.Getter;

public class Oferta {
    private String nombre;
    @Getter
    private double puntosNecesarios;
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
            throw new RuntimeException("No hay canjes disponibles para esta oferta");
        }
    }
}
