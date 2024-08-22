package ar.edu.utn.frba.dds.models.entities.usuarios;

import java.util.ArrayList;

public class Rol {
    private String nombre;
    private ArrayList<Permiso> permisos;

    public Rol(String nombre){
        this.nombre = nombre;
        this.permisos = new ArrayList<Permiso>();
    }
    
    public static Rol valueOf(String nombre){
        return new Rol(nombre);
    }

    public boolean tienePermiso(Permiso permiso){
        return permisos.contains(permiso);
    }
}
