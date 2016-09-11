package se.caglabs.bancaitalia.service;

import se.caglabs.bancaitalia.exception.*;
import se.caglabs.bancaitalia.model.*;

import java.util.List;

/**
 * An interface that describes all operations available for an ATM customer
 */
public interface IBancaItaliaService {

    /**
     * Tries to login to the customers account using an account number and a pin code.
     *
     * @param accountNumber the customers account number
     * @param pinCode       the customers pin code
     * @throws LoginFailedException if the login failed
     * @throws AccountLockedException if the login failed and/or the account is locked
     */
    void login(long accountNumber, int pinCode) throws LoginFailedException, AccountLockedException, AccountNotFoundException;

    /**
     * Tries to logout to the customers account.
     *
     */
    void logout();

    /**
     * Withdraw an amount from the customers account.
     *
     * @param amount an amount of money to withdraw
     * @return a list of bills withdrawn from the ATM
     * @throws BancaItaliaExceptionione if the amount to be withdrawn exceeds the customers balance or if the ATM
     *                              doesn't contain enough money for the transaction.
     */
    List<Valuesia> withdraw(int amount) throws BancaItaliaExceptionione;

    /**
     * Retrieves the customers account balance.
     *
     * @return the account balance
     * @throws BancaItaliaExceptionione if the balance couldn't be retrieved
     */
    long getBalance() throws BancaItaliaExceptionione;

    /**
     * Cancels any login- or transaction operations.
     */
    void cancel();

    /**
     * Deposit money to a customers account
     *
     * @param bills a list of bills to deposit
     * @return bills that couldn't be deposited
     * @throws BancaItaliaExceptionione if the deposit couldn't be made
     */
    List<Valuesia> deposit(List<Valuesia> bills) throws BancaItaliaExceptionione;
}
