package org.deloitte.atm;

import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

@Getter
public class ATMMachine {
    private static Logger logger = Logger.getLogger(ATMMachine.class.getName());


    private Map<Currency, ATMCash> cashPool;

    private Set<CashDispersalSystem> dispersalSystemSet;

    private Map<String, CashWithdrawRequest> transactionsOnHold;

    public ATMMachine(){
        cashPool = new HashMap<>();
        dispersalSystemSet = new HashSet<>();
        transactionsOnHold = new HashMap<>();
    }

    public void addCashPool(ATMCash atmCash){
        if(cashPool.get(atmCash.getCurrency()) != null){
            throw new RuntimeException("Cash pool already exists");
        }
        cashPool.put(atmCash.getCurrency(), atmCash);
    }

    public void addCashToCashPool(Currency currency, Denomination denomination, Integer quantity){
        ATMCash atmCash = cashPool.get(currency);
        atmCash.addCash(denomination, quantity);
    }

    public void addDispersalSystem(CashDispersalSystem dispersalSystem){
        if(dispersalSystemSet == null){
            dispersalSystemSet = new HashSet<>();
        }
        dispersalSystemSet.add(dispersalSystem);
    }

    /**
     * wrapper for loggers and addition to final map according to return from withdraw cash
     *
     * @param dispersalSystem
     * @param currency of the denomination
     * @param amount
     */
    public void userCashWithdrawal(CashDispersalSystem dispersalSystem, Currency currency, Integer amount){
        String dispersalId = dispersalSystem.getDispersalId();
        logger.info(dispersalId + ": Withdrawing " + amount + " rupees");
        if(transactionsOnHold.get(dispersalSystem.getDispersalId()) != null){
            throw new RuntimeException("Transaction on hold in the request dispersal system");
        }
        ATMCash atmCashPool = cashPool.get(currency);
        if(atmCashPool == null){
            throw new RuntimeException("Request currency is not supposed in this machine");
        }
        try {
            CashWithdrawRequest cashWithdrawalRequest = atmCashPool.withdrawCash(amount, dispersalId);
            transactionsOnHold.put(dispersalId, cashWithdrawalRequest);
        }
        catch (Exception e){
            logger.severe(dispersalId + ": Insufficient balance in ATM");
        }

    }

}
