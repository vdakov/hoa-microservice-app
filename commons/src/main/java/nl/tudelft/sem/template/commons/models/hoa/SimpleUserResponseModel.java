package nl.tudelft.sem.template.commons.models.hoa;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SimpleUserResponseModel extends UserResponseModel {
    
    public SimpleUserResponseModel(String displayName) {
        super(displayName);
    }
}
