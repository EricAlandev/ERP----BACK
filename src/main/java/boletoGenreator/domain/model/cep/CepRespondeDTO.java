package boletoGenreator.domain.model.cep;

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
public class CepRespondeDTO {
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade; 
    private String uf;      
}