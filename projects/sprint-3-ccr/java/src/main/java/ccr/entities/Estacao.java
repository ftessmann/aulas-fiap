package ccr.entities;

import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Estacao extends BaseModel {
    private String nome;
    private Endereco endereco;
    private ArrayList<Plataforma> plataformas;
    private ArrayList<Linha> linhas;
}
