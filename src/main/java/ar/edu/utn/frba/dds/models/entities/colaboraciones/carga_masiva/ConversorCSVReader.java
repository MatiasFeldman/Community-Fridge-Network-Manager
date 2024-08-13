package ar.edu.utn.frba.dds.models.entities.colaboraciones.carga_masiva;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionHumanaFactory;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail.MailSender;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail.Mail;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.factories.mailSender.MailSenderFactory;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.ofertas.imp.OfertasRepository;
import com.opencsv.CSVReader;
import lombok.SneakyThrows;

import javax.mail.MessagingException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class ConversorCSVReader implements ConversorCSV {
    private HumanosRepository humanosRepository;
    private OfertasRepository ofertas;

    public ConversorCSVReader(HumanosRepository humanosRepository, OfertasRepository ofertas) {
        this.humanosRepository = humanosRepository;
        this.ofertas = ofertas;

    }

    @Override
    @SneakyThrows
    public void convertir(String path) {
        CSVReader reader = new CSVReader(new FileReader(path));
        ValidadorCargaMasiva validador = new ValidadorCargaMasiva();
        String[] line;
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

        if (humanosRepository.buscarPorDocumento(tipoDocumento, documento).isEmpty()) {
            RegisterCargaMasiva registrador = new RegisterCargaMasiva(humanosRepository, ofertas);

            Usuario userCreado = registrador.registrarHumano(line);

            MailDeBienvenida.enviarMailBienvenida(mail, nombre, apellido, userCreado.getUser(), userCreado.getPassword());
        } else {
            Optional<Humano> humanoRegistrado = humanosRepository.buscarPorDocumento(tipoDocumento, documento);
            if (humanoRegistrado.isPresent()) {
                Humano human = humanoRegistrado.get();
                human.agregarContribucion(ContribucionHumanaFactory.create(formaColaboracion, cantidad));
            }

        }
    }




}
