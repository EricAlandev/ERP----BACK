package boletoGenreator.domain.model.contracts;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealContract {
    private Long idClient;
    private String nameClient;
    private BigDecimal priceInstallments;
    private Long QuantityInstallments;
    private String BankBilletType;
}
