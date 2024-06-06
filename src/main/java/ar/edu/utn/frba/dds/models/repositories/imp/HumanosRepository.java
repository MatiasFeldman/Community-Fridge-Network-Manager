package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.models.entities.personas.Humano;

import java.util.ArrayList;
import java.util.List;

public class HumanosRepository {
    private List<Humano> humanos;
    public static HumanosRepository instance;

    public HumanosRepository() {
        this.humanos = new ArrayList<>();
    }

    public static HumanosRepository getInstance() {
        if (instance == null) {
            instance = new HumanosRepository();
        }
        return instance;
    }

    public void addHumano(Humano humano) {
        this.humanos.add(humano);
    }

    public void removeHumano(Humano humano) {
        this.humanos.remove(humano);
    }

    public List<Humano> getHumanos() {
        return this.humanos;
    }

    public Humano getHumanoByDocumento(String documento) {
        return this.humanos.stream().filter(humano -> humano.getAtributosObligatorios().stream().anyMatch(atributo -> atributo.getNombreAtributo().equals("documento") && atributo.getValorAtributo().equals(documento))).findFirst().orElse(null);
    }
}
