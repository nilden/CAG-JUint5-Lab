package se.caglabs.bancaitalia.exception;

public class LoginFailedException extends Exception {
    public LoginFailedException(String msg){
        super(msg);
    }
}
