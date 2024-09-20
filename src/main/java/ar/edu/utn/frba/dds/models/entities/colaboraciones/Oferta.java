package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.exceptions.OfertaAgotadaException;
import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "oferta")
@Getter
public class Oferta extends Persistente {

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "puntos_necesarios")
    private Double puntosNecesarios;

    @Embedded
    private Rubro rubro;

    @Column(name = "canjes_totales")
    private Integer canjesTotales;

    @Column(name = "canjes_usados")
    private Integer canjesUsados;

    public static Oferta of(String nombre, Double puntosNecesarios, String rubro, Integer canjesTotales) {
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
        } else {
            throw new OfertaAgotadaException("No hay canjes disponibles para esta oferta");
        }
    }
}
