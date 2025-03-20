package ccr.entities;

import lombok.*;

import java.util.ArrayList;

@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Linha extends BaseModel {
    private String nome;
    private ArrayList<Estacao> estacoes;
    private ArrayList<Trem> trens;
}
