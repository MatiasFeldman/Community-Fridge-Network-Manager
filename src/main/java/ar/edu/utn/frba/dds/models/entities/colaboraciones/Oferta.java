package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.exceptions.OfertaAgotadaException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Oferta {
    @Getter
    private Long id;
    @Getter
    private String nombre;
    @Getter
    private Double puntosNecesarios;
    @Getter
    private Rubro rubro;
    private Integer canjesTotales;
    private Integer canjesUsados;

    public Oferta(String nombre, Double puntosNecesarios, Rubro rubro, Integer canjesTotales) {
        this.nombre = nombre;
        this.puntosNecesarios = puntosNecesarios;
        this.rubro = rubro;
        this.canjesTotales = canjesTotales;
        this.canjesUsados = 0;
    }

    public static Oferta of(String nombre, Double puntosNecesarios, String rubro, Integer canjesTotales){
        return Oferta
                .builder()
                .nombre(nombre)
                .puntosNecesarios(puntosNecesarios)
                .rubro(new Rubro(rubro))
                .canjesTotales(canjesTotales)
                .canjesUsados(0)
                .build();
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
