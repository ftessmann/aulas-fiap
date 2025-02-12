package cartas.aula.repositories;

import cartas.aula.entities.Colecao;

import java.util.ArrayList;
import java.util.List;

// Passa o tipo correto no generic
public class ColecaoRepository implements CrudRepository<Colecao> {
    private List<Colecao> colecoes = new ArrayList<>();

    @Override
    public void adicionar(Colecao object) {
        colecoes.add(object);
    }

    @Override
    public void atualizar(int id, Colecao object) {
        for (Colecao c : colecoes) {
            if (c.getId() == id) {
                c = object;
            }
        }
    }

    @Override
    public void remover(Colecao object) {
        colecoes.remove(object);
    }

    @Override
    public void remover(int id) {
        colecoes.removeIf(c -> c.getId() == id);
    }

    @Override
    public List<Colecao> listar() {
        return colecoes;
    }
}
