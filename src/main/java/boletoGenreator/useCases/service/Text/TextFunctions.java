package boletoGenreator.useCases.service.Text;

public class TextFunctions {
    
    public static String formatCic(String cic){
        return cic.replace(".", "").replace("/", "").replace("-", "");
    }
}
