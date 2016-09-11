package se.caglabs.bancaitalia.service;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import se.caglabs.bancaitalia.exception.BancaItaliaExceptionione;
import se.caglabs.bancaitalia.model.Valuesia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.expectThrows;

@DisplayName("Tests for BancaItaliaService")
public class BancaItaliaServiceTest {

    Billbox billbox = new Billbox();
    BancaItaliaService bancaItaliaService = new BancaItaliaService(billbox, new AccountManager());

    @Before
    @DisplayName("Insert some bills in the machine so we can test the it...")
    public void setup() throws Exception {

        MaintenanceService maintenanceService = new MaintenanceService(billbox);
        maintenanceService.loadBills(Valuesia.FIVEHUNDRED, 500);
        maintenanceService.loadBills(Valuesia.HUNDRED, 10000);
        maintenanceService.loadBills(Valuesia.THOUSAND, 500);
    }

    @Test
    @DisplayName("When user is not authenticated an exception is thrown")
    public void throwExceptionIfNotAuthenticated() throws Exception {

        Throwable exception = expectThrows(BancaItaliaExceptionione.class, () -> {
            bancaItaliaService.withdraw(1000);
        });

        assertEquals("Not authenticated", exception.getMessage());
    }

    @Test
    @DisplayName("When a user (owner of 1000) is logged in and authenticated the balance of 1000 is returned")
    public void returnBalanceWhenLogedinAndAuthenticated() throws Exception {

        BancaItaliaService service = new BancaItaliaService(billbox, new AccountManager());
        service.login(1234567L, 1423);

        assertEquals(1000L, service.getBalance());
        service.logout();

        @Nested
        @DisplayName("Testing to withdraw some money...")
        class Withdrawal {

            @Test
            @DisplayName("withdrawal of to much money should not be possible ")
            public void notEnoughMoney() throws Exception {

                BancaItaliaService service = new BancaItaliaService(billbox, new AccountManager());
                service.login(1234567L, 1423);

                Throwable exception = expectThrows(BancaItaliaExceptionione.class, () -> {
                    bancaItaliaService.withdraw(10000);
                });

                assertEquals("Can not withdraw more than 1000000L kr", exception.getMessage());
            }

            @Test
            @DisplayName("withdrawal of 100 when 1000 on account leaves 900 left")
            public void returnBalanceWhenLogedinAndAuthenticated() throws Exception {

                MaintenanceService maintenanceService = new MaintenanceService(billbox);
                maintenanceService.loadBills(Valuesia.FIVEHUNDRED, 500);
                maintenanceService.loadBills(Valuesia.HUNDRED, 10000);
                maintenanceService.loadBills(Valuesia.THOUSAND, 500);

                BancaItaliaService service = new BancaItaliaService(billbox, new AccountManager());
                service.login(1234567L, 1423);
                service.withdraw(100);
                assertEquals(service.getBalance(), 900L);
            }
        }
    }
}