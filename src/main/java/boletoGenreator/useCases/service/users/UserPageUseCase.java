package boletoGenreator.useCases.service.users;

import java.util.List;

import boletoGenreator.infrastructure.repository.UserRepository;
import boletoGenreator.useCases.UseCase;
import boletoGenreator.useCases.entity.contracts.EntityContracts;
import boletoGenreator.useCases.entity.user.EntityUser;
import boletoGenreator.useCases.entity.user.EntityUserIntegrity;
import jakarta.transaction.Transactional;
import lombok.Value;

public class UserPageUseCase implements UseCase<UserPageUseCase.InputValues, UserPageUseCase.OutPutValues> {

    private final UserRepository userRepository;

    public UserPageUseCase(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    

    @Transactional
    @Override
    public OutPutValues execute(InputValues input){

        Long idUser = Long.parseLong(input.getUserId());

        EntityUser client = userRepository.findById(idUser)
        .orElseThrow(() -> new RuntimeException("Client  not found"));

        List<EntityUserIntegrity> clientIntegritys = client.getIntegrity();

        List<EntityContracts> contracts = client.getUserContracts();

        return new OutPutValues(client, clientIntegritys, contracts);
    }


    @Value
    public static class InputValues implements UseCase.InputValues{
        String userId;
    }

    @Value
    public static class OutPutValues implements UseCase.OutPutValues{
        EntityUser client;
        List<EntityUserIntegrity> clientIntegritys;
        List<EntityContracts> contracts;
    }


}
