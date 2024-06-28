package ar.edu.utn.frba.dds.models.entities.reportes;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionHumana;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReporteMovimientoViandas {
    private HumanosRepository humanosRepository;

    public ReporteMovimientoViandas(HumanosRepository humanosRepository) {
        this.humanosRepository = humanosRepository;
    }

    public Reporte generarReporteMovimientoViandas() {
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

        return new Reporte("Reporte de viandas por heladera", contenido.toString());
    }

    public Map<String, Integer[]> contarViandasPorHeladera() {
        List<Humano> humanos = humanosRepository.buscarTodos();
        Map<String, Integer[]> viandasPorHeladera = new HashMap<>();

        for (Humano humano : humanos) {
            for (ContribucionHumana contribucion : humano.getContribuciones()) {
                if (contribucion instanceof DistribucionViandas) {
                    DistribucionViandas distribucion = (DistribucionViandas) contribucion;
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
        }
        return viandasPorHeladera;
    }

}
