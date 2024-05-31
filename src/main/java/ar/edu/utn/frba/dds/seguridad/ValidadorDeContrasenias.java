package ar.edu.utn.frba.dds.seguridad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ValidadorDeContrasenias {

    private ArrayList<CondicionContrasenia> condicionesACumplir;

    public ValidadorDeContrasenias(){
        this.condicionesACumplir = new ArrayList<>();
    }

    public boolean esValida(String contrasenia) throws IOException {
        for(CondicionContrasenia condicion : condicionesACumplir){
            if(!condicion.cumpleConCondicion(contrasenia)){
                return false;
            }
        }
        return true;
    }

    public void agregarCondiciones(CondicionContrasenia ... condiciones){
        Collections.addAll(condicionesACumplir, condiciones);
    }
}
