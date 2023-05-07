package OODPracticeExample.ATM;

public class StateManager {
    State state;
    State inactiveSessionState;
    State sessionRequestedState;
    State activeSession;
    static volatile StateManager stateMngrUnqInst;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean requestSession() {
        return state.requestSession();
    }


    private StateManager() {
        this.inactiveSessionState = new InactiveSessionState();
        this.sessionRequestedState = new SessionRequestedState();
        this.activeSession = new ActiveSessionState();
        setState(this.inactiveSessionState);
    }

    public static StateManager getStateMngrUnqInst() {
        if (stateMngrUnqInst == null) {
            synchronized (StateManager.class) {
                if (stateMngrUnqInst == null) {
                    stateMngrUnqInst = new StateManager();
                }
            }
        }
        return stateMngrUnqInst;
    }

    public boolean performTransaction(TransactionType transactionType) {
        return state.performTransaction(transactionType);
    }

    public void endSession() {
        state.endSession();
    }
}
