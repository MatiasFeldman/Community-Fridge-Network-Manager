package ar.edu.utn.frba.dds.utils.seguridad;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class ValidadorDeContrasenias {

    private ArrayList<CondicionContrasenia> condicionesACumplir;

    public ValidadorDeContrasenias(){
        this.condicionesACumplir = new ArrayList<>();
    }

    public Boolean esValida(String contrasenia) {
        for(CondicionContrasenia condicion : condicionesACumplir){
            if(!condicion.cumpleConCondicion(contrasenia)){
                return false;
            }
        }
        return true;
    }

    @SneakyThrows
    public Optional<CondicionContrasenia> condicionQueNoCumple(String contrasenia){
        return condicionesACumplir
                .stream()
                .filter(c -> !c.cumpleConCondicion(contrasenia))
                .findFirst();
    }

    public void agregarCondiciones(CondicionContrasenia ... condiciones){
        Collections.addAll(condicionesACumplir, condiciones);
    }
}
