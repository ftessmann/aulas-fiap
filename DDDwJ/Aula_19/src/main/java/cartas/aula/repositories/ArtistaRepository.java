package cartas.aula.repositories;

import cartas.aula.entities.Artista;

import java.util.ArrayList;
import java.util.List;

public class ArtistaRepository implements CrudRepository<Artista> {
    List<Artista> artistas = new ArrayList<>();

    @Override
    public void adicionar(Artista object) {
        artistas.add(object);
    }

    @Override
    public void atualizar(int id, Artista object) {
        for (Artista a : artistas) {
            if (a.getId() == id) {
                a = object;
            }
        }
    }

    @Override
    public void remover(Artista object) {
        artistas.remove(object);
    }

    @Override
    public void remover(int id) {
        artistas.removeIf(a -> a.getId() == id);
    }

    @Override
    public void delete(Artista object) {
        object.setDeleted(true);
    }

    @Override
    public void deleteById(int id) {
        var set = artistas.stream()
                .filter(a -> a.getId() == id)
                .findFirst();

        set.ifPresent(a -> a.setDeleted(true));
    }

    @Override
    public List<Artista> listarTodos() {
        return artistas;
    }

    @Override
    public List<Artista> listar() {
        return artistas.stream().filter(a -> !a.isDeleted()).toList();
    }
}
