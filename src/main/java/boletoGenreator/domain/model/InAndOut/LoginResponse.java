package boletoGenreator.domain.model.InAndOut;

import boletoGenreator.infrastructure.controller.dto.inAndOut.LoginDTO;
import boletoGenreator.useCases.entity.user.EntityUser;

public class LoginResponse {
    public static LoginDTO from(EntityUser user){

        return LoginDTO.builder()
            .email(user.getEmail())
            .token(user.getToken())
            .build();
    }
}
