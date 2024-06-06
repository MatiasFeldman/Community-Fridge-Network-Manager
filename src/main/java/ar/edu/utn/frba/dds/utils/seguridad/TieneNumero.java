package ar.edu.utn.frba.dds.utils.seguridad;

public class TieneNumero implements CondicionContrasenia{
    @Override
    public boolean cumpleConCondicion(String contrasenia) {
        return contrasenia.matches(".*[0-9].*");
    }
}
