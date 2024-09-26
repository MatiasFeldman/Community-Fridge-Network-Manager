package ar.edu.utn.frba.dds.models.entities.tecnicos;

import ar.edu.utn.frba.dds.dtos.tecnicos.TecnicoDTO;
import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import ar.edu.utn.frba.dds.models.entities.personas.Contacto;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;


@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "tecnico")
public class Tecnico extends Persistente {

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_contacto", referencedColumnName = "id_contacto")
    private Contacto medioContacto;

    @Embedded
    private TipoTecnico tipo;

    @Column(name = "nro_documento")
    private String nroDocumento;

    @Column(name = "nro_cuil")
    private String nroCUIL;

    @Embedded
    private AreaCobertura areaCobertura;

    public static Tecnico create(TecnicoDTO dto) {
        return Tecnico.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .medioContacto(dto.getMedioContacto())
                .tipo(dto.getTipo())
                .nroDocumento(dto.getNroDocumento())
                .nroCUIL(dto.getNroCUIL())
                .areaCobertura(dto.getAreaCobertura())
                .build();
    }

    public boolean puedeIrA(Direccion direccion){
        return areaCobertura.seEncuentraEnRango(direccion);
    }

    public Double distanciaA(Direccion direccion){
        return areaCobertura.distanciaA(direccion);
    }
}
