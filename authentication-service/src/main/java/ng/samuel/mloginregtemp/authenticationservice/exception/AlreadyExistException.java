package ng.samuel.mloginregtemp.authenticationservice.exception;

public class AlreadyExistException extends RuntimeException{
    public AlreadyExistException(String message) {
        super(message);
    }
}