package OODPracticeExample.ATM;

public class CashDispenser {

    public static CashDispenser getCashDispenserUnqInst() {
        // implement singleton pattern here
        return new CashDispenser();
    }

    public boolean dispenseCash(long amount) {
        // run the  routines to count and dispense the cash
        return true;
    }
}
