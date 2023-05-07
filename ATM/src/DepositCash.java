package OODPracticeExample.ATM;

public class DepositCash implements Deposit {
    TransactionStatus transactionStatus;

    @Override
    public boolean execute() {
        this.transactionStatus = TransactionStatus.Processing;
        Screen screen = Screen.getScreenUniqueInstance();
        screen.displayEnterAmount();
        Keypad keypad = Keypad.getKeypadUniqueInstance();
        long amount = keypad.getAmount();
        ATM atm = ATM.getATMUniqueInstance();
        ATMServices atmServices = atm.getATMServicesProxy();
        return atmServices.depositCash(amount);
    }
}
