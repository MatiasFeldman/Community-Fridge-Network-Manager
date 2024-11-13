package ar.edu.utn.frba.dds.models.entities.colaboraciones.carga_masiva;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionHumanaFactory;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail.MailSender;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.ofertas.OfertasRepository;
import com.opencsv.CSVReader;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
public class ConversorCSVReader implements ConversorCSV {
    private HumanosRepository humanosRepository;
    private OfertasRepository ofertas;
    private MailSender mailSender;


    @Override
    @SneakyThrows
    public void convertir(InputStream inputStream) {
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
            ValidadorCargaMasiva validador = new ValidadorCargaMasiva();
            String[] line;

            reader.readNext(); // salteo el header

            while ((line = reader.readNext()) != null) {
                System.out.println(Arrays.toString(line));
                line = line[0].split(";");

                if (!validador.validarLinea(line)) {
                    continue;
                }

                this.verificarExistenciaHumano(line);

            }
        }
        catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo", e);
        }
    }

    public void verificarExistenciaHumano(String[] line) throws IOException, MessagingException {
        String tipoDocumento = line[0];
        String documento = line[1];
        String nombre = line[2];
        String apellido = line[3];
        String mail = line[4];
        String formaColaboracion = line[5];
        Integer cantidad = Integer.parseInt(line[6]);


        Optional<ColaboradorHumano> humano = humanosRepository.buscarPorDocumento(tipoDocumento, documento);

        if (humano.isEmpty()) {
            System.out.println("No existe el humano en la base de datos, se procede a crearlo");
            RegisterCargaMasiva registrador = new RegisterCargaMasiva(humanosRepository, ofertas);

            Usuario userCreado = registrador.registrarHumano(line);

            MailDeBienvenida.enviarMailBienvenida(mail, nombre, apellido, userCreado.getUser(), userCreado.getPassword(), mailSender);
        } else {
            System.out.println("Ya existe el humano en la base de datos, se procede a agregar la contribucion");
            ColaboradorHumano human = humano.get();
            ContribucionHumanaFactory.createForCargaMasiva(formaColaboracion, cantidad, human);

        }
    }


}
