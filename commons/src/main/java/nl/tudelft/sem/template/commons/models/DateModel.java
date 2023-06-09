package nl.tudelft.sem.template.commons.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DateModel {
    private int year;
    private int month;
    private int day;

    public DateModel(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
}
