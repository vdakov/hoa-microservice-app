package nl.tudelft.sem.template.hoa.exceptions;

public class UserDoesNotExistException extends Exception {
    static final long serialVersionUID = 284849484435L;

    public UserDoesNotExistException(String str) {
        super(str);
    }
}
