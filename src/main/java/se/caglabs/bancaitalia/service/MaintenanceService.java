package se.caglabs.bancaitalia.service;

import se.caglabs.bancaitalia.exception.BancaItaliaExceptionione;
import se.caglabs.bancaitalia.model.Valuesia;

import java.util.List;
import java.util.Map;

/**
 * Project:Tungur-knivur
 * User: fredrik
 * Date: 16/03/16
 * Time: 18:27
 */
public class MaintenanceService implements IMaintenanceService {


    private IBillbox billbox;

    public MaintenanceService(IBillbox billbox) {
        this.billbox = billbox;
    }

    @Override
    public List<Valuesia> loadBills(Valuesia valuesia, int count) throws BancaItaliaExceptionione {
        return billbox.deposit(valuesia, count);
    }

    @Override
    public Map<Valuesia, Integer> getBillTrayStatus() {
        return billbox.getBillTrayStatus();
    }

    @Override
    public void emptyBillTrays() {
        billbox.empty();
    }
}
