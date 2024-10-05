package ar.edu.utn.frba.dds.dtos.direccion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DireccionInputDTO {
    private String calle;
    private String ciudad;
}
