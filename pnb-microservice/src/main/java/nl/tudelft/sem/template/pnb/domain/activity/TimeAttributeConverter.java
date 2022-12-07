package nl.tudelft.sem.template.pnb.domain.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import lombok.SneakyThrows;

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
