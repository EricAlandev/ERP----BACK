package boletoGenreator.infrastructure.controller.mapper.contracts;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.time.LocalDateTime;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import boletoGenreator.domain.model.contracts.DealContract;
import boletoGenreator.infrastructure.controller.dto.generic.ParseTime;
import boletoGenreator.useCases.UseCase;
import boletoGenreator.useCases.service.pdfs.ItextFunctions;
import boletoGenreator.useCases.service.pdfs.ItextFunctions.ManagerItext;
import jakarta.transaction.Transactional;
import lombok.Value;

public class ContractPdfUseCase implements UseCase<ContractPdfUseCase.InputValues, ContractPdfUseCase.OutPutValues> {

    @Transactional
    @Override
    public OutPutValues execute(InputValues input){

        ManagerItext manageItext = ItextFunctions.PrepareteItext();

        Paragraph Tittle = new Paragraph("Contract Paper").setTextAlignment(TextAlignment.CENTER);
        manageItext.getDocument().add(Tittle);

        DealContract pdfData = input.getPdfData();

        LocalDateTime today = LocalDateTime.now();

        //header
        createHeader(pdfData.getNameClient(), pdfData.getBankBilletType(), today, manageItext.getDocument());
        createObservations(manageItext.getDocument());

        BigDecimal priceLoan = pdfData.getPriceInstallments().multiply(new BigDecimal(pdfData.getQuantityInstallments()));

        Paragraph textInstallments  = new Paragraph("The client gonna pay " + pdfData.getQuantityInstallments() + " installments, and the loan gonna cost on total R$ " + priceLoan.setScale(2))
        .setPadding(5)
        .setTextAlignment(TextAlignment.RIGHT);

        manageItext.getDocument().add(textInstallments);

        String dateLastIntallment = ""; 

        //Generate the lines of the installments
        for(int i = 0; i < pdfData.getQuantityInstallments(); i++){
            dateLastIntallment = generateInstallmentLine(pdfData.getPriceInstallments(), today, manageItext.getDocument(), i);
        }

        //Text with the agreements
        Paragraph textAboutDetails = new Paragraph("This contract gonna have the duration of " +  pdfData.getQuantityInstallments() + " months. Being you last installment on the day " + dateLastIntallment +".If you agree with the deal, sign with you signature on the empty field called 'CLIENT'")
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
        DealContract pdfData;
    }

    @Value
    public static class OutPutValues implements UseCase.OutPutValues{
        byte[] pdf;
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


    public String generateInstallmentLine(BigDecimal priceInstallment, LocalDateTime today, Document document, int i){

        LocalDateTime dateInstallmente = today.plusDays(30 * i);
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
