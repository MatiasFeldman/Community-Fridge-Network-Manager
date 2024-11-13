package ar.edu.utn.frba.dds.models.entities.colaboraciones.carga_masiva;

import lombok.SneakyThrows;

import java.io.InputStream;

public interface ConversorCSV {

    @SneakyThrows
    public void convertir(InputStream inputStream);
}
