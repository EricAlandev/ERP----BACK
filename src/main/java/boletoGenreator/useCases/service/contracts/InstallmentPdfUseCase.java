package boletoGenreator.useCases.service.contracts;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import boletoGenreator.infrastructure.repository.UserRepository;
import boletoGenreator.useCases.UseCase;
import boletoGenreator.useCases.entity.user.EntityUser;
import boletoGenreator.useCases.service.pdfs.ItextFunctions;
import boletoGenreator.useCases.service.pdfs.ItextFunctions.ManagerItext;
import lombok.Value;

public class InstallmentPdfUseCase implements UseCase<InstallmentPdfUseCase.InputValues, InstallmentPdfUseCase.OutPutValues> {

    private final UserRepository userRepository;

    public InstallmentPdfUseCase(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    
    @Override 
    public OutPutValues execute(InputValues input){

        EntityUser user = userRepository.findById(Long.parseLong(input.getIdUser()))
        .orElseThrow(() -> new RuntimeException("")); 

        ManagerItext managerItext = ItextFunctions.PrepareteItext();

        UnitValue[] headerBankD = {
            UnitValue.createPercentValue(20),
            UnitValue.createPercentValue(25),
            UnitValue.createPercentValue(55)
        };

        Table headerBank = new Table(headerBankD);

        String imgUrl = logoBankDefine("");
        Image bankLogo = ItextFunctions.createImage(imgUrl);
        Cell bankLogoCell = new Cell().add(bankLogo);

        Cell bankNumber = new Cell().add(bankCodeManager(
            Long.valueOf(45), (Long.valueOf(45) != null)  ? Long.valueOf(45) 
            : null
        ));

        Cell bankBilletDigits = new Cell().add(
            bank47Digits(
            (Long.valueOf(47) != null) ? 
            Long.valueOf(47) 
            : null
        ));

        headerBank.addCell(bankLogoCell);
        headerBank.addCell(bankNumber);
        headerBank.addCell(bankBilletDigits);

        managerItext.getDocument().add(bankLogoCell);

        return new OutPutValues(managerItext.getOutput().toByteArray());
    }


    @Value 
    public static class InputValues implements  UseCase.InputValues{
        private String idUser;
    }

    @Value 
    public static class OutPutValues implements  UseCase.OutPutValues{
        private byte[] returnV;
    }

    public String logoBankDefine(String bank){
        String bankUrl = "/banks/";
        
        switch (bank) {
            case "BD":
                bankUrl = bankUrl + "bradesco.png";
                break;

            case "BB":
                bankUrl = bankUrl + "bb.png";
                break;
        
            default:
                bankUrl = bankUrl + "bb.png";
                break;
        }

        return bankUrl;
    }

    public Paragraph bankCodeManager(Long bankcode, Long checkDigit){
        String formatedBankCode = "" + bankcode;

        if(!(checkDigit == null) && checkDigit > 0){
            formatedBankCode = formatedBankCode + "-" + checkDigit;
        }

        return new Paragraph(formatedBankCode);
    }
    public Paragraph bank47Digits(Long digits){
        if(digits == null){
            return null;
        }

        return new Paragraph("" + digits);
    }
}
