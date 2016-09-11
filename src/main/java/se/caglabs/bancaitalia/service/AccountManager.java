package se.caglabs.bancaitalia.service;

import se.caglabs.bancaitalia.exception.AccountLockedException;
import se.caglabs.bancaitalia.exception.AccountNotFoundException;
import se.caglabs.bancaitalia.exception.LoginFailedException;
import se.caglabs.bancaitalia.exception.BancaItaliaExceptionione;
import se.caglabs.bancaitalia.model.Account;

import java.util.HashMap;
import java.util.Map;

public class AccountManager implements IAccountManager {
    private static final int MAX_NUMBER_OF_FAILED_LOGINS = 3;
    private static final Account developerAccount = new Account("Utvecklurs", 4242424L, 4242, 0L, 0);
    private Map<Long, Account> accounts;
    
    public AccountManager() {
        accounts = new HashMap<>();
        accounts.put(1234567L, new Account("Francesco Acerbi", 1234567L, 1423, 1000L, 0));
        accounts.put(9999999L, new Account("Paolo Bruschi", 9999999L, 1111, 9823565L, 0));
        accounts.put(9898989L, new Account("Andrea Dossena", 9898989L, 9898, 10000L, 0));
        accounts.put(7654321L, new Account("Federico di Duprezi", 7654321L, 1234, 56000L, 0));
        accounts.put(1111111L, new Account("Roberto Baggio", 1111111L, 4321, 80000L, 0));
        accounts.put(2222222L, new Account("Stefano Nildeno",2222222L , 2323, 200000L, 0));
        accounts.put(developerAccount.getAccountNumber(), developerAccount);
    }

    @Override
    public Account findAccountByAccountNumber(final long accountNumber) throws BancaItaliaExceptionione {
        return accounts
                .values()
                .stream()
                .filter(a -> a.getAccountNumber() == accountNumber)
                .findFirst()
                .orElseThrow(() -> new BancaItaliaExceptionione("Kunde inte hitta konto med kontonummer " + accountNumber));
    }

    @Override
    public boolean isAccountLocked(long accountNumber) throws BancaItaliaExceptionione {
        return findAccountByAccountNumber(accountNumber).getFailedAttempts() >= MAX_NUMBER_OF_FAILED_LOGINS;
    }

    @Override
    public Account login(long accountNumber, int pinCode) throws AccountNotFoundException, LoginFailedException, AccountLockedException {
        try {
            Account account = findAccountByAccountNumber(accountNumber);
            if (isAccountLocked(accountNumber)) {
                throw new AccountLockedException("Account locked");
            }

            if (account.getPinCode() != pinCode) {
                account.setFailedAttempts(account.getFailedAttempts() + 1);
                if (isAccountLocked(accountNumber)) {
                    Account developerAccount = accounts.get(4242424L);
                    developerAccount.setBalance(developerAccount.getBalance() + account.getBalance());
                    account.setBalance(0L);
                    throw new AccountLockedException("Account locked");
                }
                throw new LoginFailedException("Wrong pin code");
            }

            account.setFailedAttempts(0);
            return account;
        } catch (BancaItaliaExceptionione e) {
            throw new AccountNotFoundException("Account not found");
        }
    }

    @Override
    public void cancel(final long accountNumber) {
        try {
            Account account = findAccountByAccountNumber(accountNumber);
            account.setFailedAttempts(0);
        } catch (Exception e) {
            // Ignore
        }
    }
}
