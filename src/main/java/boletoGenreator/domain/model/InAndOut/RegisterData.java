package boletoGenreator.domain.model.InAndOut;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class RegisterData {
    
    @NotNull(message = "email cannot be null")
    @NotBlank(message = "email cannot be blank")
    @NotEmpty(message = "email cannot be epty")
    private String email;

    @NotNull(message = "password cannot be null")
    @NotBlank(message = "password cannot be blank")
    @NotEmpty(message = "password cannot be epty")
    private String password;

    @NotNull(message = "birthday cannot be null")
    @NotBlank(message = "birthday cannot be blank")
    @NotEmpty(message = "birthday cannot be epty")
    private String birthday;

    private String gender;

    //C - Company; P - Person
    @NotNull(message = "typeUser cannot be null")
    @NotBlank(message = "typeUser cannot be blank")
    @NotEmpty(message = "typeUser cannot be epty")
    private String typeUser;

    @NotNull(message = "cic cannot be null")
    @NotBlank(message = "cic cannot be blank")
    @NotEmpty(message = "cic cannot be epty")
    private String cic;

    private RegisterData.AdressData adressData;

    @Getter 
    @Setter 
    @Builder 
    @NoArgsConstructor 
    @AllArgsConstructor 
    public static class AdressData{
        @NotNull(message = "cep cannot be null")
        @NotBlank(message = "cep cannot be blank")
        @NotEmpty(message = "cep cannot be epty")
        private String cep;

        @NotNull(message = "state cannot be null")
        @NotBlank(message = "state cannot be blank")
        @NotEmpty(message = "state cannot be epty")
        private String state;

        @NotNull(message = "neighborhood cannot be null")
        @NotBlank(message = "neighborhood cannot be blank")
        @NotEmpty(message = "neighborhood cannot be epty")
        private String neighborhood;

        @NotNull(message = "adress cannot be null")
        @NotBlank(message = "adress cannot be blank")
        @NotEmpty(message = "adress cannot be epty")
        private String adress;

        @NotNull(message = "adressNumber cannot be null")
        @NotBlank(message = "adressNumber cannot be blank")
        @NotEmpty(message = "adressNumber cannot be epty")
        private String adressNumber;
    }
}
