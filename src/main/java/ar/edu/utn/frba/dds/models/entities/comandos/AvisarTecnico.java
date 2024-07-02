package ar.edu.utn.frba.dds.models.entities.comandos;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.models.repositories.tecnicos.TecnicosRepository;

import java.util.Optional;

public class AvisarTecnico implements Comando{
    private TecnicosRepository tecnicos;

    public AvisarTecnico(TecnicosRepository tecnicos){
        this.tecnicos = tecnicos;
    }


    @Override
    public void ejecutar(Heladera heladera, String mensaje) {
        Direccion origen = heladera.getDireccion();
        Optional<Tecnico> tecnico = tecnicos.buscarMasCercano(origen);
        if(tecnico.isPresent()){
            //Mandarle al técnico q tiene q ir a arreglar la heladera
        }
        else {
            // Avisar q no hay técnicos disponibles
        }
    }
}
