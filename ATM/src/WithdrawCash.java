package OODPracticeExample.ATM;

public class WithdrawCash implements Transaction {
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
        boolean ack = atmServices.withdrawCash(amount);
        if (ack) {
            CashDispenser cashDispenser = CashDispenser.getCashDispenserUnqInst();
            return cashDispenser.dispenseCash(amount);
        } else {
            System.out.println("Transaction has failed");
            return false;
        }
    }
}
