package boletoGenreator.domain.model.contracts;

import java.math.BigDecimal;

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
public class TaxesInstallments {
    private BigDecimal taxes;
    private int quantityInstallments;
}
