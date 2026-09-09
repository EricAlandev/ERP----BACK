package boletoGenreator.useCases.service.contracts;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
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

        Image bankLogo = ItextFunctions.createImage("/banks/bradesco.png");

        Cell bankLogoCell = new Cell().add(bankLogo);

        headerBank.addCell(bankLogoCell);

        managerItext.getDocument().add(bankLogoCell);

        return new OutPutValues();
    }


    @Value 
    public static class InputValues implements  UseCase.InputValues{
        private String idUser;
    }

    @Value 
    public static class OutPutValues implements  UseCase.OutPutValues{
        
    }
}
