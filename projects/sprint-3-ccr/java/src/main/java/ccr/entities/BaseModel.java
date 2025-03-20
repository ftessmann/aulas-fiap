package ccr.entities;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public abstract class BaseModel {
    private int id;
    @Setter
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    /*
     * Por algum motivo foi necessário adicionar o @Setter ao createdAt
     * Em alguns repositories ele não estava sendo passado usando o @Data
     */
}
