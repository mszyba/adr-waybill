package eu.michalszyba.adrwaybill.exception;

import java.util.NoSuchElementException;

public class CustomerIsNotForCompanyException extends NoSuchElementException {
    public CustomerIsNotForCompanyException(String message) {
        super(message);
    }
}
