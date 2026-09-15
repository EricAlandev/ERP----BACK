package boletoGenreator.infrastructure.controller.mapper.cep;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import boletoGenreator.domain.model.cep.CepRespondeDTO;
import jakarta.websocket.server.PathParam;

import org.springframework.web.bind.annotation.GetMapping;

@RestController 
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping(CepEndpoints.CEP)
public interface CepResource {
    

    @GetMapping(CepEndpoints.NUMBER_CEP)
    public CompletableFuture<CepRespondeDTO> findCEP(@PathParam("cep") String cepNumber);
}
