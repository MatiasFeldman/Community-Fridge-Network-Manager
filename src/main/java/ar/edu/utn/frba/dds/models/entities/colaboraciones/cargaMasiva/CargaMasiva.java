package ar.edu.utn.frba.dds.models.entities.colaboraciones.cargaMasiva;

import ar.edu.utn.frba.dds.models.entities.helpers.EnviarMail;
import ar.edu.utn.frba.dds.models.entities.helpers.Mail;
import ar.edu.utn.frba.dds.models.repositories.imp.HumanosRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.*;

public class CargaMasiva {
    private String path;

    public static void main(String[] args) {
        String path = args[0];

        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                String tipoDocumento = line[0];
                String documento = line[1];
                String nombre = line[2];
                String apellido = line[3];
                String mail = line[4];
                String fechaColaboracionString = line[5];
                String formaColaboracion = line[6];
                int cantidad = Integer.parseInt(line[7]);

                // Validar tipo de documento
                if (!tipoDocumento.matches("DNI|LE|LC")) {
                    System.err.println("Error en la línea: " + line + ". Tipo de documento inválido.");
                    continue;
                }

                // Validar formato de mail
                if (!mail.matches(".+@.+\\..+")) {
                    System.err.println("Error en la línea: " + line + ". Formato de mail inválido.");
                    continue;
                }

                // Parsear fecha
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date fechaColaboracion = sdf.parse(fechaColaboracionString);

                // Validar forma de colaboración
                if (!formaColaboracion.matches("DINERO|DONACION_VIANDAS|REDISTRIBUCION_VIANDAS|ENTREGA_VIANDAS")) {
                    System.err.println("Error en la línea: " + line + ". Forma de colaboración inválida.");
                    continue;
                }

                //Fijarse si existe el humano y asignarle la colaboración en el repositorio
                if (HumanosRepository.getInstance().getHumanoByDocumento(documento) == null) {

                    String cuerpo = "Hola " + nombre + " " + apellido + ",\n\n"
                            + "Muchas gracias por querer colaborar con nosotros. "
                            + "Lamentablemente no pudimos encontrar tu usuario en nuestra base de datos. "
                            + "Por favor, registrate en nuestro sitio web para poder colaborar.\n\n"
                            + "Saludos,\n"
                            + "Equipo de colaboraciones";
                    String motivo = "Colaboración pendiente";

                    EnviarMail enviador = new EnviarMail(cuerpo, motivo);
                    enviador.enviarMail(mail, new Mail(cuerpo, motivo));
                } else {
                    // Crear colaboración
                }
            }
        } catch (IOException | CsvValidationException | ParseException | MessagingException e) {
            e.printStackTrace();
        }
    }

}
