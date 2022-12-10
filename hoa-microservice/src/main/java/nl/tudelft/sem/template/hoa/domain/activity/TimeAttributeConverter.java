package nl.tudelft.sem.template.hoa.domain.activity;

import lombok.SneakyThrows;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Scanner;

@Converter
public class TimeAttributeConverter implements AttributeConverter<GregorianCalendar, String> {

    @Override
    public String convertToDatabaseColumn(GregorianCalendar time) {
        return time.get(Calendar.YEAR) + "-" + time.get(Calendar.MONTH) + "-" + time.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public GregorianCalendar convertToEntityAttribute(String dbData) {
        try (Scanner scanner = new Scanner(dbData).useDelimiter("-");){
            int year = scanner.nextInt();
            int month = scanner.nextInt();
            int day = scanner.nextInt();

            return new GregorianCalendar(year, month, day);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
