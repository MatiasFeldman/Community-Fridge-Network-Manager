package ar.edu.utn.frba.dds.seguridad;

public class TieneMayuscula implements CondicionContrasenia{
    @Override
    public boolean cumpleConCondicion(String contrasenia) {
        return contrasenia.matches(".*[A-Z].*");
    }
}
