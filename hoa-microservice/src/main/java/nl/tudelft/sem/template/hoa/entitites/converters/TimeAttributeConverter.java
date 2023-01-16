package nl.tudelft.sem.template.hoa.entitites.converters;

import lombok.SneakyThrows;
import nl.tudelft.sem.template.commons.models.DateModel;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Scanner;

@Converter
public class TimeAttributeConverter implements AttributeConverter<DateModel, String> {

    @Override
    public String convertToDatabaseColumn(DateModel time) {
        return String.format("%d-%d-%d", time.getYear(), time.getMonth(), time.getDay());
    }

    @Override
    public DateModel convertToEntityAttribute(String dbData) {
        try (Scanner scanner = new Scanner(dbData).useDelimiter("-");){
            int year = scanner.nextInt();
            int month = scanner.nextInt();
            int day = scanner.nextInt();

            return new DateModel(year, month, day);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
