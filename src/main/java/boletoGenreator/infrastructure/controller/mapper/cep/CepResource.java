package boletoGenreator.infrastructure.controller.mapper.cep;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import boletoGenreator.infrastructure.controller.dto.cep.CepResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController 
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping(CepEndpoints.CEP)
public interface CepResource {
    

    @GetMapping(CepEndpoints.NUMBER_CEP)
    public CompletableFuture<CepResponse> findCEP(@PathVariable("cep") String cepNumber);
}
