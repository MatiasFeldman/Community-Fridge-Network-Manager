package ar.edu.utn.frba.dds.models.entities.colaboraciones.carga_masiva;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionHumana;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionHumanaFactory;
import ar.edu.utn.frba.dds.models.entities.helpers.mail.EnviarMail;
import ar.edu.utn.frba.dds.models.entities.helpers.mail.Mail;
import ar.edu.utn.frba.dds.models.entities.helpers.mail.MimeMailSender;
import ar.edu.utn.frba.dds.models.entities.personas.Contacto;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.entities.personas.TipoAtributo;
import ar.edu.utn.frba.dds.models.entities.usuarios.Rol;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.repositories.users.imp.UsuariosRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.lang3.RandomStringUtils;

import javax.mail.MessagingException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConversorCSVReader implements IConversorCSV {
    private UsuariosRepository usersRespository;

    public ConversorCSVReader(UsuariosRepository repositorio) {
        this.usersRespository = repositorio;
    }

    @Override
    public void convertir(String path) throws CsvValidationException, IOException, ParseException, MessagingException {
        CSVReader reader = new CSVReader(new FileReader(path));
        ValidadorCargaMasiva validador = new ValidadorCargaMasiva();
        String[] line;
        while ((line = reader.readNext()) != null) {
            String tipoDocumento = line[0];
            String documento = line[1];
            String nombre = line[2];
            String apellido = line[3];
            String mail = line[4];
            String fechaColaboracionString = line[5];
            String formaColaboracion = line[6];
            Integer cantidad = Integer.parseInt(line[7]);
            String username = nombre.charAt(0) + apellido;

            // Validar tipo de documento
            if (!validador.cumpleTipoDNI(tipoDocumento)){
                System.err.println("Error en la línea: " + line + ". Tipo de documento inválido.");
                continue;
            }

            // Validar formato de mail
            if (!validador.cumpleFromatoMail(mail)) {
                System.err.println("Error en la línea: " + line + ". Formato de mail inválido.");
                continue;
            }

            // Parsear fecha
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaColaboracion = sdf.parse(fechaColaboracionString);

            // Validar forma de colaboración
            if (!validador.cumpleFormaColaboracion(formaColaboracion)) {
                System.err.println("Error en la línea: " + line + ". Forma de colaboración inválida.");
                continue;
            }

            Humano humano = new Humano();
            humano.generarAtributo(TipoAtributo.OBLIGATORIO, "Nombre", nombre);
            humano.generarAtributo(TipoAtributo.OBLIGATORIO, "Apellido", apellido);
            humano.generarAtributo(TipoAtributo.OBLIGATORIO, "Mail", mail);
            humano.generarContacto(new Contacto(tipoDocumento, documento));

            ContribucionHumana contribucion = ContribucionHumanaFactory.create(formaColaboracion, cantidad);

            humano.agregarContribucion(contribucion);

            if (usersRespository.buscarPorUsername(username).isEmpty()) {
                String password = RandomStringUtils.randomAlphanumeric(16);
                String cuerpo = "Hola " + nombre + " " + apellido + ",\n\n"
                        + "Muchas gracias por querer colaborar con nosotros. "
                        + "Lamentablemente no pudimos encontrar tu usuario en nuestra base de datos. "
                        + "Es por eso, que te creamos una cuenta para que confimers y/o actualices tus credenciales\n\n"
                        + "Usuario: " + username + "\n"
                        + "Contraseña: " + password + "\n\n"
                        + "Saludos,\n"
                        + "Equipo de colaboraciones";
                String motivo = "Colaboración pendiente";

                usersRespository.guardar(new Usuario(username, password, new Rol("HUMANO")));

                EnviarMail enviador = new EnviarMail(cuerpo, motivo, new MimeMailSender());
                enviador.enviarMail(mail, new Mail(cuerpo, motivo));
            }
        }
    }
}
