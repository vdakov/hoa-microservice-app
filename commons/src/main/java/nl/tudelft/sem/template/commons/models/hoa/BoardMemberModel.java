package nl.tudelft.sem.template.commons.models.hoa;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardMemberModel extends FullUserResponseModel {
    private int yearsOnBoard;

}
