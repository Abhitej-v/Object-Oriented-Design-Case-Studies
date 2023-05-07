package OODPracticeExample.ATM;

public interface State {
    boolean requestSession();

    boolean performTransaction(TransactionType transactionType);

    void endSession();
}
