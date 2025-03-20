package ccr.entities;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Localizacao extends BaseModel {
    private String longitude;
    private String latitude;
}
