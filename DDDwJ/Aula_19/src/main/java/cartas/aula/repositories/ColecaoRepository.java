package cartas.aula.repositories;

import cartas.aula.entities.Colecao;
import cartas.aula.extentions.LocalDateTimeJsonAdapter;
import cartas.aula.infrastructure.DatabaseConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Passa o tipo correto no generic
public class ColecaoRepository implements CrudRepository<Colecao> {
    private static final Logger log = LogManager.getLogger(ColecaoRepository.class);
    private List<Colecao> colecoes = new ArrayList<>(List.of(
//            new Colecao("Primeira edição", "1ED", "2025-02-20"),
//            new Colecao("Segunda edição", "2ED", "2025-02-22"),
//            new Colecao("Terceira edição", "3ED", "2025-02-24")
    ));

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
        var query = "select * from colecao";

        try {
            var connection = DatabaseConfig.getConnection();
            var statement = connection.prepareStatement(query);
            var result = statement.executeQuery();

            while (result.next()) {
                var colecao = new Colecao();
                colecao.setId(result.getInt("id"));
                colecao.setDeleted(result.getBoolean("deleted"));
                colecao.setNome(result.getString("nome"));
                colecao.setCodigo(result.getString("codigo"));
                colecao.setDataLancamento(result.getString("datalancamento"));

                colecoes.add(colecao);
            }

            connection.close();
            return colecoes;

        } catch (SQLException error) {
            log.error("Erro ao conectar:", error);
        }
        return null;
    }

    @Override
    public List<Colecao> listar() {
        return colecoes.stream().filter(s -> !s.isDeleted()).toList();
    }

    public void exportar() {
        var conteudo = "este é um texto para exportar para um arquivo";
        var caminho = "./exports/" + LocalDateTime.now() + "_colecoes.txt";

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

    public void importar(String path) {
        String caminho = "./exports/" + path;

        try {
            var file = new File(caminho);
            var reader = new FileReader(file);
            var content = "";
            while (reader.ready()) {
                content += (char) reader.read();
            }
            System.out.println(content);
        } catch (IOException e) {
            System.out.println("Erro ao importar arquivo");
        }
    }

    public void exportarArquivoGrande() {
        var guid = UUID.randomUUID().toString();

        var path = "./exports/" + guid + "_colecoes.txt";

        try {
            var newFile = new File(path);

            if (!newFile.exists()) {
                var writer = new BufferedWriter(
                        new FileWriter(path)
                );

                var conteudoGrande = new StringBuilder();

                for(int i = 0; i < 100000000; i++ ) {
                    conteudoGrande.append("Linha ").append(i).append("\n");
                }

                writer.write(conteudoGrande.toString());
                writer.close();
                System.out.println("Arquivo exportado com sucesso");
            }
        } catch (Exception e) {
            System.out.println("Erro ao exportar arquivo");
        }
    }

    public void exportarParaJson() {
        var guid = UUID.randomUUID().toString();
        var path = "./exports/" + guid + "_colecoes.json";

        try {
            var gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonAdapter())
                    .setPrettyPrinting()
                    .create();
            var json = gson.toJson(colecoes);
            var file = new File(path);
            var fileWriter = new FileWriter(file);
            fileWriter.write(json);
            fileWriter.close();
            System.out.println("Arquivo exportado com sucesso");
        } catch (Exception e) {
            log.error("Erro ao exportar arquivo");
            System.out.println("Erro ao exportar arquivo");
        }
    }

    public void importarDeJson(String arquivo) {
        try {
            var gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonAdapter())
                    .create();

            var path = "./exports/" + arquivo;
            var reader = new FileReader(path);
            var colecoesJson = gson.fromJson(reader, Colecao[].class);
            for (var c: colecoesJson) {
                adicionar(c);
            }
        } catch (Exception e) {
            log.error("Erro ao importar arquivo", e);
            System.out.println("Erro ao importar arquivo");
        }
    }


}
