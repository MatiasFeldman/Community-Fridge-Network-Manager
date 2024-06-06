package ar.edu.utn.frba.dds.models.entities.helpers;

import java.io.IOException;

public interface IConexionApi {
    public void get() throws IOException, InterruptedException;
    public String revisarRespuesta();
}
