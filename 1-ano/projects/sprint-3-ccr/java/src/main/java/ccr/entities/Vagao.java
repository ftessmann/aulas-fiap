package ccr.entities;

import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Vagao extends BaseModel {
    private Trem trem;
    private String numeracao;
}
