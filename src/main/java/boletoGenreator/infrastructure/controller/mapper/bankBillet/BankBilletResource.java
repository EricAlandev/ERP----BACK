package boletoGenreator.infrastructure.controller.mapper.bankBillet;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import boletoGenreator.infrastructure.controller.dto.boleto.BankBilletPickIntallmentsResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping(BankBilletEndpoints.BankBillet)
public interface BankBilletResource {
    
    @GetMapping(BankBilletEndpoints.Installments)
    public CompletableFuture<BankBilletPickIntallmentsResponse> getInstallments(@PathVariable("idContract") String idContract);
}
