package boletoGenreator.useCases.entity.contracts;
import java.util.List;

import boletoGenreator.useCases.entity.user.EntityUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "contracts")
public class EntityContracts {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 55)
    private String typeContract;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private EntityUser contractsUser;

    //relation of the contratc + bankBillets;
    @OneToMany(mappedBy = "contracts", cascade = CascadeType.ALL)
    private List<EntityContractBillet> contractBilletPivo;
}
