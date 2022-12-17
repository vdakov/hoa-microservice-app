package nl.tudelft.sem.template.hoa.entitites;

public class ElectionVote extends Vote {
    private String applicantName;

    public ElectionVote(String applicantName, int hoaId) {
        super(hoaId);
        this.applicantName = applicantName;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }
}
