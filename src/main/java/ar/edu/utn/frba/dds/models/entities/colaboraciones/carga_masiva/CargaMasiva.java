package ar.edu.utn.frba.dds.models.entities.colaboraciones.carga_masiva;

public class CargaMasiva {
    private String path;
    private ConversorCSV conversor;

    public CargaMasiva(String path, ConversorCSV conversor) {
        this.conversor = conversor;
    }

    public void cargar() { // propagar o hacer el catch del exception y convertirla
        conversor.convertir(path);
    }

}
