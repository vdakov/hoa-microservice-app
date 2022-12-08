package nl.tudelft.sem.template.hoa.entitites;

public class BoardMember extends User {
    private Hoa board;
    private int yearsOnBoard;

    public BoardMember(String displayName, Hoa hoa) {
        super(displayName);
    }

    public void changeVoteRequirement() {

    }
}
