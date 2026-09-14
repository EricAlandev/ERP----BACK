package boletoGenreator.useCases.service.combos;

import java.util.List;

import boletoGenreator.domain.model.combos.ComboStateDTO;
import boletoGenreator.infrastructure.repository.combos.ComboStateRepository;
import boletoGenreator.useCases.UseCase;
import lombok.Value;

public class ComboStateUseCase implements UseCase<ComboStateUseCase.InputValues, ComboStateUseCase.OutPutValues> {

    private final ComboStateRepository comboStateRepository;

    public ComboStateUseCase(ComboStateRepository comboStateRepository){
        this.comboStateRepository = comboStateRepository;
    }
    
    @Override 
    public OutPutValues execute(InputValues input){

        List<ComboStateDTO> states = comboStateRepository.findStates();

        return new OutPutValues(states);
    }

    @Value 
    public static class InputValues implements UseCase.InputValues{

    }

    @Value 
    public static class OutPutValues implements UseCase.OutPutValues{
        List<ComboStateDTO> states;
    }
}
