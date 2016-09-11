/*
 * NYPS 2020
 *
 * User: joebin
 * Date: 2016-03-10
 * Time: 18:12
 */
package se.caglabs.bancaitalia.service;


import se.caglabs.bancaitalia.exception.AccountLockedException;
import se.caglabs.bancaitalia.exception.AccountNotFoundException;
import se.caglabs.bancaitalia.exception.BancaItaliaExceptionione;
import se.caglabs.bancaitalia.exception.LoginFailedException;
import se.caglabs.bancaitalia.model.Account;
import se.caglabs.bancaitalia.model.Valuesia;

import java.util.List;

public class BancaItaliaService implements IBancaItaliaService {
    private static final long MAX_ACCOUNT_BALANCE = 1000000L;
    private static final int MAX_WITHDRAW_AMOUNT = 5000;

    private IBillbox billbox;
    private final IAccountManager accountManager;
    private Long accountNumber = null;
    private boolean authenticated = false;

    public BancaItaliaService(IBillbox billbox, IAccountManager accountManager) {
        this.billbox = billbox;
        this.accountManager = accountManager;
    }

    @Override
    public void login(long accountNumber, int pinCode) throws LoginFailedException, AccountLockedException, AccountNotFoundException {
        this.accountNumber = accountNumber;
        accountManager.login(accountNumber, pinCode);
        authenticated = true;
    }

    @Override
    public void logout() {
        authenticated = false;
    }

    @Override
    public List<Valuesia> withdraw(int amount) throws BancaItaliaExceptionione {
        if(!authenticated) {
            throw new BancaItaliaExceptionione("Not authenticated");
        }

        if(amount > MAX_WITHDRAW_AMOUNT){
            throw new BancaItaliaExceptionione("Can not withdraw more than " + MAX_WITHDRAW_AMOUNT + "kr");
        }

        Account account = accountManager.findAccountByAccountNumber(accountNumber);
        if(account.getBalance() < amount ){
            throw new BancaItaliaExceptionione("Not enough money on the account");
        }

        List<Valuesia> withdrawnBills = billbox.withdraw(amount);
        account.setBalance(account.getBalance() - amount);
        return withdrawnBills;
    }

    @Override
    public long getBalance() throws BancaItaliaExceptionione {
        if(!authenticated) {
            throw new BancaItaliaExceptionione("Not authenticated");
        }

        Account account = accountManager.findAccountByAccountNumber(accountNumber);
        return account.getBalance();
    }

    @Override
    public void cancel() {
        this.accountManager.cancel(accountNumber);
        this.accountNumber = null;
    }

    @Override
    public List<Valuesia> deposit(List<Valuesia> bills) throws BancaItaliaExceptionione {
        if(!authenticated) {
            throw new BancaItaliaExceptionione("Not authenticated");
        }

        long totalDepositAmount = bills.stream().mapToLong(Valuesia::getNoteValue).sum();
        if( totalDepositAmount > MAX_ACCOUNT_BALANCE) {
            throw new BancaItaliaExceptionione("Couldn't deposit " + totalDepositAmount + "kr because this would violate the max account balance of " + MAX_ACCOUNT_BALANCE + "kr");
        }

        List<Valuesia> returnedBills = billbox.deposit(bills);
        long actualDepositAmount = totalDepositAmount - returnedBills.stream().mapToLong(Valuesia::getNoteValue).sum();

        Account account = accountManager.findAccountByAccountNumber(accountNumber);
        account.setBalance(account.getBalance() + actualDepositAmount);

        return returnedBills;
    }
}
