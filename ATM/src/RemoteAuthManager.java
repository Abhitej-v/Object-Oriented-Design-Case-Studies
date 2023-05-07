package OODPracticeExample.ATM;

import java.rmi.Naming;

public class RemoteAuthManager {
    ValidatePin authManagerProxy;
    static volatile RemoteAuthManager remoteAuthManagerUnqInst;

    public boolean validateSession(MetaData metaData) {
        Screen screen = Screen.getScreenUniqueInstance();
        screen.displayEnterPin();
        Keypad keypad = Keypad.getKeypadUniqueInstance();
        int enteredPin = keypad.getPin();
        try {
            boolean ack = authManagerProxy.validatePin(metaData, enteredPin);
            if (ack) {
                ATM atm = ATM.getATMUniqueInstance();
                atm.lookUpATMServicesProxy(metaData);
                changeToActiveState();
            }
            return ack;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void changeToActiveState() {
        StateManager stateManager = StateManager.getStateMngrUnqInst();
        stateManager.setState(stateManager.activeSession);
    }

    public static RemoteAuthManager getUnqRemoteAuthMngrInst() {
        if (remoteAuthManagerUnqInst == null) {
            synchronized (RemoteAuthManager.class) {
                if (remoteAuthManagerUnqInst == null) {
                    remoteAuthManagerUnqInst = new RemoteAuthManager();
                }
            }
        }
        return remoteAuthManagerUnqInst;
    }

    private RemoteAuthManager() {
        try {
            this.authManagerProxy = (ValidatePin) Naming.lookup("rmi://127.0.0.1/AuthManager");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
