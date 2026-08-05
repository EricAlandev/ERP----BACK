package boletoGenreator.infrastructure.controller.mapper.users;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import boletoGenreator.domain.model.users.UserPageResponse;
import boletoGenreator.domain.model.users.UserSearch;
import boletoGenreator.useCases.ServiceExecute;
import boletoGenreator.useCases.service.users.SearchUsersUseCase;
import boletoGenreator.useCases.service.users.UserPageUseCase;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
public class UserController implements UserResource {

    private final SearchUsersUseCase searchUsersUseCase;
    private final UserPageUseCase userPageUseCase;

    public UserController(SearchUsersUseCase searchUsersUseCase, UserPageUseCase userPageUseCase){
        this.searchUsersUseCase = searchUsersUseCase;
        this.userPageUseCase = userPageUseCase;
    }
    
    @Override
    public CompletableFuture<List<UserSearch>> findUsers(@RequestBody UserSearch searchData){

        return ServiceExecute.execute(
            searchUsersUseCase, 
            new SearchUsersUseCase.InputValues(searchData), 
            (output) -> output.getUsers());
    }

    @Override
    public CompletableFuture<UserPageResponse> pullUserData(String userId){

        return ServiceExecute.execute(
            userPageUseCase, 
            new UserPageUseCase.InputValues(userId), 
            (output) -> UserPageResponse.from(output.getClient(), output.getClientIntegritys(), output.getContracts()));
    }
}
