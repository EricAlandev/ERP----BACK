package boletoGenreator.useCases.impl.user;

import java.util.List;

import boletoGenreator.domain.model.users.UserSearch;

public interface UserCustomRepository {
    
    List<UserSearch> findUsers(UserSearch userSearch); 
}
