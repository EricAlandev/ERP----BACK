package boletoGenreator.domain.model.contracts;

import java.math.BigDecimal;

import boletoGenreator.domain.model.contracts.client.ClientPreData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DealContract extends ClientPreData{
    private BigDecimal priceInstallments;
    private Long QuantityInstallments;
    private String BankBilletType;
}
