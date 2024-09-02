package ar.edu.utn.frba.dds.models.entities.colaboraciones.carga_masiva;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CargaMasiva {
    private String path;
    private ConversorCSV conversor;

    public void cargar() { // propagar o hacer el catch del exception y convertirla
        conversor.convertir(path);
    }

}
