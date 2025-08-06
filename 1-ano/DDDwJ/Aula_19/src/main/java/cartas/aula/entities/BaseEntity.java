package cartas.aula.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity {
    private int id;
    private boolean deleted;
    private LocalDateTime dataCriacao = LocalDateTime.now();
}
