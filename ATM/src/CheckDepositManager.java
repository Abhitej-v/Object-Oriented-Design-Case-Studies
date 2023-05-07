package OODPracticeExample.ATM;

import java.util.ArrayList;

public class CheckDepositManager {
    ArrayList<DepositInfoLog> processingItems = new ArrayList<DepositInfoLog>();

    public static CheckDepositManager getUnqCheckDepositMngr() {
        // implement singleton pattern
        return new CheckDepositManager();
    }

    public void addToProcessingItems(DepositInfoLog depositInfoLog) {
        processingItems.add(depositInfoLog);
    }

    public void processItems() {
        // as the items in the processingItems list gets processed the balance in the respective bank accounts gets updated
    }
}
