package nl.tudelft.sem.template.hoa.models;

import lombok.Data;

@Data
public class JoinModel extends FullAddressModel {
    private String hoaName;
    private String userDisplayName;
}

