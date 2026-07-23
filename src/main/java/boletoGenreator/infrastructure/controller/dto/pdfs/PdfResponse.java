package boletoGenreator.infrastructure.controller.dto.pdfs;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class PdfResponse {

    public static ResponseEntity<byte[]> from (byte[] pdf, String typePDF){
        String pdfType = (typePDF == "attachment") ? typePDF : "inline";

        return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_PDF)
        .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "" + pdfType + "; filename=\"boleto.pdf\"")
        .body(pdf);
    }
    
}
