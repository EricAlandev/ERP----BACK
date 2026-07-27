package boletoGenreator.infrastructure.controller.mapper.users;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import boletoGenreator.domain.model.users.UserSearch;
import boletoGenreator.useCases.ServiceExecute;
import boletoGenreator.useCases.service.users.SearchUsersUseCase;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
public class UserController implements UserResource {

    private final SearchUsersUseCase searchUsersUseCase;

    public UserController(SearchUsersUseCase searchUsersUseCase){
        this.searchUsersUseCase = searchUsersUseCase;
    }
    
    @Override
    public CompletableFuture<List<UserSearch>> findUsers(UserSearch searchData){

        return ServiceExecute.execute(
            searchUsersUseCase, 
            new SearchUsersUseCase.InputValues(searchData), 
            (output) -> output.getUsers());
    }
}
