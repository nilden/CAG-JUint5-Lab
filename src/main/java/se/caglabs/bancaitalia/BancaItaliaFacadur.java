package se.caglabs.bancaitalia;

import se.caglabs.bancaitalia.exception.*;
import se.caglabs.bancaitalia.model.*;
import se.caglabs.bancaitalia.service.*;

import java.util.List;
import java.util.Map;

/**
 * A facade for customer- and maintenance ATM services
 */
public class BancaItaliaFacadur implements IBancaItaliaService, IMaintenanceService {

    private IBancaItaliaService iBancaItaliaService;
    private IMaintenanceService maintenanceService;

    /**
     * Constructor for creating a facade
     *
     * @param billbox the billbox containing the money
     * @param accountManager the accountmanager responsible for handling accounts
     */
    public BancaItaliaFacadur(IBillbox billbox, IAccountManager accountManager) {
        this.iBancaItaliaService = new BancaItaliaService(billbox, accountManager);
        this.maintenanceService = new MaintenanceService(billbox);
    }

    /**
     * Tries to login to the customers account using an account number and a pin code.
     *
     * @param accountNumber the customers account number
     * @param pinCode       the customers pin code
     * @throws LoginFailedException if the login failed
     * @throws AccountLockedException if the login failed and/or the account is locked
     * @throws AccountNotFoundException if the account doesn't exist
     */
    @Override
    public void login(long accountNumber, int pinCode) throws LoginFailedException, AccountLockedException, AccountNotFoundException {
        iBancaItaliaService.login(accountNumber, pinCode);
    }

    @Override
    public void logout(){
        iBancaItaliaService.logout();
    }

    /**
     * Withdraw an amount from the customers account.
     *
     * @param amount an amount of money to withdraw
     * @return a list of bills withdrawn from the ATM
     * @throws BancaItaliaExceptionione if the amount to be withdrawn exceeds the customers balance or if the ATM
     *                              doesn't contain enough money for the transaction.
     */
    @Override
    public List<Valuesia> withdraw(int amount) throws BancaItaliaExceptionione {
        return iBancaItaliaService.withdraw(amount);
    }

    /**
     * Retrieves the customers account balance.
     *
     * @return the account balance
     * @throws BancaItaliaExceptionione if the balance couldn't be retrieved
     */
    @Override
    public long getBalance() throws BancaItaliaExceptionione {
        return iBancaItaliaService.getBalance();
    }

    /**
     * Cancels any login- or transaction operations.
     */
    @Override
    public void cancel() {
        iBancaItaliaService.cancel();
    }

    /**
     * Deposit money to a customers account
     *
     * @param bills a list of bills to deposit
     * @return bills that couldn't be deposited
     * @throws BancaItaliaExceptionione if the deposit couldn't be made
     */
    @Override
    public List<Valuesia> deposit(List<Valuesia> bills) throws BancaItaliaExceptionione {
        return iBancaItaliaService.deposit(bills);
    }

    /**
     * Allows maintenance to load the machine with bills
     *
     * @param valuesia the bill value being added
     * @param count    the number of bills being added
     * @return all bills that couldn't be added
     * @throws BancaItaliaExceptionione if the operation failed.
     */
    @Override
    public List<Valuesia> loadBills(Valuesia valuesia, int count) throws BancaItaliaExceptionione {
        return maintenanceService.loadBills(valuesia, count);
    }

    /**
     * Allows maintenance to show the status of all trays in the machine.
     *
     * @return a map with all bill trays with the note value as the key and the
     * number of bills as the value
     */
    @Override
    public Map<Valuesia, Integer> getBillTrayStatus() {
        return maintenanceService.getBillTrayStatus();
    }

    /**
     * Allows maintenance to clear all bill trays in the ATM
     */
    @Override
    public void emptyBillTrays() {
        maintenanceService.emptyBillTrays();
    }


}
