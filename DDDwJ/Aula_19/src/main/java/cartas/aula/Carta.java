package cartas.aula;

import lombok.*;

@Data // cria getters e setters, equals, hashcode e tostring
@NoArgsConstructor // cria construtor vazio
@AllArgsConstructor // cria contrutor completo
public class Carta {
    @DataField(prompt = "Nome da carta")
    private String nome;

    @DataField(prompt = "Texto da carta")
    private String texto;

    @DataField(prompt = "Artista da carta")
    private String artista;
}
