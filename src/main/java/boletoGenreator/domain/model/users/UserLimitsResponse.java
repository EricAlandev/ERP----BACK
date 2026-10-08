package boletoGenreator.domain.model.users;

import java.math.BigDecimal;

import boletoGenreator.domain.model.limits.PreLimitsDTO;
import boletoGenreator.useCases.entity.user.EntityUser;
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
public class UserLimitsResponse {
    private BigDecimal maxLoan;
    private Long quantityInstallments;
    private UserLimits user;
    
    @Getter 
    @Setter 
    @NoArgsConstructor 
    @AllArgsConstructor 
    @Builder 
    public static class UserLimits{
        private String email;
        private String typeUser;
        private String cic;
        private String gender;
    }

    public static UserLimitsResponse from(PreLimitsDTO dto, EntityUser vanillaUser){

        return UserLimitsResponse.builder()
        .maxLoan(dto.getMaxLoan())
        .quantityInstallments(dto.getQuantityInstallments())
        .user(UserLimits.builder()
            .email(vanillaUser.getEmail())
            .gender(vanillaUser.getGender())
            .cic(vanillaUser.getCic())
            .typeUser(vanillaUser.getTypeUser())
            .build()
        )
        .build();
    }
}
