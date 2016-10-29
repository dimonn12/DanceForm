package by.danceform.app.exception;

/**
 * Created by dimonn12 on 29.10.2016.
 */
public class ApplicationException extends RuntimeException {

    private final ApplicationExceptionCodes code;

    public ApplicationException(ApplicationExceptionCodes code) {
        this.code = code;
    }

    public ApplicationExceptionCodes getCode() {
        return code;
    }

}
