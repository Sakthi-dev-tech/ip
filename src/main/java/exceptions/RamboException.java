package exceptions;

/**
 * Represents an error caused by invalid input to Rambo.
 */
public class RamboException extends Exception {

    /**
     * Creates an exception with a user-friendly explanation of the error.
     *
     * @param message explanation shown to the user
     */
    public RamboException(String message) {
        super(message);
    }
}
