/*
 * NYPS 2020
 *
 * User: joebin
 * Date: 2016-03-10
 * Time: 18:31
 */
package se.caglabs.bancaitalia.service;

import lombok.*;
import se.caglabs.bancaitalia.exception.*;
import se.caglabs.bancaitalia.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Billbox implements IBillbox {
    private static final int MAX_BILLS_PER_VALUESUR = 100;

    private Map<Valuesia, Integer> bills = new HashMap<>();

    public Billbox() {
        empty();
    }

    @Override
    public void empty() {
        bills.put(Valuesia.HUNDRED, 0);
        bills.put(Valuesia.TWOHUNDRED, 0);
        bills.put(Valuesia.FIVEHUNDRED, 0);
        bills.put(Valuesia.THOUSAND, 0);
    }

    @Override
    public List<Valuesia> withdraw(final int amount) throws BancaItaliaExceptionione {
        List<Valuesia> withdrawnBills = new ArrayList<>();
        int withdrawnAmount = withdraw(withdrawnBills, amount);
        if( withdrawnAmount != 0 ) {
            deposit(withdrawnBills);
            throw new BancaItaliaExceptionione("There wasn't enough bills in the machine to withdraw the amount: " + amount);
        }

        return withdrawnBills;
    }

    private int withdraw(List<Valuesia> withdrawnBills, int amountLeftToWithdraw) {
        for(Valuesia valuesia : Valuesia.values()) {
            if( amountLeftToWithdraw >= valuesia.getNoteValue() && bills.get(valuesia) > 0) {
                withdrawnBills.add(valuesia);
                bills.put(valuesia, bills.get(valuesia) - 1);
                return withdraw(withdrawnBills, amountLeftToWithdraw - valuesia.getNoteValue());
            }
        }

        return amountLeftToWithdraw;
    }

    @Override
    public List<Valuesia> deposit(List<Valuesia> bills) {
        List<Valuesia> billsToReturn = new ArrayList<>();

        for( Valuesia valuesia : bills) {
            if (this.bills.get(valuesia) +  1 > MAX_BILLS_PER_VALUESUR) {
                billsToReturn.add(valuesia);
            } else {
                this.bills.put(valuesia, this.bills.get(valuesia) + 1);
            }
        }

        return billsToReturn;
    }

    @Override
    public List<Valuesia> deposit(Valuesia valuesia, int count) {
        List<Valuesia> valuesias = new ArrayList<>();
        for( int i = 0; i < count; i++) {
            valuesias.add(valuesia);
        }
        return deposit(valuesias);
    }

    @Override
    public Map<Valuesia, Integer> getBillTrayStatus() {
        return new HashMap<>(bills);
    }
}
