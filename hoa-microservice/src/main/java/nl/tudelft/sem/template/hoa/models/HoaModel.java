package nl.tudelft.sem.template.hoa.models;

import java.util.Set;

import lombok.Data;
import nl.tudelft.sem.template.hoa.entitites.BoardMember;
import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.hoa.entitites.UserHoa;

@Data
public class HoaModel extends CreateHoaModel {

    private Set<UserHoa> members;
    private Set<BoardMember> boardMembers;

    public HoaModel(String name, String country, String city, Set<UserHoa> members, Set<BoardMember> boardMembers) {
        super(name, country, city);
        this.members = members;
        this.boardMembers = boardMembers;
    }

    public HoaModel(Hoa hoa) {
        super(hoa.getName(), hoa.getCountry(), hoa.getCity());
        this.members = hoa.getMembers();
        this.boardMembers = hoa.getBoardMembers();
    }
}
