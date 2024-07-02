package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dtos.incidentes.DenunciaFallaTecnicaDTO;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Accionador;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.DenunciaFallaTecnica;

public class HeladerasController {
    private Accionador accionador;

    public HeladerasController(Accionador accionador){
        this.accionador = accionador;
    }

    public void reportarFallaTecnica(Object solicitud){
        DenunciaFallaTecnicaDTO dto = (DenunciaFallaTecnicaDTO) solicitud;
        DenunciaFallaTecnica denuncia = DenunciaFallaTecnica.of(dto);

        accionador.registrarFallaTecnica(denuncia);
    }

}
