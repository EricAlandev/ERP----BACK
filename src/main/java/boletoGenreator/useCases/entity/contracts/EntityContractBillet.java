package boletoGenreator.useCases.entity.contracts;

import boletoGenreator.useCases.entity.EntityBankBillet;
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
@Table(name = "contractbillets")
public class EntityContractBillet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private EntityContracts contracts;

    @ManyToOne
    @JoinColumn(name = "bankbillet_id")
    private EntityBankBillet bankBillets;
}
