package ar.edu.utn.frba.dds.models.entities.colaboraciones.carga_masiva;

import com.opencsv.exceptions.CsvValidationException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.text.ParseException;

public class CargaMasiva {
    private String path;
    private IConversorCSV conversor;

    public CargaMasiva(String path, IConversorCSV conversor) {
        this.conversor = conversor;
    }

    public void cargar() throws CsvValidationException, MessagingException, IOException, ParseException {
        conversor.convertir(path);
    }

}
