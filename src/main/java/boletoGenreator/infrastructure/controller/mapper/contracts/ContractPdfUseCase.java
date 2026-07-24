package boletoGenreator.infrastructure.controller.mapper.contracts;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.time.LocalDateTime;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
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

        BigDecimal priceLoan = BigDecimal.ZERO;

        //Generate the lines of the installments
        for(int i = 0; i < pdfData.getQuantityInstallments(); i++){
            generateInstallmentLine(pdfData.getPriceInstallments(), today, manageItext.getDocument(), priceLoan, i);
        }

        Paragraph textInstallments  = new Paragraph("The client gonna pay " + pdfData.getQuantityInstallments() + " installments, and the loan gonna cost on total " + priceLoan).setTextAlignment(TextAlignment.RIGHT);

        manageItext.getDocument().add(textInstallments);

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

            Image managerSignature = ItextFunctions.createImage("/contract/BankManagerSignature.png");

            Image clientSignature = ItextFunctions.createImage("/contract/ClientField.png");

            signatureTables.addCell(managerSignature);
            signatureTables.addCell(clientSignature);

            return signatureTables;
    }


    public void generateInstallmentLine(BigDecimal priceInstallment, LocalDateTime today, Document document, BigDecimal priceLoan, int i){

        LocalDateTime dateInstallmente = today.plusDays(30 * i);
        String formatedDate = ParseTime.parseTime(dateInstallmente);

        UnitValue[] installmentDimensions = {
            UnitValue.createPercentValue(50),
            UnitValue.createPercentValue(50)
        };
        Table intallmentTable = new Table(installmentDimensions);

        intallmentTable.setWidth(UnitValue.createPercentValue(100));

        intallmentTable.addCell(new Cell().add(new Paragraph("Value Installmente : " + priceInstallment)));

        intallmentTable.addCell(new Cell().add(new Paragraph("Date : " + formatedDate)));

        document.add(intallmentTable);
        priceLoan = priceLoan.add(priceInstallment);
    }

    public void createHeader(String nameClient, String BankBilletType, LocalDateTime today, Document document){
         UnitValue[] pdfHeader = {
            UnitValue.createPercentValue(50),
            UnitValue.createPercentValue(50)
        };

        Table headerContract = new Table(pdfHeader);

        headerContract.setWidth(UnitValue.createPercentValue(100));

        headerContract.addCell(new Cell().add(new Paragraph("Client Name : " + nameClient)));

        headerContract.addCell(new Cell().add(new Paragraph("Type Contract : " + BankBilletType)));

        headerContract.addCell(new Cell().add(new Paragraph("Date Contract : " +  today)));

        document.add(headerContract);
    }
}
