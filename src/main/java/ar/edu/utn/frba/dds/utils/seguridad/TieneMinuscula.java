package ar.edu.utn.frba.dds.utils.seguridad;

public class TieneMinuscula implements CondicionContrasenia{
    @Override
    public boolean cumpleConCondicion(String contrasenia) {
        return contrasenia.matches(".*[a-z].*");
    }
}
