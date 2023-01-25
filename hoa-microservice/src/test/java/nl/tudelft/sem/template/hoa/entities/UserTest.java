package nl.tudelft.sem.template.hoa.entities;

import nl.tudelft.sem.template.commons.models.hoa.FullAddressModel;
import nl.tudelft.sem.template.commons.models.hoa.SimpleUserResponseModel;
import nl.tudelft.sem.template.commons.models.hoa.UserLessUserHoaModel;
import nl.tudelft.sem.template.commons.models.hoa.FullUserResponseModel;
import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.hoa.entitites.UserHoa;
import nl.tudelft.sem.template.hoa.entitites.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
    private static UserHoa userHoa1;
    private static Hoa hoa;
    private static FullAddressModel fullAddressModel;
    private static Hoa hoa2;
    private static UserHoa userHoa2;
    private User user1;

    /**
     * Initializes the environment for each test
     */
    @BeforeEach
    public void initialize() {
        hoa = new Hoa();
        hoa.setName("Example HOA");
        user1 = new User("example1");
        fullAddressModel = new FullAddressModel("Netherlands",
                "Delft",
                "Mekelweg",
                "4",
                "2628CD");
        userHoa1 = new UserHoa(user1, hoa, fullAddressModel);
        hoa = hoa.addMember(userHoa1);
        user1.joinAssociation(userHoa1);
        hoa2 = new Hoa();
        hoa2.setName("Second example HOA");
        userHoa2 = new UserHoa(user1, hoa2, fullAddressModel);
    }

    @Test
    public void leaveAssociationTest() {
        boolean value = user1.leaveAssociation(userHoa1);
        assertEquals(true, value);
    }

    @Test
    public void joinSecondHoaTest() {
        User value = user1.joinAssociation(userHoa2);
        assertEquals(user1, value);
    }

    @Test
    public void leaveDifferentHoaTest() {
        boolean value = user1.leaveAssociation(userHoa2);
        assertEquals(false, value);
    }

    @Test
    public void simpleResponseModelTest() {
        SimpleUserResponseModel expected = new SimpleUserResponseModel(user1.getDisplayName());
        assertEquals(expected, user1.toSimpleModel());
    }

    @Test
    public void fullUserResponseModelTest() {
        Set<UserLessUserHoaModel> set = new HashSet<>();
        set.add(userHoa1.toUserLessModel());
        FullUserResponseModel expected = new FullUserResponseModel(set, user1.getDisplayName());
        assertEquals(expected, user1.toFullModel());
    }

}
