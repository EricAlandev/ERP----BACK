package boletoGenreator.domain.model.users;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import boletoGenreator.useCases.entity.user.EntityUserScore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
public class UserLimitsDTO {
    
    private Long idUser;


    //profession columns
    private String job;
    private BigDecimal salary;

    private EntityUserScore score;

    //score data that we gonna use
    //private Long idScore;
    //private BigDecimal scoreClient;
    //private String paymentSituation;
    //private LocalDateTime lastChange;
}
