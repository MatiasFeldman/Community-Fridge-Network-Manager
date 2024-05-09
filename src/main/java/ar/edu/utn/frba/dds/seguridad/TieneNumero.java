package ar.edu.utn.frba.dds.seguridad;

public class TieneNumero implements CondicionContrasenia{
    @Override
    public boolean cumpleConCondicion(String contrasenia) {
        return contrasenia.matches(".*[0-9].*");
    }
}
