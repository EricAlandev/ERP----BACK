package boletoGenreator.infrastructure.controller.mapper.contracts;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;

import boletoGenreator.domain.model.contracts.DealContract;
import boletoGenreator.infrastructure.controller.dto.generic.ParseTime;
import boletoGenreator.useCases.UseCase;
import boletoGenreator.useCases.service.pdfs.ItextFunctions;
import boletoGenreator.useCases.service.pdfs.ItextFunctions.ManagerItext;
import lombok.Value;

public class ContractPdfUseCase implements UseCase<ContractPdfUseCase.InputValues, ContractPdfUseCase.OutPutValues> {
    

    @Override
    public OutPutValues execute(InputValues input){

        ManagerItext manageItext = ItextFunctions.PrepareteItext();

        Paragraph Tittle = new Paragraph("Contract Paper").setTextAlignment(TextAlignment.CENTER);

        manageItext.getDocument().add(Tittle);

        DealContract pdfData = input.getPdfData();

        //header
        float [] pdfHeader = {33.f, 33.3f, 33.3f};

        Table headerContract = new Table(pdfHeader);

        headerContract.addCell(new Cell().add(new Paragraph("Client Name : " + pdfData.getNameClient())));

        headerContract.addCell(new Cell().add(new Paragraph("Type Contract : " + pdfData.getBankBilletType())));

        headerContract.addCell(new Cell().add(new Paragraph("Date Contract : " + LocalDateTime.now())));

        manageItext.getDocument().add(headerContract);

        BigDecimal priceLoan = BigDecimal.ZERO;

        //Generate the lines of the installments
        for(int i = 0; i < pdfData.getQuantityInstallments(); i++){
            
            BigDecimal priceInstallment = pdfData.getPriceInstallments();

            LocalDateTime today = LocalDateTime.now();
            LocalDateTime dateInstallmente = today.plusDays(30 * i);
            String formatedDate = ParseTime.parseTime(dateInstallmente);

            float [] installmentDimensions = {50f, 50f};
            Table intallmentTable = new Table(installmentDimensions);

            intallmentTable.addCell(new Cell().add(new Paragraph("Value Installmente : " + priceInstallment)));

            intallmentTable.addCell(new Cell().add(new Paragraph("Date : " + formatedDate)));

            manageItext.getDocument().add(intallmentTable);
            priceLoan.add(priceInstallment);
        }
        Paragraph textInstallments  = new Paragraph("The client gonna pay " + pdfData.getQuantityInstallments() + " installments, and the loan gonna cost on total " + priceLoan).setTextAlignment(TextAlignment.RIGHT);

        manageItext.getDocument().add(textInstallments);

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
}
