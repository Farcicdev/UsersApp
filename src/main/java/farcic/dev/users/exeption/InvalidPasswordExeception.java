package farcic.dev.users.exeption;

public class InvalidPasswordExeception extends RuntimeException {

    public InvalidPasswordExeception(String message) {
        super(message);
    }
}
