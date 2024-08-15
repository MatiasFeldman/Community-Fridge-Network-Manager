package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.exceptions.UsuarioSinTarjetaException;
import lombok.*;
import java.util.Optional;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class DonacionDeVianda implements ContribucionHumana{
    private TarjetaHumano solicitante;
    @Setter
    private Boolean finalizada;


    public DonacionDeVianda(TarjetaHumano solicitante) {
        this.solicitante = solicitante;
        this.finalizada = false;
    }

    public static DonacionDeVianda of(UUID idSolicitante){
        Optional<TarjetaHumano> tarjeta = tarjetasRepository.buscarTarjetaDe(idSolicitante);
        if (tarjeta.isPresent()){
            return new DonacionDeVianda(tarjeta.get());
        }
        throw new UsuarioSinTarjetaException("El usuario no tiene tarjeta");
    }



    @Override
    public double calcularPuntaje() {
        if (finalizada){
            ConstantesMultiplicativas constantes = new ConstantesMultiplicativas();
            return constantes.getCteViandasDonadas();
        }
        return 0;
    }



}
