package nl.tudelft.sem.template.hoa.models;

import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FullUserResponseModel extends UserResponseModel {
    private Set<UserLessUserHoaModel> associations;

    public FullUserResponseModel(Set<UserLessUserHoaModel> associations, String displayName) {
        super(displayName);
        this.associations = associations;
    }
}
