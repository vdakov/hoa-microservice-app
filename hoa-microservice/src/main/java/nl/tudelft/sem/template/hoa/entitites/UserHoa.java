package nl.tudelft.sem.template.hoa.entitites;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.hoa.models.FullAddressModel;
import nl.tudelft.sem.template.hoa.models.FullUserHoaModel;
import nl.tudelft.sem.template.hoa.models.HoaLessUserHoaModel;
import nl.tudelft.sem.template.hoa.models.SimpleHoaResponseModel;
import nl.tudelft.sem.template.hoa.models.SimpleUserResponseModel;
import nl.tudelft.sem.template.hoa.models.UserLessUserHoaModel;

@Entity
@Data
@Table
@NoArgsConstructor()
public class UserHoa extends HasFullAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;
    
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Hoa hoa;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private User user;

    /**
        Constructor for a UserHoa object, which represents a connection between a user and an HOA (Homeowners Association).
        @param user the user associated with this UserHoa connection
        @param hoa the HOA associated with this UserHoa connection
        @param address the address for this UserHoa connection
    */
    public UserHoa(User user, Hoa hoa, FullAddressModel address) {
        super(
            address.getCountry(), address.getCity(), 
            address.getStreet(), address.getHouseNumber(), 
            address.getPostalCode()
        );
        this.user = user;
        this.hoa = hoa;
    }


    /**
     * Converts this UserHoa object to a FullUserHoaModel object.
     * This DTO is used to prevent infinite loops when serializing the UserHoa object.
     * @return a FullUserHoaModel object with the user, HOA, and address information from this UserHoa object
     */
    public FullUserHoaModel toFullModel() {
        SimpleUserResponseModel user = this.user.toSimpleModel();
        SimpleHoaResponseModel hoa = this.hoa.toSimpleModel();

        return new FullUserHoaModel(
            user, hoa, this.getCountry(), this.getCity(), 
            this.getStreet(), this.getHouseNumber(), this.getPostalCode()
        );

    }

    /**
     * Converts this UserHoa object to a HoaLessUserHoaModel object.
     * This DTO is used to prevent infinite loops when serializing the Hoa object.
     * @return a HoaLessUserHoaModel object with the user and address information from this UserHoa object
     */
    public HoaLessUserHoaModel toHoaLessModel() {
        SimpleUserResponseModel user = this.user.toSimpleModel();

        return new HoaLessUserHoaModel(
            user, this.getCountry(), this.getCity(), 
            this.getStreet(), this.getHouseNumber(), this.getPostalCode()
        );
    }

    /**
     * Converts this UserHoa object to a UserLessUserHoaModel object.
     * This DTO is used to prevent infinite loops when serializing the User object.
     * @return a UserLessUserHoaModel object with the HOA and address information from this UserHoa object
     */
    public UserLessUserHoaModel toUserLessModel() {
        SimpleHoaResponseModel hoa = this.hoa.toSimpleModel();

        return new UserLessUserHoaModel(
            hoa, this.getCountry(), this.getCity(), this.getStreet(), 
            this.getHouseNumber(), this.getPostalCode()
            );
    }

}
