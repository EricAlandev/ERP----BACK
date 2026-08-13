package boletoGenreator.domain.model.contracts;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import boletoGenreator.domain.model.contracts.client.ClientPreData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ContractData extends ClientPreData {
    private Long idContract;
    private String typeContract;
    private Timestamp datecontract;
    private List<BankBillet> bankBillets;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BankBillet{
        private Long id;
        private BigDecimal price;
        private String stats;
        private String typeContract;
        private LocalDateTime expirationdate;
    }
}
