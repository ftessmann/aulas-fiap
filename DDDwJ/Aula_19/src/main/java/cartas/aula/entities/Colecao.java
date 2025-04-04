package cartas.aula.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Colecao extends BaseEntity {
    private String nome;
    private String codigo;
    private String dataLancamento;

}
