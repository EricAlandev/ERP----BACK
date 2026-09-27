package boletoGenreator.useCases.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
@Table(name = "bankbillets")
public class EntityBankBillet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 55, name = "typecontract")
    private String typeContract;

    @Column()
    private BigDecimal price;

    //PD - Pending
    //P  - Payed
    //L  - Late
    @Column(length = 2)
    private String stats;

    @Column(name = "payed_on_day")
    private LocalDateTime dateTime;

    @Column(length = 10, name = "late_fee")
    private BigDecimal lateFee;

    @Column(length = 20, name = "fee_mora")
    private BigDecimal FeeMora;

    @Column(name = "expirationdate", columnDefinition = "TEXT")
    private Timestamp expirationDate;

    //Pivo between the contracts + bankBillets
    @OneToMany(mappedBy = "bankBillets", cascade = CascadeType.ALL)
    private List<EntityContractBillet> billetContractPivo;
}
