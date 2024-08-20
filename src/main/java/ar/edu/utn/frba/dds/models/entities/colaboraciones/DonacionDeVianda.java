package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.exceptions.UsuarioSinTarjetaException;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.repositories.tarjetas.TarjetasRepository;
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
    @Getter
    private Heladera heladera;

    private static TarjetasRepository tarjetasRepository = new TarjetasRepository();

    public DonacionDeVianda(TarjetaHumano solicitante, Heladera heladera) {
        this.solicitante = solicitante;
        this.finalizada = false;
        this.heladera = heladera;
    }

    public static DonacionDeVianda of(UUID idSolicitante, Heladera heladera){
        Optional<TarjetaHumano> tarjeta = tarjetasRepository.buscarTarjetaPorDuenio(idSolicitante);
        if (tarjeta.isPresent()){
            return new DonacionDeVianda(tarjeta.get(), heladera);
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
