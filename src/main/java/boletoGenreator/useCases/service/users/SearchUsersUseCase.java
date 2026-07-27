package boletoGenreator.useCases.service.users;

import java.util.List;

import boletoGenreator.domain.model.users.UserSearch;
import boletoGenreator.useCases.UseCase;
import boletoGenreator.useCases.impl.user.UserCustomRepository;
import jakarta.transaction.Transactional;
import lombok.Value;

public class SearchUsersUseCase implements UseCase<SearchUsersUseCase.InputValues, SearchUsersUseCase.OutPutValues> {
    
    private final UserCustomRepository userCustomRepository;

    public SearchUsersUseCase(UserCustomRepository userCustomRepository){
        this.userCustomRepository = userCustomRepository;
    }

    @Transactional
    @Override
    public OutPutValues execute(InputValues input){

        UserSearch searchData = input.getSearchData();
        List<UserSearch> users = userCustomRepository.findUsers(searchData);

        return new OutPutValues(users);
    }

    @Value
    public static class InputValues implements UseCase.InputValues{
        UserSearch searchData;
    }

    @Value
    public static class OutPutValues implements UseCase.OutPutValues{
        List<UserSearch> users;
    }
}
