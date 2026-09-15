package boletoGenreator.useCases.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "adress")
public class EntityAdress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String state;

    @Column(length = 30)
    private String neighborhood;

    @Column(length = 55)
    private String adress;

    @Column(name = "adress_number")
    private Long adressNumber;

    @Column(name = "cep", length = 8)
    private String cep;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private EntityUser userAdress;
}
