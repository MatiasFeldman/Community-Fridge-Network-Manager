package ar.edu.utn.frba.dds.models.entities.colaboraciones.carga_masiva;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.InputStream;

@AllArgsConstructor
@NoArgsConstructor
public class CargaMasiva {
    private InputStream inputStream;
    private ConversorCSV conversor;

    @SneakyThrows
    public void cargar() { // propagar o hacer el catch del exception y convertirla
        conversor.convertir(inputStream);
    }

}
