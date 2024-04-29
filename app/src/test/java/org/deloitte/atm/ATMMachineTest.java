package org.deloitte.atm;

import org.junit.Test;
import org.junit.Test.None;
import static org.junit.Assert.assertThrows;

public class ATMMachineTest {

    @Test
    public void userCashWithdrawalErrorTest(){
        CashDispersalSystem cashDispersalSystem = CashDispersalSystem.builder().dispersalId("12345").build();
        Currency rupee = Currency.builder().currency("Rupees").symbol("Rs").build();
        ATMMachine atmMachine = new ATMMachine();
        assertThrows(RuntimeException.class,()-> atmMachine.userCashWithdrawal(cashDispersalSystem, rupee, 500));
    }

    @Test(expected = Test.None.class)
    public void userCashWithdrawalTest(){
        CashDispersalSystem cashDispersalSystem = CashDispersalSystem.builder().dispersalId("12345").build();
        Currency rupee = Currency.builder().currency("Rupees").symbol("Rs").build();
        Denomination rupees100 = Denomination.builder().currency(rupee).value(100).build();
        Denomination rupees500 = Denomination.builder().currency(rupee).value(500).build();
        ATMMachine atmMachine = new ATMMachine();
        ATMCash cash = new ATMCash(rupee);
        atmMachine.addCashPool(cash);
        atmMachine.addCashToCashPool(rupee, rupees500, 5);
        atmMachine.addCashToCashPool(rupee, rupees100, 10);
        atmMachine.addDispersalSystem(cashDispersalSystem);
        atmMachine.userCashWithdrawal(cashDispersalSystem, rupee, 500);
    }
}
