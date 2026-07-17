package boletoGenreator.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import boletoGenreator.infrastructure.repository.BankBilletsRepository;
import boletoGenreator.infrastructure.repository.UserIntegrityRepository;
import boletoGenreator.infrastructure.repository.UserRepository;
import boletoGenreator.useCases.service.contracts.MakeContractUseCase;
import boletoGenreator.useCases.service.contracts.SimulationUseCase;
import boletoGenreator.useCases.service.inAndOut.LoginUseCase;
import boletoGenreator.useCases.service.inAndOut.RegisterUseCase;
import boletoGenreator.useCases.service.jwt.JwtAuthorization;

@Configuration
public class Modules {
    
    @Bean
    public LoginUseCase loginUseCase(UserRepository userRepository, JwtAuthorization jwtAuthorization){
        return new LoginUseCase(userRepository, jwtAuthorization);
    }

    @Bean
    public RegisterUseCase registerUseCase(UserRepository userRepository, UserIntegrityRepository userIntegrityRepository){
        return new RegisterUseCase(userRepository, userIntegrityRepository);
    }

    @Bean
    public MakeContractUseCase makeContractUseCase(UserRepository userRepository, BankBilletsRepository bankBilletsRepository){
        return new MakeContractUseCase(userRepository, bankBilletsRepository);
    }

    @Bean
    public SimulationUseCase simulationUseCase( UserRepository userRepository){
        return new SimulationUseCase( userRepository);
    }
}
