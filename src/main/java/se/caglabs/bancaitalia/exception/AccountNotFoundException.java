package se.caglabs.bancaitalia.exception;

public class AccountNotFoundException extends Exception {
    public AccountNotFoundException(String msg) {
        super(msg);
    }
}
