
package org.deloitte.atm;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
public class ATMCashTest  {

    @Test
    public  void withdrawCash(){
        Currency rupee = Currency.builder().currency("Rupees").symbol("Rs").build();
        Denomination rupees100 = Denomination.builder().currency(rupee).value(100).build();
        Denomination rupees500 = Denomination.builder().currency(rupee).value(500).build();
        ATMMachine atmMachine = new ATMMachine();
        ATMCash cash = new ATMCash(rupee);
        atmMachine.addCashPool(cash);
        atmMachine.addCashToCashPool(rupee, rupees500, 5);
        atmMachine.addCashToCashPool(rupee, rupees100, 10);
        CashWithdrawRequest cashWithdrawalRequest = cash.withdrawCash(500, "dispersalId");
        assertEquals("1",cashWithdrawalRequest.getWithdrawalCashMap().get(rupees500).toString());

        assertThrows(Exception.class,()->cash.withdrawCash(500000, "dispersalId"));
    }


}