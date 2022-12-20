package nl.tudelft.sem.template.commons.models.hoa;

import lombok.Data;

@Data
public class JoinModel extends FullAddressModel {
    private String hoaName;
    private String userDisplayName;
}

