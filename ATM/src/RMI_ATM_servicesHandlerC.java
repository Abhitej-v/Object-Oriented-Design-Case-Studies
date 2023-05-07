package OODPracticeExample.ATM;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class RMI_ATM_servicesHandlerC extends UnicastRemoteObject implements RMI_ATM_servicesHandler {
    HashMap<Long, String> cardNumProxyLookUpAdrsMap = new HashMap<Long, String>();
    private static final long serialVersionId = 2L;
    static volatile RMI_ATM_servicesHandlerC rmiAtmServicesHandlerUnqInst;

    @Override
    public String getATMServicesProxy(MetaData metaData) {
        return cardNumProxyLookUpAdrsMap.get(metaData.cardNum);
    }

    public static RMI_ATM_servicesHandlerC getRmiAtmServicesHandlerUnqInst() throws RemoteException {
        if (rmiAtmServicesHandlerUnqInst == null) {
            synchronized (RMI_ATM_servicesHandlerC.class) {
                if (rmiAtmServicesHandlerUnqInst == null) {
                    rmiAtmServicesHandlerUnqInst = new RMI_ATM_servicesHandlerC();
                }
            }
        }
        return rmiAtmServicesHandlerUnqInst;
    }

    private RMI_ATM_servicesHandlerC() throws RemoteException {
    }
}
