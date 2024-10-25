package ar.edu.utn.frba.dds.models.entities.personas;


import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "atributo_humano")
@Setter
public class AtributoHumanoRespondido extends Persistente {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_atributo")
    private Atributo atributo;

    @Column(name = "valor")
    private String valor;

    public AtributoHumanoRespondido(String valor, Atributo atributo) {
        this.valor = valor;
        this.atributo = atributo;
    }

    public static AtributoHumanoRespondido create(String nombre, String valor, TipoAtributo tipoAtributo, TipoCampoAtributo tipoCampo){
        Atributo atrib = Atributo.create(nombre, tipoAtributo, tipoCampo);
        AtributoHumanoRespondido respondido = new AtributoHumanoRespondido();
        respondido.setAtributo(atrib);
        respondido.setValor(valor);
        return respondido;
    }

    public String getNombreAtributo() {
        return this.atributo.getNombre();
    }

    public Boolean completo(){
        return valor != null && !this.valor.isEmpty();
    }
}
