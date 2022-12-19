package nl.tudelft.sem.template.hoa.exceptions;

public class HoaDoesNotExistException extends Exception {
    static final long serialVersionUID = 18797894295L;

    public HoaDoesNotExistException(String str) {
        super(str);
    }
}
