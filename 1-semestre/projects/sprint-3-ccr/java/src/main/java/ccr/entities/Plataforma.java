package ccr.entities;

import lombok.*;

@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Plataforma extends BaseModel {
    private String numero;
    private Estacao estacao;
}
