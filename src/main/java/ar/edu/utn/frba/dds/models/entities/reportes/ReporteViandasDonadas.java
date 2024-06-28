package ar.edu.utn.frba.dds.models.entities.reportes;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.DonacionDeVianda;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReporteViandasDonadas {
    private HumanosRepository humanosRepository;

    public ReporteViandasDonadas(HumanosRepository humanosRepository) {
        this.humanosRepository = humanosRepository;
    }

    public Reporte generarReporteViandas() {
        Map<String, Integer> viandasPorHumano = contarViandasPorHumano();
        StringBuilder contenido = new StringBuilder();
        contenido.append("Reporte de viandas donadas\n");
        contenido.append("Humano\tCantidad de viandas\n");
        for (Map.Entry<String, Integer> entry : viandasPorHumano.entrySet()) {
            contenido.append(entry.getKey()).append("\t").append(entry.getValue()).append("\n");
        }
        return new Reporte("Reporte de viandas donadas", contenido.toString());
    }

    private Map<String, Integer> contarViandasPorHumano() {
        List<Humano> humanos = humanosRepository.buscarTodos();
        Map<String, Integer> viandasPorHumano = new HashMap<>();

        for (Humano humano : humanos) {
            String idUsuario = humano.getIdUsuario().toString();
            int conteo = (int) humano.getContribuciones().stream()
                    .filter(contribucion -> contribucion instanceof DonacionDeVianda)
                    .count();
            viandasPorHumano.put(idUsuario, conteo);
        }

        return viandasPorHumano;
    }
}
