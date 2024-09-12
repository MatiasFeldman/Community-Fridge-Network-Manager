package ar.edu.utn.frba.dds.models.entities.colaboraciones.carga_masiva;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionHumanaFactory;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail.MailSender;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.ofertas.OfertasRepository;
import com.opencsv.CSVReader;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import javax.mail.MessagingException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

@AllArgsConstructor
public class ConversorCSVReader implements ConversorCSV {
    private HumanosRepository humanosRepository;
    private OfertasRepository ofertas;
    private MailSender mailSender;


    @Override
    @SneakyThrows
    public void convertir(String path) {
        CSVReader reader = new CSVReader(new FileReader(path));
        ValidadorCargaMasiva validador = new ValidadorCargaMasiva();
        String[] line;

        reader.readNext();


        while ((line = reader.readNext()) != null) {


            if (!validador.validarLinea(line)) {
                continue;
            }

            this.verificarExistenciaHumano(line);

        }
    }

    public void verificarExistenciaHumano(String[] line) throws IOException, MessagingException {
        String tipoDocumento = line[0];
        String documento = line[1];
        String nombre = line[2];
        String apellido = line[3];
        String mail = line[4];
        String formaColaboracion = line[6];
        Integer cantidad = Integer.parseInt(line[7]);


        Optional<Humano> humano = humanosRepository.buscarPorDocumento(tipoDocumento, documento);

        if (humano.isEmpty()) {
            RegisterCargaMasiva registrador = new RegisterCargaMasiva(humanosRepository, ofertas);

            Usuario userCreado = registrador.registrarHumano(line);

            MailDeBienvenida.enviarMailBienvenida(mail, nombre, apellido, userCreado.getUser(), userCreado.getPassword(), mailSender);
        } else {
            Humano human = humano.get();
            ContribucionHumanaFactory.createForCargaMasiva(formaColaboracion, cantidad, human);

        }
    }




}
