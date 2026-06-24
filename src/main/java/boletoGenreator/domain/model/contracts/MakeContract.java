package boletoGenreator.domain.model.contracts;

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
public class MakeContract {
    private String idClient;
    private String bankBilletType;
    private String price;
}
