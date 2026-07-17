package boletoGenreator.infrastructure.controller.dto.contract;

import java.math.BigDecimal;
import java.util.List;

import boletoGenreator.useCases.entity.user.EntityUser;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SimulationResponse {

    private Long idClient;
    private String nameClient;
    private String BankBilletType;
    private List<String> statsClient;
    private BigDecimal taxes;
    private int QuantityInstallments;
    private Long price;

    public static SimulationResponse from(BigDecimal taxes, int QuantityInstallments, EntityUser client, List<String> stats, Long price, String BankBilletType){

        return SimulationResponse.builder()
        .idClient(client.getId())
        .nameClient(client.getEmail())
        .statsClient(stats)
        .taxes(taxes)
        .price(price)
        .BankBilletType(BankBilletType)
        .QuantityInstallments(QuantityInstallments)
        .build();
    }
}
