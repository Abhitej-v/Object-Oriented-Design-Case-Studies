package OODPracticeExample.ATM;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class AuthManager extends UnicastRemoteObject implements ValidatePin {
    private static final long serialVersionId = 1L;
    HashMap<Long, Integer> cardNumPinMap = new HashMap<Long, Integer>();
    static volatile AuthManager authManagerUnqInst;

    public static AuthManager getAuthUnqInst() throws RemoteException {
        // implement singleton pattern for auth manager
        return new AuthManager(); // to avoid warnings
    }

    private AuthManager() throws RemoteException {
    }

    @Override
    public boolean validatePin(MetaData metaData, int pin) {
        int registeredPin = cardNumPinMap.get(metaData.cardNum);
        if (registeredPin == pin) {
            return true;
        } else {
            return false;
        }
    }
}
