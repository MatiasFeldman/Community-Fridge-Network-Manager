package ar.edu.utn.frba.dds.models.entities.reportes;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.DonacionDeVianda;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.repositories.donaciones_de_vianda.DonacionesDeViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class ReporteViandasDonadas implements Reporte {
    private HumanosRepository humanosRepository;
    private DonacionesDeViandaRepository donacionesDeViandaRepository;

    public ReporteViandasDonadas(HumanosRepository humanosRepository) {
        this.humanosRepository = humanosRepository;
    }

    @Override
    public String nombre (){
        return "Reporte de viandas donadas";
    }

    @Override
    public String contenido(){
        return generarReporteViandas();
    }

    public String generarReporteViandas() {
        Map<String, Integer> viandasPorHumano = contarViandasPorHumano();
        StringBuilder contenido = new StringBuilder();
        contenido.append("Reporte de viandas donadas\n");
        contenido.append("Humano\tCantidad de viandas\n");
        for (Map.Entry<String, Integer> entry : viandasPorHumano.entrySet()) {
            contenido.append(entry.getKey()).append("\t").append(entry.getValue()).append("\n");
        }
        return contenido.toString();
    }

    private Map<String, Integer> contarViandasPorHumano() {
        List<Humano> humanos = humanosRepository.buscarTodos();

        Map<String, Integer> viandasPorHumano = new HashMap<>();

        for (Humano humano : humanos) {
            Long id = humano.getIdHumano();
            String idUsuario = humano.getIdUsuario().toString();
            List<DonacionDeVianda> donacionesDeVianda = donacionesDeViandaRepository.buscarPorColaborador(id);

            viandasPorHumano.put(idUsuario, donacionesDeVianda.size());
        }

        return viandasPorHumano;
    }
}
