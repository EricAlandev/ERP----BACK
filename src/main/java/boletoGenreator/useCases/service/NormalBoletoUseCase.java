package boletoGenreator.useCases.service;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import boletoGenreator.domain.model.BankBilletData;
import boletoGenreator.useCases.UseCase;
import lombok.Value;

@Service
public class NormalBoletoUseCase implements UseCase<NormalBoletoUseCase.InputValues, NormalBoletoUseCase.OutPutValues> {
    

    @Override
    public OutPutValues  execute(InputValues input){

        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            //transform into bytes
            PdfWriter writer = new PdfWriter(output);

            //manage the pdf at all;
            PdfDocument pdfDoc = new PdfDocument(writer);

            //the area that the devs gonan build the UI of the pdf;
            Document document = new Document(pdfDoc);

            Paragraph header = new Paragraph("Payment comprovant")
            .setTextAlignment(TextAlignment.CENTER);
            
            document.add(header);

            float[] pdfHeader = {33.3f, 33.3f, 33.3f};

            com.itextpdf.layout.element.Table detailedTable = new com.itextpdf.layout.element.Table(pdfHeader);

            detailedTable.setWidth(UnitValue.createPercentValue(100f)).setMarginTop(10f);

            detailedTable.addCell(new Cell().add(
                new Paragraph("Client : " + input.getData().getClientName()))
                .setPadding(5f)
                .setWidth(UnitValue.createPercentValue(33.3f)
            ));

            detailedTable.addCell(new Cell().add(
                new Paragraph("Value : " + input.getData().getValue()))
                .setPadding(5f)
                .setWidth(UnitValue.createPercentValue(33.3f)
            ));
            
            detailedTable.addCell(new Cell().add(
                new Paragraph("Date : " + input.getData().getDate()))
                .setPadding(5f)
                .setWidth(UnitValue.createPercentValue(33.3f)
            ));

            document.add(detailedTable);
            document.close();

            byte[] pdfBytes = output.toByteArray();
        
            return new OutPutValues(pdfBytes);
        } catch (Exception e) {
            throw new RuntimeException("Fail to write the bank billet");
        }    
    }

    @Value
    public static class InputValues implements UseCase.InputValues{
        BankBilletData data;
    }

    @Value
    public static class OutPutValues implements UseCase.OutPutValues{
        byte[] pdf;
    }
}
