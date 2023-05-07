package OODPracticeExample.ATM;

public class SessionRequestedState implements State {
    @Override
    public boolean requestSession() {
        System.out.println("A session request is already in progress");
        return false;
    }

    @Override
    public boolean performTransaction(TransactionType transactionType) {
        System.out.println("Session is not active, cant perform any transaction");
        return false;
    }

    @Override
    public void endSession() {
        System.out.println("No active session to end");
    }
}
