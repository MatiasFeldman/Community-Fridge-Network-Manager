package ar.edu.utn.frba.dds.models.entities.reportes;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.DonacionDeVianda;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
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


    @Override
    public String nombre() {
        return "Reporte de viandas donadas";
    }

    @Override
    public String contenido() {
        return generarReporteViandas();
    }

    public String generarReporteViandas() {
        StringBuilder contenido = new StringBuilder();
        contenido.append("Reporte de viandas donadas\n");
        contenido.append("ColaboradorHumano\tCantidad de viandas\n");
        for (ColaboradorHumano colaboradorHumano : humanosRepository.buscarTodos()) {
            System.out.println(donacionesDeViandaRepository.cantViandasDonadasPor(colaboradorHumano));
            contenido.append(colaboradorHumano.getIdUsuario()).append("\t").append(donacionesDeViandaRepository.cantViandasDonadasPor(colaboradorHumano)).append("\n");
        }
        return contenido.toString();
    }
}
