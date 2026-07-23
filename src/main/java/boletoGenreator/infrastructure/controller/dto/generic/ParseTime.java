package boletoGenreator.infrastructure.controller.dto.generic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ParseTime {
    public static String parseTime(LocalDateTime now){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        String formatedDate = now.format(formatter);

        return formatedDate;
    }
}
