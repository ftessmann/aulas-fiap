package cartas.aula.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// implementação genérica dos repositories
public class CrudRepositoryImpl<T> implements CrudRepository<T> {
    private Map<Long, T> storage = new HashMap<>();
    private long currentId = 1;

    @Override
    public void adicionar(T object) {

    }

    @Override
    public void atualizar(int id, T object) {

    }

    @Override
    public void remover(T object) {

    }

    @Override
    public void remover(int id) {

    }

    @Override
    public void delete(T object) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public List<T> listarTodos() {
        return List.of();
    }

    @Override
    public List<T> listar() {
        return List.of();
    }
}
