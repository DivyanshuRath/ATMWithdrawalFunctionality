package org.deloitte.atm;

import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class CashWithdrawRequest {

    @Getter
    private UUID requestId;

    private Map<Denomination, Integer> cashWithdrawal;

    public CashWithdrawRequest(){
        requestId =  UUID.randomUUID();
        cashWithdrawal = new HashMap<>();
    }

    public void addAmount(Denomination denomination, Integer count){
        if(denomination == null || count == null && count <=0){
            throw new RuntimeException("Invalid amount being added");
        }
        cashWithdrawal.put(denomination, count);
    }

    public Map<Denomination, Integer> getWithdrawalCashMap(){
        return Collections.unmodifiableMap(cashWithdrawal);
    }
}
