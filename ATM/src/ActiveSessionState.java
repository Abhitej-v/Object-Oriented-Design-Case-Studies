package OODPracticeExample.ATM;

public class ActiveSessionState implements State {

    public ActiveSessionState() {
    }

    @Override
    public boolean requestSession() {
        System.out.println("A session is already active, kindly the current session to start a new session");
        return false;
    }

    @Override
    public boolean performTransaction(TransactionType transactionType) {
        ATM atm = ATM.getATMUniqueInstance();
        return atm.performTransaction(transactionType);
    }

    @Override
    public void endSession() {
        ATM atm = ATM.getATMUniqueInstance();
        atm.clearATMServicesProxy();
        CardReader cardReader = CardReader.getUniqueCardReaderInstance();
        cardReader.ejectCard();
    }
}
