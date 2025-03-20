package ccr.entities;

import lombok.*;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Equipe extends BaseModel {
    private String nome;
    private ArrayList<Usuario> integrantes;
    private Localizacao localizacao;
    private Estacao base;
}
