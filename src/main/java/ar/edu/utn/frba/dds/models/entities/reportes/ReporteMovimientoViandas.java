package ar.edu.utn.frba.dds.models.entities.reportes;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.*;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.entities.personas.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.repositories.distribuciones_de_viandas.DistribucionesDeViandasRepository;
import ar.edu.utn.frba.dds.models.repositories.donaciones_de_vianda.DonacionesDeViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.personasVulnerables.PersonasVulnerablesRepository;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class ReporteMovimientoViandas implements Reporte {
    private HumanosRepository humanosRepository;
    private PersonasVulnerablesRepository personasVulnerablesRepository;
    private DonacionesDeViandaRepository donacionesDeViandaRepository;
    private DistribucionesDeViandasRepository distribucionesDeViandasRepository;


    public String generarReporteMovimientoViandas() {
        Map<String, Integer[]> viandasPorHeladera = contarViandasPorHeladera();
        StringBuilder contenido = new StringBuilder();
        contenido.append("Reporte de viandas por heladera\n");
        contenido.append("Heladera Nombre\t\tEntraron\tSalieron\n");

        for (Map.Entry<String, Integer[]> entry : viandasPorHeladera.entrySet()) {
            contenido.append(entry.getKey())  // Aquí accedemos al nombre de la heladera
                    .append("\t\t")
                    .append(entry.getValue()[1])  // Viandas entrantes (destino)
                    .append("\t\t")
                    .append(entry.getValue()[0])  // Viandas salientes (origen)
                    .append("\n");
        }

        return contenido.toString();
    }

    public Map<String, Integer[]> contarViandasPorHeladera() {
        List<ColaboradorHumano> colaboradorHumanos = humanosRepository.buscarTodos();
        List<PersonaVulnerable> personasVulnerables = personasVulnerablesRepository.buscarTodos();
        Map<String, Integer[]> viandasPorHeladera = new HashMap<>();

        // Conteo de viandas distribuidas
        for (ColaboradorHumano colaboradorHumano : colaboradorHumanos) {
            List<DistribucionViandas> distribuciones = distribucionesDeViandasRepository.buscarPorColaborador(colaboradorHumano.getIdHumano());
            for (DistribucionViandas distribucion : distribuciones) {
                Heladera origen = distribucion.getHeladeraOrigen();
                Heladera destino = distribucion.getHeladeraDestino();

                // Contar viandas que salen de la heladera origen
                Integer[] conteoOrigen = viandasPorHeladera.get(origen.getNombre().getNombreDePunto());
                if (conteoOrigen == null) {
                    conteoOrigen = new Integer[]{0, 0};
                }
                conteoOrigen[0] += distribucion.getCantidadViandas();
                viandasPorHeladera.put(origen.getNombre().getNombreDePunto(), conteoOrigen);

                // Contar viandas que llegan a la heladera destino
                Integer[] conteoDestino = viandasPorHeladera.get(destino.getNombre().getNombreDePunto());
                if (conteoDestino == null) {
                    conteoDestino = new Integer[]{0, 0};
                }
                conteoDestino[1] += distribucion.getCantidadViandas();
                viandasPorHeladera.put(destino.getNombre().getNombreDePunto(), conteoDestino);

            }
        }

        // Contar viandas retiradas por personas vulnerables
        for (PersonaVulnerable persona : personasVulnerables) {
            for (TarjetaPersonaVulnerable tarjeta : persona.getTarjetas()) {
                List<UsoTarjeta> historialUso = tarjeta.getHistorialDeUsos();
                for (UsoTarjeta uso : historialUso) {
                    Heladera heladera = uso.getHeladera();
                    Integer[] conteo = viandasPorHeladera.getOrDefault(heladera.getNombre().getNombreDePunto(), new Integer[]{0, 0});
                    conteo[1] += 1; // Cada uso de tarjeta cuenta como una vianda saliente
                    viandasPorHeladera.put(heladera.getNombre().getNombreDePunto(), conteo);
                }
            }
        }

        // contar donaciones de viandas
        for (ColaboradorHumano colaboradorHumano : colaboradorHumanos) {
            List<DonacionDeVianda> viandasDonadas = donacionesDeViandaRepository.buscarPorColaborador(colaboradorHumano.getIdHumano());
            for (DonacionDeVianda donacion : viandasDonadas) {
                Heladera destino = donacion.getHeladera();

                // Contar viandas que llegan a la heladera destino
                Integer[] conteoDestino = viandasPorHeladera.get(destino.getNombre().getNombreDePunto());
                if (conteoDestino == null) {
                    conteoDestino = new Integer[]{0, 0};
                }
                conteoDestino[1] += 1;
                viandasPorHeladera.put(destino.getNombre().getNombreDePunto(), conteoDestino);

            }
        }

        return viandasPorHeladera;
    }

    @Override
    public String nombre() {
        return "Reporte de viandas por heladera";
    }

    @Override
    public String contenido() {
        return generarReporteMovimientoViandas();
    }
}
