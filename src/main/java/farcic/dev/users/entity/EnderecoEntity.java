package farcic.dev.users.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_endereco")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnderecoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "rua", nullable = false, length = 120)
    private String rua;
    @Column(name = "numero", nullable = false, length = 20)
    private String numero;
    @Column(name = "complemento", length = 100)
    private String complemento;
    @Column(name = "bairro", nullable = false, length = 100)
    private String bairro;
    @Column(name = "cidade", nullable = false, length = 100)
    private String cidade;
    @Column(name = "estado", nullable = false, length = 2)
    private String estado;
    @Column(name = "cep", nullable = false, length = 9)
    private String cep;

}
