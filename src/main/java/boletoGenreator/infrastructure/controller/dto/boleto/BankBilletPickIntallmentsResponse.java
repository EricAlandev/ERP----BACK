package boletoGenreator.infrastructure.controller.dto.boleto;

import java.math.BigDecimal;
import java.util.List;

import boletoGenreator.useCases.entity.EntityBankBillet;
import boletoGenreator.useCases.entity.contracts.EntityContractBillet;
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
public class BankBilletPickIntallmentsResponse {
    
    private List<Installment> bankBillets;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Installment{
        private Long id;
        private String typeContract;
        private BigDecimal price;
        private String stats;

        public static Installment from(EntityContractBillet cb){
            EntityBankBillet bankBillet = cb.getBankBillets();

            return Installment.builder()
            .id(bankBillet.getId())
            .typeContract(bankBillet.getTypeContract())
            .price(bankBillet.getPrice())
            .stats(bankBillet.getStats())
            .build();
        }
    }

    public static BankBilletPickIntallmentsResponse from(List<EntityContractBillet> pivo){

        List<BankBilletPickIntallmentsResponse.Installment> installments = pivo.stream()
        .map((p) -> BankBilletPickIntallmentsResponse.Installment.from(p))
        .toList();

        return BankBilletPickIntallmentsResponse.builder()
        .bankBillets(installments)
        .build();
    }
}
