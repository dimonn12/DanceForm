package by.danceform.app.exception;

/**
 * Created by dimonn12 on 29.10.2016.
 */
public enum ApplicationExceptionCodes {

    REGISTRATION_CLOSED(1, "Registration is not available");

    private final int id;
    private final String value;

    ApplicationExceptionCodes(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

}
