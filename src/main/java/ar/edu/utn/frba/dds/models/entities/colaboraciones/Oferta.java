package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.exceptions.OfertaAgotadaException;
import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "oferta")
@Getter
public class Oferta extends Persistente {

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "puntos_necesarios")
    private Double puntosNecesarios;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rubro")
    private Rubro rubro;

    @Column(name = "canjes_totales")
    private Integer canjesTotales;

    @Column(name = "canjes_usados")
    private Integer canjesUsados;

    @Column(name = "Image")
    private String image;

    public static Oferta of(String nombre, Double puntosNecesarios, String rubro, Integer canjesTotales,String image) {
        return Oferta
                .builder()
                .nombre(nombre)
                .puntosNecesarios(puntosNecesarios)
                .rubro(new Rubro(rubro))
                .canjesTotales(canjesTotales)
                .canjesUsados(0)
                .image(image)
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
