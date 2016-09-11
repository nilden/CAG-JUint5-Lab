package se.caglabs.bancaitalia.service;

import se.caglabs.bancaitalia.exception.*;
import se.caglabs.bancaitalia.model.*;

import java.util.List;
import java.util.Map;

/**
 * An interface describing all maintenance operations for the ATM
 */
public interface IMaintenanceService {

    /**
     * Loads the ATM with bills
     *
     * @param valuesia the bill value being added
     * @param count    the number of bills being added
     * @return all bills that wasn't accepted by the machine
     * @throws BancaItaliaExceptionione if the operation failed.
     */
    List<Valuesia> loadBills(Valuesia valuesia, int count) throws BancaItaliaExceptionione;

    /**
     * Shows the status of all trays in the machine.
     *
     * @return a map with all bill trays with the note value as the key and the number of bills as the value
     */
    Map<Valuesia, Integer> getBillTrayStatus();

    /**
     * Empties all trays with money
     */
    void emptyBillTrays();
}
