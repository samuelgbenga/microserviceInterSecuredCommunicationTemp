package ng.samuel.mloginregtemp.authenticationservice.exception;

public class NotAuthenticatedException extends RuntimeException{
    public NotAuthenticatedException(String message) {
        super(message);
    }
}