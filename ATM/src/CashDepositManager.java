package OODPracticeExample.ATM;

import java.util.ArrayList;

public class CashDepositManager {
    ArrayList<DepositInfoLog> processingItems = new ArrayList<DepositInfoLog>();

    public static CashDepositManager getUnqCashDepositMngr() {
        // implement singleton pattern
        return new CashDepositManager();
    }

    public void addToProcessingItems(DepositInfoLog depositInfoLog) {
        processingItems.add(depositInfoLog);
    }

    public void processItems() {
        // as the items in the processingItems list gets processed the balance in the respective bank accounts gets updated
    }
}
