package org.deloitte.atm;

import lombok.Getter;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class ATMCash {
    private static Logger logger = Logger.getLogger(ATMCash.class.getName());

    private final ConcurrentSkipListMap<Denomination, Integer> availableCash;
    private final ReentrantLock lock = new ReentrantLock();

    @Getter
    private final Currency currency;

    public ATMCash(Currency currency) {

        // Keys are denominations, values are quantity of notes
        this.availableCash = new ConcurrentSkipListMap<Denomination, Integer>(new Comparator<Denomination>() {
            @Override
            public int compare(Denomination o1, Denomination o2) {
                return o1.getValue() - o2.getValue();
            }
        });
        this.currency = currency;
    }

    /**
     * Validates the user provided amount for Withdrawal
     *
     * @param amount Amount to be withdrawn
     * @param dispersalId ids to keep a check for parallel withdrawals
     * @return CashWithdrawRequest
     */
    protected CashWithdrawRequest withdrawCash(int amount, String dispersalId) {
        lock.lock();
        try {
            // Check if the amount is multiple of 10
            if (amount % 10 != 0) {
                //TODO: through specific exception instead of generic exception
                throw new RuntimeException("Invalid amount entered");
            }

            int totalAmountInAtm = 0;
            for(Denomination denominationObj : availableCash.descendingKeySet()){
                Integer denomination = denominationObj.getValue();
                totalAmountInAtm += denomination * availableCash.get(denominationObj);
            }

            if(totalAmountInAtm < amount){
                throw new RuntimeException(dispersalId + ": Insufficient balance in ATM");
            }

            CashWithdrawRequest cashWithdrawRequest = new CashWithdrawRequest();
            // descendingKeySet to get higher demonination value first
            for (Denomination denominationObj : availableCash.descendingKeySet()) {
                Integer denomination = denominationObj.getValue();
                if (amount == 0)
                    break;
                if (amount - denomination >= 0
                        && availableCash.get(denominationObj) > 0) {

                    int neededCount = amount / denomination;
                    int havingDenominationCount = getDenominationCountBasedOnMaintainenceCriteria(denominationObj, neededCount);

                    amount -= (havingDenominationCount * denomination);
                    cashWithdrawRequest.addAmount(denominationObj, havingDenominationCount);

                    if(havingDenominationCount < neededCount){
                        throw new RuntimeException(dispersalId + ": Insufficient balance in ATM");
                    }

                }
            }

            for(Map.Entry<Denomination, Integer> withdrawRequest: cashWithdrawRequest.getWithdrawalCashMap().entrySet()){
                logger.info(dispersalId + ": Denomination withdrwal is : " + withdrawRequest.getKey().getValue() + " :: " + withdrawRequest.getValue());
            }

            if (amount != 0 && cashWithdrawRequest.getWithdrawalCashMap().isEmpty()) {
                // TODO: Raise insufficient funds exception
                new RuntimeException("Insufficient Funds!");
            } else {
                // deduct from pool and create a hold transaction
                for (Map.Entry<Denomination, Integer> cashWithdraw : cashWithdrawRequest.getWithdrawalCashMap().entrySet()) {
                    Integer denominationCount = availableCash.get(cashWithdraw.getKey());
                    availableCash.put(cashWithdraw.getKey(), denominationCount - cashWithdraw.getValue());
                }
                return cashWithdrawRequest;
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    private int getDenominationCountBasedOnMaintainenceCriteria(Denomination denomination, int maxCount) {
        // TODO Auto-generated method stub
        return Math.min(maxCount, availableCash.get(denomination));
    }

    /**
     * addCash to the ATM
     *
     * @param denomination i.e. currency and value
     * @param count of the denomination
     */
    protected void addCash(Denomination denomination, int count) {
        // Check is count is valid
        if (count < 0) {
            throw new RuntimeException("Invalid count");
        }

        int value = denomination.getValue() * count;
        // Check is demonination is valid
        if(value < 0 || value % 10 != 0 || value > Integer.MAX_VALUE){
            throw new RuntimeException("Invalid value");
        }

        Integer availableCount = availableCash.get(denomination);
        if (availableCount == null) {
            availableCount = 0;
        }
        availableCash.put(denomination, availableCount + count);

    }
}
