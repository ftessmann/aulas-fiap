package ccr.entities;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Incidente extends BaseModel{
    private Localizacao localizacao;
    private String descricao;
    private Gravidade gravidade;
    private String nome;
    private Usuario criador;
    private LocalDateTime date;
}
