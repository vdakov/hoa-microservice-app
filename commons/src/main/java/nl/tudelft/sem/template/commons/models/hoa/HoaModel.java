package nl.tudelft.sem.template.commons.models.hoa;

import java.util.Set;

import lombok.Data;

@Data
public class HoaModel extends HoaRequestModel {

    private Set<FullUserHoaModel> members;
    private Set<BoardMemberModel> boardMembers;

    public HoaModel(
        String name, String country, String city, Set<FullUserHoaModel> members, Set<BoardMemberModel> boardMembers
    ) {
        super(name, country, city);
        this.members = members;
        this.boardMembers = boardMembers;
    }
}
