package boletoGenreator.infrastructure.controller.dto.generic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ParseTime {
    public static String parseTime(LocalDateTime now){
        DateTimeFormatter formatter = ParseTime.formater("dd-MM-yyyy");
        String formatedDate = now.format(formatter);

        return ParseTime.formatAndReplace(formatedDate);
    }

    public static LocalDateTime parseTolocal(String time){
        return LocalDate.parse(time).atStartOfDay();
    }

    public static DateTimeFormatter formater(String format){
        return  DateTimeFormatter.ofPattern(format);
    }

    public static String formatAndReplace(String date){
        return date.replace("-", " / ");
    }
}
