package boletoGenreator.useCases.service.pdfs;

import java.io.ByteArrayOutputStream;
import java.net.URL;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ItextFunctions {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ManagerItext{
        ByteArrayOutputStream output;
        Document document;
    }
    
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

    //find Path + generate signature Tables
    public static Image createImage(String path){
        URL url = ItextFunctions.findPath(path);

        ImageData imageData = ImageDataFactory.create(url);
        Image image = new Image(imageData);

        return image;
    }

    private static URL findPath(String path){
        URL url = ManagerItext.class.getClassLoader().getResource(path);
        return url; 
    }
}
