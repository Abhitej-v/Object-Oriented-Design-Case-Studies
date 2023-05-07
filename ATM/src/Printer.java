package OODPracticeExample.ATM;

public class Printer {

    public static Printer getPrinterUnqInst() {
        // implement singleton pattern
        return new Printer();
    }

    public void printReceipt(Transaction transaction) {
        // get all the info from the transaction object and run the routines to print the information
    }
}
