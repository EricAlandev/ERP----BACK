package boletoGenreator.infrastructure.controller.mapper;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import boletoGenreator.domain.model.BankBilletData;
import boletoGenreator.infrastructure.controller.dto.pdfs.PdfResponse;
import boletoGenreator.useCases.ServiceExecute;
import boletoGenreator.useCases.service.NormalBoletoUseCase;

@RestController 
@CrossOrigin
public class BoletoController implements boletoResource {

    private final NormalBoletoUseCase normalBoletoUseCase;

    public BoletoController(NormalBoletoUseCase normalBoletoUseCase){
        this.normalBoletoUseCase = normalBoletoUseCase;
    }
    
    
    @Override
    public CompletableFuture<ResponseEntity<byte[]>> generatePDF(BankBilletData data){

        return ServiceExecute.execute(
            normalBoletoUseCase, 
            new NormalBoletoUseCase.InputValues(data), 
            (out) -> PdfResponse.from(out.getPdf(), null)
        );
    }
}
