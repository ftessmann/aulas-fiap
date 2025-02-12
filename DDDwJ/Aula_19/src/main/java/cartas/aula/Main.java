package cartas.aula;

import cartas.aula.entities.Colecao;
import cartas.aula.repositories.ColecaoRepository;

public class Main {
    public static void main(String[] args) {
        var colecaoRepository = new ColecaoRepository();

        var novaColecao = new Colecao(1, "Colecao 1", "1ED", "2025-02-02");
        var outraColecao = new Colecao(2, "Segunda colecao", "2ED", "2021-04-02");

        colecaoRepository.adicionar(novaColecao);
        colecaoRepository.adicionar(outraColecao);

        System.out.println(colecaoRepository.listar());

    }
}
