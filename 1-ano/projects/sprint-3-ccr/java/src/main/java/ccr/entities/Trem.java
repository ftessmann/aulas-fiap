package ccr.entities;

import lombok.*;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Trem extends BaseModel {
    private String modelo;
    private Estacao estacaoInicial;
    private Estacao estacaoFinal;
    private Linha linha;
    private ArrayList<Usuario> condutores;
    private ArrayList<Vagao> vagoes;
}
