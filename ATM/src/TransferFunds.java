package OODPracticeExample.ATM;

public class TransferFunds implements Transaction {
    TransactionStatus transactionStatus;

    @Override
    public boolean execute() {
        this.transactionStatus = TransactionStatus.Processing;
        Screen screen = Screen.getScreenUniqueInstance();
        screen.displayEnterAmount();
        Keypad keypad = Keypad.getKeypadUniqueInstance();
        long amount = keypad.getAmount();
        long recipientAccNum = keypad.getRecipientAccNum();
        ATM atm = ATM.getATMUniqueInstance();
        ATMServices atmServices = atm.getATMServicesProxy();
        return atmServices.transferFunds(recipientAccNum, amount);
    }
}
