package boletoGenreator.infrastructure.controller.mapper.InAndOut;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import boletoGenreator.domain.model.InAndOut.RegisterData;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping("/register") 
public interface RegisterResource {
        
    @PostMapping
    public CompletableFuture<String> register(@RequestBody RegisterData data);
}
