package nl.tudelft.sem.template.hoa.domain.activity;

import lombok.SneakyThrows;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Converter
public class TimeAttributeConverter implements AttributeConverter<Date, String> {

    @Override
    public String convertToDatabaseColumn(Date time) {
        return time.toString();
    }

    @SneakyThrows
    @Override
    public Date convertToEntityAttribute(String dbData) {
        return new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", new Locale("us")).parse(dbData);
    }
}
