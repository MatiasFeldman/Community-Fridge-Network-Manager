package ar.edu.utn.frba.dds.models.entities.comandos;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.models.repositories.tecnicos.TecnicosRepository;

public class AvisarTecnico implements Comando{
    private TecnicosRepository tecnicos;

    public AvisarTecnico(TecnicosRepository tecnicos){
        this.tecnicos = tecnicos;
    }


    @Override
    public void ejecutar(Heladera heladera, String mensaje) {

    }
}
