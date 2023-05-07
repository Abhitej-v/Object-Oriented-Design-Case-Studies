package OODPracticeExample.ATM;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMI_ATM_servicesHandler extends Remote {
    String getATMServicesProxy(MetaData metaData) throws RemoteException;
}
