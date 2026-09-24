package boletoGenreator.infrastructure.controller.dto.contract;

import java.util.List;

import boletoGenreator.useCases.entity.user.EntityUser;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SimulationResponse {

    private String nameClient;
    private List<String> statsClient;
    private String QuantityInstallments;
    private Long maxPriceAllowed;

    public static SimulationResponse from(int QuantityInstallments, EntityUser client, List<String> stats, Long maxPrice){

        return SimulationResponse.builder()
        .nameClient(client.getEmail())
        .statsClient(stats)
        .QuantityInstallments(String.valueOf(QuantityInstallments))
        .maxPriceAllowed(maxPrice)
        .build();
    }
}
