package cartas.aula.repositories;

import cartas.aula.entities.Carta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartaRepository implements CrudRepository<Carta> {
    private List<Carta> cartas = new ArrayList<>();

    @Override
    public void adicionar(Carta object) {
        cartas.add(object);
    }

    @Override
    public void atualizar(int id, Carta object) {
        for (Carta c : cartas) {
            if (c.getId() == id) {
                c = object;
            }
        }
    }

    @Override
    public void remover(Carta object) {
        cartas.remove(object);
    }

    @Override
    public void remover(int id) {
        cartas.removeIf(c -> c.getId() == id);
    }

    @Override
    public List<Carta> listar() {
        return cartas;
    }
}
