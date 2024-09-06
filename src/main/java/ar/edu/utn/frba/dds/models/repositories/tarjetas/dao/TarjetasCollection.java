package ar.edu.utn.frba.dds.models.repositories.tarjetas.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Tarjeta;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaHumano;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TipoTarjeta;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TarjetasCollection implements TarjetasDAO {
    private List<Tarjeta> tarjetas;

    @Override
    public Optional<Tarjeta> buscarPorDuenio(Long id, TipoTarjeta tipo) {
        switch (tipo) {
            case HUMANO -> {
                return tarjetas
                        .stream()
                        .filter(t -> (t.getDuenioId().equals(id) && t.getTipoTarjeta() == TipoTarjeta.HUMANO))
                        .findFirst();
            }
            case VULNERABLE -> {
                return tarjetas
                        .stream()
                        .filter(t -> (t.getDuenioId().equals(id) && t.getTipoTarjeta() == TipoTarjeta.VULNERABLE))
                        .findFirst();
            }
            default -> {
                return Optional.empty();
            }
        }
    }

    @Override
    public Optional<Tarjeta> buscarPorId(Long idTarjetaRepartida) {
        return tarjetas
                .stream()
                .filter(t -> t.getId().equals(idTarjetaRepartida))
                .findFirst();
    }
}
