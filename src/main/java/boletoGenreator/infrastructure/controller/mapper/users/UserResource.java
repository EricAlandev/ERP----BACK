package boletoGenreator.infrastructure.controller.mapper.users;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import boletoGenreator.domain.model.users.UserSearch;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping(UserEndpoints.Search)
public interface UserResource {
    
    @PostMapping()
    public CompletableFuture<List<UserSearch>> findUsers(@RequestBody UserSearch searchData);
}
