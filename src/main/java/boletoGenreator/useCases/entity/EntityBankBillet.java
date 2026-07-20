package boletoGenreator.useCases.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import boletoGenreator.useCases.entity.contracts.EntityContractBillet;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "bankBillets")
public class EntityBankBillet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 55)
    private String typeContract;

    @Column()
    private BigDecimal price;

    @Column(length = 2)
    private String stats;

    @Column(name = "expirationDate", columnDefinition = "TEXT")
    private Timestamp expirationDate;

    //Pivo between the contracts + bankBillets
    @OneToMany(mappedBy = "bankBillets", cascade = CascadeType.ALL)
    private List<EntityContractBillet> billetContractPivo;
}
