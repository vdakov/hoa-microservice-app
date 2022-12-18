package nl.tudelft.sem.template.hoa.models;

import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FullHoaResponseModel extends HoaResponseModel {
    private Set<HoaLessUserHoaModel> members;

    public FullHoaResponseModel(Set<HoaLessUserHoaModel> members, String name, String country, String city) {
        super(name, country, city);
        this.members = members;
    }
}
