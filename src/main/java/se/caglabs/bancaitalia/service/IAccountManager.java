package se.caglabs.bancaitalia.service;

import se.caglabs.bancaitalia.exception.*;
import se.caglabs.bancaitalia.model.*;

/**
 * An interface describing an account manager for hand
 */
public interface IAccountManager {

    /**
     * Retrieves an account by its account number
     *
     * @param accountNumber the account number of the account to be retrieved
     * @return the account if found
     * @throws BancaItaliaExceptionione if the account couldn't be found an exception will be thrown
     */
    Account findAccountByAccountNumber(long accountNumber) throws BancaItaliaExceptionione;

    /**
     * Returns true if the account is locked because of too many login attempts
     *
     * @param accountNumber the account number of the account
     * @return true if the account is locked
     * @throws BancaItaliaExceptionione if the account couldn't be found
     */
    boolean isAccountLocked(long accountNumber) throws BancaItaliaExceptionione;

    /**
     * Attempt to login to a customers account
     *
     * @param accountNumber the account number for the customer
     * @param pinCode       the pin code for the account
     * @return the account if found
     * @throws AccountNotFoundException if the account couldn't be found
     * @throws LoginFailedException if the login failed
     * @throws AccountLockedException if the account is locked
     */
    Account login(long accountNumber, int pinCode) throws AccountNotFoundException, LoginFailedException, AccountLockedException;

    /**
     * Cancels any account operation
     *
     * @param accountNumber the account number to cancel operations
     */
    void cancel(long accountNumber);
}
