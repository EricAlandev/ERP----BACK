package boletoGenreator.useCases.service.pdfs;

import java.io.ByteArrayOutputStream;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ItextFunctions {
    
    public static ManagerItext PrepareteItext(){
        ByteArrayOutputStream output = new 
        ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(output);

        //manage the pdf at all;
        PdfDocument pdfDoc = new PdfDocument(writer);

        //the area that the devs gonan build the UI of the pdf;
        Document document = new Document(pdfDoc);

        ManagerItext manager = new ManagerItext();
        manager.setOutput(output);
        manager.setDocument(document);

        return manager;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ManagerItext{
        ByteArrayOutputStream output;
        Document document;
    }
}
