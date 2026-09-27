package boletoGenreator.useCases.entity.user;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Getter 
@Setter 
@Table (name = "profession")
public class EntityUserProfession {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job", length = 55)
    private String job;

    @Column(name = "salary")
    private BigDecimal salary;

    @OneToOne 
    @JoinColumn(name = "user_id")
    private EntityUser userByProfession;
}
