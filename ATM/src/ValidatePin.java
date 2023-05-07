package OODPracticeExample.ATM;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ValidatePin extends Remote {
    boolean validatePin(MetaData metaData, int pin) throws RemoteException;
}
