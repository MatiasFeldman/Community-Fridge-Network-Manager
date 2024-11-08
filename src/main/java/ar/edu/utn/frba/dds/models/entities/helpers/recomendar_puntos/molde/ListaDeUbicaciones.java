package ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos.molde;

import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Random;

@AllArgsConstructor
@NoArgsConstructor
public class ListaDeUbicaciones {
    @Getter
    @Setter
    public List<Coordenada> coordenadas ;

    public Coordenada devolverCualquiera(){
        Random random = new Random();
        int index = random.nextInt(coordenadas.size());
        return coordenadas.get(index);
    }
}
