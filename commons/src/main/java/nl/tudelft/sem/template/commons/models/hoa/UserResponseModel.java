package nl.tudelft.sem.template.commons.models.hoa;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class UserResponseModel {
    private String displayName;

    public UserResponseModel(String displayName) {
        this.displayName = displayName;
    }
}
