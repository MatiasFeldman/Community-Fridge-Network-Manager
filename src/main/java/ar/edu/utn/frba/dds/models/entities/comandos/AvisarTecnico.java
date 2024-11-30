package ar.edu.utn.frba.dds.models.entities.comandos;

import ar.edu.utn.frba.dds.exceptions.NoHayTecnicosDisponiblesException;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail.Mail;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail.MimeMailSender;
import ar.edu.utn.frba.dds.models.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.models.repositories.tecnicos.TecnicosRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import lombok.SneakyThrows;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class AvisarTecnico implements Comando {
    private TecnicosRepository tecnicos;

    public AvisarTecnico(TecnicosRepository tecnicos) {
        this.tecnicos = tecnicos;
    }


    @Override
    @SneakyThrows
    public void ejecutar(Heladera heladera, String mensaje) {

        System.out.println(mensaje);

        CompletableFuture.runAsync(() -> {
            try {
                Direccion origen = heladera.getDireccion();
                Optional<Tecnico> tecnico = tecnicos.buscarMasCercano(origen);

                if (tecnico.isPresent()) {
                    MimeMailSender mailSender = ServiceLocator.instanceOf(MimeMailSender.class);
                    String tipo = switch (mensaje) {
                        case "FALLA_TECNICA" -> "Falla técnica";
                        case "MOVIMIENTO" -> "Alerta de Movimiento";
                        case "TEMPERATURA" -> "Temperatura fuera de rango";
                        case "FALLA_CONEXION" -> "Falla de conexión";
                        default -> "heladera";
                    };

                    System.out.println("Le voy a avisar al técnico: " + tecnico.get().getApellido() + ", " + tecnico.get().getNombre());

                    Mail mail = new Mail(
                            "Se ha encontrado el siguiente problema en la heladera " + heladera.getId() +
                                    " - " + heladera.getDireccion().getDireccion() + ": " + tipo,
                            "FALLA EN LA HELADERA"
                    );
                    mailSender.enviarMail(tecnico.get().getMail(), mail);
                } else {
                    throw new NoHayTecnicosDisponiblesException("No hay técnicos disponibles por la zona");
                }
            } catch (Exception e) {
                e.printStackTrace(); // Registra cualquier error, pero no interrumpe el flujo principal
            }
        });


    }
}
