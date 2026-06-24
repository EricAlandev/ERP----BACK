package boletoGenreator.infrastructure.controller.mapper;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import boletoGenreator.domain.model.BankBilletData;
import boletoGenreator.infrastructure.controller.dto.boleto.NormalBoletoResponse;
import boletoGenreator.useCases.ServiceExecute;
import boletoGenreator.useCases.service.NormalBoletoUseCase;

@RestController 
@CrossOrigin
public class BoletoController implements boletoResource {

    @Autowired
    private NormalBoletoUseCase normalBoletoUseCase;
    
    @Override
    public CompletableFuture<ResponseEntity<byte[]>> generatePDF(BankBilletData data){

        return ServiceExecute.execute(
            normalBoletoUseCase, 
            new NormalBoletoUseCase.InputValues(data), 
            (out) -> NormalBoletoResponse.from(out.getPdf())
        );
    }
}
