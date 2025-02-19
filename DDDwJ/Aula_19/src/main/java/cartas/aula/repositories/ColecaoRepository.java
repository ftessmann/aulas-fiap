package cartas.aula.repositories;

import cartas.aula.entities.Colecao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
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
    public void delete(Colecao object) {
        object.setDeleted(true);
    }

    @Override
    public void deleteById(int id) {
        var set = colecoes.stream()
                .filter(s -> s.getId() == id)
                .findFirst();

        set.ifPresent(s -> s.setDeleted(true));
    }

    @Override
    public List<Colecao> listarTodos() {
        return colecoes;
    }

    @Override
    public List<Colecao> listar() {
        return colecoes.stream().filter(s -> !s.isDeleted()).toList();
    }

    public void exportar() {
        var conteudo = "este é um texto para exportar para um arquivo";
        var caminho = "./exports/" + LocalDateTime.now().toString() + "_colecoes.txt";

        try {
            var file = new File(caminho);
            if (!file.exists()) {
                file.createNewFile();
            }
            var writer = new FileWriter(file);
            writer.write(conteudo);
            writer.close();
            System.out.println("Arquivo exportado com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao exportar arquivo.");
        }
    }
}
