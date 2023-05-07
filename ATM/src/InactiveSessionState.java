package OODPracticeExample.ATM;

public class InactiveSessionState implements State {

    @Override
    public boolean requestSession() {
        CardReader cardReader = CardReader.getUniqueCardReaderInstance();
        StateManager stateManager = StateManager.getStateMngrUnqInst();
        stateManager.setState(stateManager.sessionRequestedState);
        return cardReader.readCardDetails();
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
