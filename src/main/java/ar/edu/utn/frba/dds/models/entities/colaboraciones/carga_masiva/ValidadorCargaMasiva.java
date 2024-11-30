package ar.edu.utn.frba.dds.models.entities.colaboraciones.carga_masiva;

import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
public class ValidadorCargaMasiva {
    private List<String> tiposDocumento = List.of("DNI", "LE", "LC");
    private List<String> formaColaboracionValida = List.of("DINERO", "DONACION_VIANDAS", "REDISTRIBUCION_VIANDAS","ENTREGA_VIANDAS");
    private Integer longitudMaxTipoDoc = 3;
    private Integer longitudMaxDoc = 10;
    private Integer longitudMaxNombre = 50;
    private Integer longitudMaxApellido = 50;
    private Integer longitudMaxMail = 50;
    private Integer longitudMaxFormaColaboracion = 22;
    private Integer longitudMaxCantidad = 7;


    public boolean validarLinea(String[] line){

        System.out.println("Validando linea");

        String tipoDocumento = line[0];
        String documento = line[1];
        String nombre = line[2];
        String apellido = line[3];
        String mail = line[4];
        String formaColaboracion = line[6];
        Integer cantidad = Integer.parseInt(line[7]);


        return cumpleTipoDNI(tipoDocumento, documento) &&
                cumpleFromatoMail(mail) &&
                cumpleFormaColaboracion(formaColaboracion) &&
                cumpleLongitudMaximaElNombre(nombre, apellido) &&
                cumpleLongitudCantidad(cantidad);
    }

    public boolean cumpleTipoDNI(String tipo, String nro){
        return tiposDocumento.stream().anyMatch(tipo::equals) && nro.length() <= longitudMaxDoc && tipo.length() <= longitudMaxTipoDoc;
    }

    public boolean cumpleFromatoMail(String mail){
        return mail.matches(".+@.+\\..+") && mail.length() <= longitudMaxMail;
    }

    public boolean cumpleFormaColaboracion(String forma){
        return formaColaboracionValida.stream().anyMatch(forma::equals) && forma.length() <= longitudMaxFormaColaboracion;
    }

    public void agregarFormaColaboracionValida(String forma){
        formaColaboracionValida.add(forma);
    }

    public void agregarTipoDocumentoValido(String tipo){
        tiposDocumento.add(tipo);
    }

    public boolean cumpleLongitudMaximaElNombre(String nombre, String apellido) {
        return nombre.length() <= longitudMaxNombre && apellido.length() <= longitudMaxApellido;
    }

    public boolean cumpleLongitudCantidad(Integer cantidad) {
        return cantidad.toString().length() <= longitudMaxCantidad;
    }
}
