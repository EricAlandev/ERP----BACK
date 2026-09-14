package boletoGenreator.infrastructure.controller.mapper.combo;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import boletoGenreator.domain.model.combos.ComboStateDTO;
import boletoGenreator.useCases.ServiceExecute;
import boletoGenreator.useCases.service.combos.ComboStateUseCase;

@RestController 
@CrossOrigin(origins = "http://localhost:5173/")
public class CombosController implements CombosResource {

    private final ComboStateUseCase comboStateUseCase;

    public CombosController(ComboStateUseCase comboStateUseCase){
        this.comboStateUseCase = comboStateUseCase;
    }
 
    @Override 
    public CompletableFuture<List<ComboStateDTO>> getStates() {
        return ServiceExecute.execute(
            comboStateUseCase, 
            new ComboStateUseCase.InputValues(), 
            (output) -> output.getStates()
        );
    }
    
}
