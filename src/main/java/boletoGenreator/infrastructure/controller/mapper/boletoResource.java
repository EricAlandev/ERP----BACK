package boletoGenreator.infrastructure.controller.mapper;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import boletoGenreator.domain.model.BankBilletData;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping("/boleto") 
public interface boletoResource {
    
    @PostMapping
    public CompletableFuture<ResponseEntity<byte[]>> generatePDF(@RequestBody BankBilletData data);
}
