package ar.edu.utn.frba.dds.seguridad;

public class TieneMinuscula implements CondicionContrasenia{
    @Override
    public boolean cumpleConCondicion(String contrasenia) {
        return contrasenia.matches(".*[a-z].*");
    }
}
