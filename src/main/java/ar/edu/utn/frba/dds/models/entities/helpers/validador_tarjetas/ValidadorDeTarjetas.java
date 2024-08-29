package ar.edu.utn.frba.dds.models.entities.helpers.validador_tarjetas;

import ar.edu.utn.frba.dds.exceptions.UsuarioSinTarjetaException;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.Tarjeta;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaHumano;
import ar.edu.utn.frba.dds.models.repositories.tarjetas.TarjetasRepository;

import java.util.Optional;
import java.util.UUID;

public class ValidadorDeTarjetas {
    private static TarjetasRepository tarjetas;


    public static Tarjeta tieneTarjeta(UUID id){
        Optional<Tarjeta> tarjetaPosible = tarjetas.buscarTarjetaPorDuenio(id);
        if(tarjetaPosible.isPresent()){
            return tarjetaPosible.get();
        }
        throw new UsuarioSinTarjetaException("El usuario no tiene tarjeta");
    }
}
