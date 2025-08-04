package ccr.entities;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Usuario extends BaseModel {
    private String nome;
    private String cpf;
    private String email;
    private String senha; // TODO: hash para senha
    private String telefone;
    private Endereco endereco;
    private Cargo cargo;
}
