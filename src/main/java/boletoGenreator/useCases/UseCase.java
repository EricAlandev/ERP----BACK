package boletoGenreator.useCases;

public interface UseCase<I extends UseCase.InputValues, O extends UseCase.OutPutValues> {

    // Every service must implement this method
    O execute(I input);

    // Marker interfaces to group input and output data
    interface InputValues {}
    interface OutPutValues {}
}
