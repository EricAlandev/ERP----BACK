package boletoGenreator.useCases.entity.user;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@Table(name = "userscore")
public class EntityUserScore {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "score_client")
    private BigDecimal scoreClient;

    //NC -> New Client
    //GC -> Good Client
    //BC -> Bad Client
    //AC - Average Client
    @Column(name = "payment_situation", length = 2)
    private String paymentSituation;

    @Column(name = "date_last_change")
    private LocalDateTime lastChange;

    @OneToOne  
    @JoinColumn(name = "user_id")
    private EntityUser userFromScore;

}   
