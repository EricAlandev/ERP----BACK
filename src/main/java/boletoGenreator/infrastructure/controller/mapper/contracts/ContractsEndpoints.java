package boletoGenreator.infrastructure.controller.mapper.contracts;

public class ContractsEndpoints {
    
    public static final String CONTRACT = "/contract";
    public static final String DEAL = "/deal";
    public static final String CONTRACTPDF = "/{id}/pdf";
    public static final String SIMULATION = "/simulation/{id}";
    public static final String INSTALLMENTS = "/installments/{id}";

    //for calls by the backend
    public static final String ContractPDFGeneration = "/contract/contractPDF";
}
