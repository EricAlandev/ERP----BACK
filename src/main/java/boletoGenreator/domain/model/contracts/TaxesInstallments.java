package boletoGenreator.domain.model.contracts;

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
    private int quantityInstallments;
    private Long MaxPriceAllowed;
}
