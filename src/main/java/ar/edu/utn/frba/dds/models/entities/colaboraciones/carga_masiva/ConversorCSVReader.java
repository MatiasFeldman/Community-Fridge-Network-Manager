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
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class ConversorCSVReader implements ConversorCSV {
    private HumanosRepository humanosRepository;
    private OfertasRepository ofertas;
    private MailSender mailSender;

    @Override
    @SneakyThrows
    public void convertir(InputStream inputStream) {
        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            ValidadorCargaMasiva validador = new ValidadorCargaMasiva();
            String[] line;

            reader.readNext(); // Salta el encabezado

            while ((line = reader.readNext()) != null) {
                System.out.println("Procesando línea: " + Arrays.toString(line));
                // Divide la línea usando coma como separador
                String[] parsedLine = line[0]
                        .replace("[", "") // Quitamos el corchete de inicio
                        .replace("]", "") // Quitamos el corchete de fin
                        .split(",\\s*");  // Dividimos por coma, ignorando espacios


                Boolean cumple = validador.validarLinea(line);

                System.out.println("Cumple: " + cumple);

                if (!cumple) {
                    System.out.println("La línea no cumple con los requisitos: " + Arrays.toString(line));
                    continue;
                } else {
                    System.out.println("La línea cumple con los requisitos");
                    this.verificarExistenciaHumano(line);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo", e);
        }
    }

    public void verificarExistenciaHumano(String[] line) throws IOException, MessagingException {
        System.out.println("Verificando existencia del humano");

        String tipoDocumento = line[0];
        String documento = line[1];
        String nombre = line[2];
        String apellido = line[3];
        String mail = line[4];
        String formaColaboracion = line[6];
        Integer cantidad = Integer.parseInt(line[7]);

        System.out.println("Tipo documento: " + tipoDocumento);

        System.out.println("Documento: " + documento);

        Optional<ColaboradorHumano> humano = humanosRepository.buscarPorDocumento(tipoDocumento, documento);

        System.out.println("Humano: " + humano);

        if (humano.isEmpty()) {
            System.out.println("No existe el humano en la base de datos, se procede a crearlo");
            RegisterCargaMasiva registrador = new RegisterCargaMasiva(humanosRepository, ofertas);

            UsuarioConPassword userCreadoConPass = registrador.registrarHumano(line);
            Usuario userCreado = userCreadoConPass.getUsuario();

            CompletableFuture.runAsync(() ->{
                MailDeBienvenida.enviarMailBienvenida(mail, nombre, apellido, userCreado.getUser(), userCreadoConPass.getPasswordSinHash(), mailSender);
            });
        } else {
            System.out.println("Ya existe el humano en la base de datos, se procede a agregar la contribución");
            ColaboradorHumano human = humano.get();
            System.out.println("Humano encontrado: " + human.getUsername());
            System.out.println(formaColaboracion);
            ContribucionHumanaFactory.createForCargaMasiva(formaColaboracion, cantidad, human);
            System.out.println("Contribución agregada");
        }
    }
}