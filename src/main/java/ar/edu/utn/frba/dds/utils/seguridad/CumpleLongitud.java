package ar.edu.utn.frba.dds.utils.seguridad;

import lombok.Getter;

@Getter
public class CumpleLongitud implements CondicionContrasenia{
    private String mensaje;
    private int longitudMinima;
    private int longitudMaxima;

    public CumpleLongitud(int longitudMinima, int longitudMaxima){
        this.longitudMinima = longitudMinima;
        this.longitudMaxima = longitudMaxima;
        this.mensaje = "La contraseña debe tener entre" + this.longitudMinima + " y " + this.longitudMaxima + " caracteres";
    }
    @Override
    public boolean cumpleConCondicion(String contrasenia) {
        return contrasenia.length() >= longitudMinima || contrasenia.length() <= longitudMaxima;
    }
}
