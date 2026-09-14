package boletoGenreator.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestClient;

import boletoGenreator.infrastructure.controller.mapper.contracts.ContractPdfUseCase;
import boletoGenreator.infrastructure.repository.BankBilletsRepository;
import boletoGenreator.infrastructure.repository.UserIntegrityRepository;
import boletoGenreator.infrastructure.repository.UserRepository;
import boletoGenreator.infrastructure.repository.combos.ComboStateRepository;
import boletoGenreator.infrastructure.repository.contracts.ContractBilletsRepository;
import boletoGenreator.infrastructure.repository.contracts.ContractRepository;
import boletoGenreator.useCases.impl.user.UserCustomRepository;
import boletoGenreator.useCases.impl.user.UserCustomRepositoryImpl;
import boletoGenreator.useCases.service.combos.ComboStateUseCase;
import boletoGenreator.useCases.service.contracts.MakeContractUseCase;
import boletoGenreator.useCases.service.contracts.SimulationUseCase;
import boletoGenreator.useCases.service.inAndOut.LoginUseCase;
import boletoGenreator.useCases.service.inAndOut.RegisterUseCase;
import boletoGenreator.useCases.service.jwt.JwtAuthorization;
import boletoGenreator.useCases.service.users.SearchUsersUseCase;
import boletoGenreator.useCases.service.users.UserPageUseCase;

@Configuration
public class Modules {

    //Configuration RestClient;
    @Bean
    public RestClient restClient(RestClient.Builder builder){
        return builder
               .baseUrl("http://localhost:8080")
               .build();
    }
    
    @Bean
    public LoginUseCase loginUseCase(UserRepository userRepository, JwtAuthorization jwtAuthorization){
        return new LoginUseCase(userRepository, jwtAuthorization);
    }

    @Bean
    public RegisterUseCase registerUseCase(UserRepository userRepository, UserIntegrityRepository userIntegrityRepository){
        return new RegisterUseCase(userRepository, userIntegrityRepository);
    }

    @Bean
    public MakeContractUseCase makeContractUseCase(UserRepository userRepository, BankBilletsRepository bankBilletsRepository, UserIntegrityRepository userIntegrityRepository, ContractBilletsRepository contractBilletsRepository, ContractRepository contractRepository){

        return new MakeContractUseCase(userRepository, bankBilletsRepository, userIntegrityRepository, contractBilletsRepository, contractRepository);
    }

    @Bean
    public SimulationUseCase simulationUseCase( UserRepository userRepository){
        return new SimulationUseCase( userRepository);
    }

    @Bean
    public UserCustomRepository userCustomRepository(JdbcTemplate jdbcTemplate){
        return new UserCustomRepositoryImpl(jdbcTemplate);
    }

    @Bean
    public ContractPdfUseCase contractPdfUseCase(UserCustomRepository userCustomRepository){

        return new ContractPdfUseCase(userCustomRepository);
    }

    @Bean
    public SearchUsersUseCase searchUsersUseCase(UserCustomRepository userCustomRepository){

        return new SearchUsersUseCase(userCustomRepository);
    }

    @Bean
    public UserPageUseCase userPageUseCase(UserRepository userRepository){
        return new UserPageUseCase(userRepository);
    }

    @Bean 
    public boletoGenreator.useCases.service.contracts.ContractInstallmentsUseCase contractInstallmentsUseCase (UserCustomRepository userCustomRepository){
        return new boletoGenreator.useCases.service.contracts.ContractInstallmentsUseCase(userCustomRepository);
    }

    @Bean 
    public ComboStateUseCase comboStateUseCase(ComboStateRepository coboStateRepository){
        return new ComboStateUseCase(coboStateRepository);
    }
}
