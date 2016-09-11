package se.caglabs.bancaitalia.exception;

public class AccountLockedException extends Exception {
    public AccountLockedException(String msg) {
        super(msg);
    }
}
