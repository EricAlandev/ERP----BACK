package boletoGenreator.useCases;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public interface ServiceExecute {

    static <RX, I extends UseCase.InputValues, O extends UseCase.OutPutValues> CompletableFuture<RX> execute(
        UseCase<I, O> useCase,
        I input,
        Function<O, RX> outputManager
    ){
        O output = useCase.execute(input);

        // Mapping execution
        return CompletableFuture.completedFuture(outputManager.apply(output));
    };
    
} 

