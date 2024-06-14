package ar.edu.utn.frba.dds.models.entities.colaboraciones.carga_masiva;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class ValidadorCargaMasiva {
    private List<String> tiposDocumento = List.of("DNI", "LE", "LC");
    private List<String> formaColaboracion = List.of("DINERO", "DONACION_VIANDAS", "REDISTRIBUCION_VIANDAS","ENTREGA_VIANDAS");

    public boolean cumpleTipoDNI(String tipo){
        return tiposDocumento.stream().anyMatch(tipo::equals);
    }

    public boolean cumpleFromatoMail(String mail){
        return mail.matches(".+@.+\\..+");
    }

    public boolean cumpleFormaColaboracion(String forma){
        return formaColaboracion.stream().anyMatch(forma::equals);
    }

    public void agregarFormaColaboracionValida(String forma){
        formaColaboracion.add(forma);
    }

    public void agregarTipoDocumentoValido(String tipo){
        tiposDocumento.add(tipo);
    }
}
