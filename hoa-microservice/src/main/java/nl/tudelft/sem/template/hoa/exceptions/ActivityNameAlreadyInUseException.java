package nl.tudelft.sem.template.hoa.exceptions;

public class ActivityNameAlreadyInUseException extends Exception {
    static final long serialVersionUID = 2465522198L;

    public ActivityNameAlreadyInUseException(String name) {
        super(name);
    }
}
