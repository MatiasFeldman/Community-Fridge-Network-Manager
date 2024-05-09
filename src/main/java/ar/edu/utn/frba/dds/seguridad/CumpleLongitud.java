package ar.edu.utn.frba.dds.seguridad;

public class CumpleLongitud implements CondicionContrasenia{

    private int longitudMinima;
    private int longitudMaxima;

    public CumpleLongitud(int longitudMinima, int longitudMaxima){
        this.longitudMinima = longitudMinima;
        this.longitudMaxima = longitudMaxima;
    }
    @Override
    public boolean cumpleConCondicion(String contrasenia) {
        return contrasenia.length() >= longitudMinima || contrasenia.length() <= longitudMaxima;
    }
}
