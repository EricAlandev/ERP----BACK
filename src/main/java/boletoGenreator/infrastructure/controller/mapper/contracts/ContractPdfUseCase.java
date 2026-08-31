package boletoGenreator.infrastructure.controller.mapper.contracts;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.List;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import boletoGenreator.domain.model.contracts.ContractData;
import boletoGenreator.infrastructure.controller.dto.generic.ParseTime;
import boletoGenreator.useCases.UseCase;
import boletoGenreator.useCases.impl.user.UserCustomRepository;
import boletoGenreator.useCases.service.pdfs.ItextFunctions;
import boletoGenreator.useCases.service.pdfs.ItextFunctions.ManagerItext;
import jakarta.transaction.Transactional;
import lombok.Value;

public class ContractPdfUseCase implements UseCase<ContractPdfUseCase.InputValues, ContractPdfUseCase.OutPutValues> {

    private final UserCustomRepository userCustomRepository;

    public ContractPdfUseCase(UserCustomRepository userCustomRepository){
        this.userCustomRepository = userCustomRepository;
    }

    @Transactional
    @Override
    public OutPutValues execute(InputValues input){

        //pull data;
        ContractData contract = findContractData(input.getIdContract());

        ManagerItext manageItext = ItextFunctions.PrepareteItext();

        Paragraph Tittle = new Paragraph("Contract Paper").setTextAlignment(TextAlignment.CENTER);
        manageItext.getDocument().add(Tittle);

        LocalDateTime today = LocalDateTime.now();

        //header
        createHeader(contract.getNameClient(), contract.getTypeContract(), today, manageItext.getDocument());
        createObservations(manageItext.getDocument());

        //verify the installment prices and quantity of Installments 
        BigDecimal priceLoan = contract.getBankBillets().get(0).getPrice();
        int quantityInstallments = contract.getBankBillets().size();

        Paragraph textInstallments  = new Paragraph("The client gonna pay " + quantityInstallments + " installments, and the loan gonna cost on total R$ " + priceLoan.setScale(2))
        .setPadding(5)
        .setTextAlignment(TextAlignment.RIGHT);

        manageItext.getDocument().add(textInstallments);

        String dateLastIntallment = ""; 

        //Generate the lines of the installments
        for(int i = 0; i < quantityInstallments; i++){
            ContractData.BankBillet bankBillet = contract.getBankBillets().get(i);
            
            dateLastIntallment = generateInstallmentLine(bankBillet.getPrice(), bankBillet.getExpirationdate(), manageItext.getDocument());
        }

        //Text with the agreements
        Paragraph textAboutDetails = new Paragraph("This contract gonna have the duration of " +  quantityInstallments + " months. Being you last installment on the day " + dateLastIntallment +".If you agree with the deal, sign with you signature on the empty field called 'CLIENT'")
        .setPadding(5);

        manageItext.getDocument().add(textAboutDetails);

        //Signatures
        try {
            Table signaturesTable = generateSignatures();
            manageItext.getDocument().add(signaturesTable);

        } catch (MalformedURLException e) {
            throw new RuntimeException("");
        }
        
        manageItext.getDocument().close();

        byte[] pdfBytes = manageItext.getOutput().toByteArray();

        return new OutPutValues(pdfBytes);
    }

    @Value
    public static class InputValues implements UseCase.InputValues{
        String idContract;
    }

    @Value
    public static class OutPutValues implements UseCase.OutPutValues{
        byte[] pdf;
    }

    public ContractData findContractData(String idContract){
        List<ContractData> ContractData = userCustomRepository.findContractData(Long.parseLong(idContract));

       ContractData contract = ContractData.get(0);
        
       if(contract == null){
         throw new RuntimeException("Fail to pick the data of the pdf");       
        }

        return contract;
    }

    public Table generateSignatures() throws MalformedURLException{
            UnitValue[] signaturesProportion = {
                UnitValue.createPercentValue(50),
                UnitValue.createPercentValue(50)
            };

            Table signatureTables = new Table(signaturesProportion);

            signatureTables.setWidth(UnitValue.createPercentValue(100));

            Image clientSignature = ItextFunctions.createImage("/contract/ClientField.png");

            Image managerSignature = ItextFunctions.createImage("/contract/BankManagerSignature.png");

            Cell clientCell = new Cell().add(clientSignature)
            .setBorder(null);
            Cell managerCell = new Cell().add(managerSignature)
            .setBorder(null);
            
            signatureTables.addCell(clientCell);
            signatureTables.addCell(managerCell);
            
            return signatureTables;
    }


    public String generateInstallmentLine(BigDecimal priceInstallment, LocalDateTime date , Document document){
        LocalDateTime dateInstallmente = date;
        String formatedDate = ParseTime.parseTime(dateInstallmente);

        UnitValue[] installmentDimensions = {
            UnitValue.createPercentValue(50),
            UnitValue.createPercentValue(50)
        };
        Table intallmentTable = new Table(installmentDimensions);

        intallmentTable.setWidth(UnitValue.createPercentValue(100));

        intallmentTable.addCell(new Cell().add(new Paragraph("Value Installment : R$ " + priceInstallment.setScale(2))));

        intallmentTable.addCell(new Cell().add(new Paragraph("Date : " + formatedDate)));

        document.add(intallmentTable);

        return formatedDate;
    }

    public void createHeader(String nameClient, String BankBilletType, LocalDateTime today, Document document){

        //Detail client
        Paragraph clientParagragh = new Paragraph();
        Text clientDataTittle = new Text("Client Details :")
        .setFont(ItextFunctions.BoldFont());

        clientParagragh.add(clientDataTittle);
        document.add(clientParagragh);

        Table headerContract = new Table(1);

        headerContract.setWidth(UnitValue.createPercentValue(100));

        headerContract.addCell(new Cell().add(new Paragraph("Client Name : " + nameClient)).setBorder(null));

        headerContract.addCell(new Cell().add(new Paragraph("Type Contract : " + BankBilletType)).setBorder(null));

        headerContract.addCell(new Cell().add(new Paragraph("Date Contract : " +  ParseTime.parseTime(today))).setBorder(null));

        document.add(headerContract);
    }

    public void createObservations(Document document){
        Paragraph observations = new Paragraph();
        Text ObservationTittle = new Text("Observations :\n").setFont(ItextFunctions.BoldFont());
        Text textObservation = new Text("This is a contract paper to make a Loan. So, stay aware that youu gonna be in debit with us.");

        observations.add(ObservationTittle);
        observations.add(textObservation);

        document.add(observations);
    }
}
