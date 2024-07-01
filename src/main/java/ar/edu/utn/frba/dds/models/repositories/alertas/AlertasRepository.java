package ar.edu.utn.frba.dds.models.repositories.alertas;

import ar.edu.utn.frba.dds.models.entities.comandos.Alerta;

import java.util.List;

public class AlertasRepository {
    private List<Alerta> alertas;
    public void guardar(Alerta alerta) {
        alertas.add(alerta);
    }

    public List<Alerta> obtenerAlertas() {
        return alertas;
    }

    public void eliminar(Alerta alerta) {
        alertas.remove(alerta);
    }

    public boolean existeAlerta(Alerta alerta) {
        return alertas.contains(alerta);
    }


}
