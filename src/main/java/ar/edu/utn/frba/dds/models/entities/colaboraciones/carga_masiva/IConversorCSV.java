package ar.edu.utn.frba.dds.models.entities.colaboraciones.carga_masiva;

import com.opencsv.exceptions.CsvValidationException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.text.ParseException;

public interface IConversorCSV {

    public void convertir(String path) throws CsvValidationException, IOException, ParseException, MessagingException;
}
