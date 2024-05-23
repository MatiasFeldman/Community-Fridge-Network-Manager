package ar.edu.utn.frba.dds.colaboraciones.cargaMasiva;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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

                // TODO: Fijarse si existe el colaborador y asignarle la colaboración
                /*Colaborador colaborador = buscarColaborador(documento);

                if (colaborador != null) {
                    colaborador.asignarColaboracion(formaColaboracion, cantidad);
                } else {
                    enviarMail(nombre, apellido, mail);
                }*/

            }
        } catch (IOException | CsvValidationException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static void enviarMail(String nombre, String apellido, String mail) {
        final String username = "nuestroMail@gmail.com";
        final String password = "nuestraClave";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("desdeEmail@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(mail)
            );
            message.setSubject("Colaboración pendiente");
            message.setText("Hola " + nombre + " " + apellido + ",\n\n"
                    + "Muchas gracias por querer colaborar con nosotros. "
                    + "Lamentablemente no pudimos encontrar tu usuario en nuestra base de datos. "
                    + "Por favor, registrate en nuestro sitio web para poder colaborar.\n\n"
                    + "Saludos,\n"
                    + "Equipo de colaboraciones");

            Transport.send(message);

            System.out.println("Mail enviado a " + mail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
