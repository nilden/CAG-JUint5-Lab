package se.caglabs.bancaitalia.service;

import se.caglabs.bancaitalia.exception.*;
import se.caglabs.bancaitalia.model.*;

import java.util.List;
import java.util.Map;

/**
 * An interface describing a billbox that contains money in different trays.
 */
public interface IBillbox {

    /**
     * Clears all money in the billbox
     */
    void empty();

    /**
     * Withdraws an amount of money from the billbox
     *
     * @param amount the amount of money to withdraw
     * @return a list of money withdrawn from the billbox
     * @throws BancaItaliaExceptionione if the money couldn't be withdrawn
     */
    List<Valuesia> withdraw(int amount) throws BancaItaliaExceptionione;

    /**
     * Deposits money to the billbox
     *
     * @param bills a list of bills to deposit
     * @return a list of money that couldn't be added
     */
    List<Valuesia> deposit(List<Valuesia> bills);

    /**
     * Deposits money to the billbox
     *
     * @param valuesia the note value beeing added
     * @param count    the number of bills beeing added
     * @return a list of money that couldn't be added
     */
    List<Valuesia> deposit(Valuesia valuesia, int count);

    /**
     * Returns a map with trays for each note value and the number of bills for each tray
     *
     * @return a map with the tray and the number of bills
     */
    Map<Valuesia, Integer> getBillTrayStatus();
}
