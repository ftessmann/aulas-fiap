package cartas.aula.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Colecao {
    private int id;
    private String nome;
    private String codigo;
    private String dataLancamento;

}
