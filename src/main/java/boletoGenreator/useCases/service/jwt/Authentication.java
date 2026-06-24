package boletoGenreator.useCases.service.jwt;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;

@Component
public class Authentication{

    private final JwtAuthorization jwtAuthorization;

    public Authentication(JwtAuthorization jwtAuthorization){
        this.jwtAuthorization = jwtAuthorization;
    }   

    public boolean Authorization(String token){

        Claims isAuthorizade = jwtAuthorization.isTokenValid(token);

        if(isAuthorizade != null){
            return true;
        }

            return false;
    }

    
}
