package OODPracticeExample.ATM;

public class CheckBalance implements Transaction {
    TransactionStatus transactionStatus;

    @Override
    public boolean execute() {
        this.transactionStatus = TransactionStatus.Processing;
        Screen screen = Screen.getScreenUniqueInstance();
        ATM atm = ATM.getATMUniqueInstance();
        ATMServices atmServices = atm.getATMServicesProxy();
        long balance = atmServices.checkBalance();
        return screen.showAmount(balance);
    }
}
