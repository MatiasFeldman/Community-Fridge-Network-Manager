package ar.edu.utn.frba.dds.models.repositories.rubros.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Rubro;
import lombok.AllArgsConstructor;


import java.util.List;
import java.util.Optional;
@AllArgsConstructor
public class RubroCollection implements RubroDAO{
    private List<Rubro> rubro;

    @Override
    public void guardar(Rubro rubro){this.rubro.add(rubro);}

    @Override
    public void eliminar(Rubro rubro){this.rubro.remove(rubro);}

    @Override
    public void modificar(Rubro rubro) {
        Optional<Rubro> rubroOptional = this.buscarPorId(rubro.getId());
        rubroOptional.ifPresent(rubro1 -> {
            this.rubro.remove(rubro1);
            this.rubro.add(rubro);
        });
    }

    @Override
    public List<Rubro> buscarTodos() {
        return this.rubro;
    }

    @Override
    public Optional<Rubro> buscarPorId(Long id) {
        return rubro
                .stream()
                .filter(rubro -> rubro.getId().equals(id))
                .findFirst();
    }
}
