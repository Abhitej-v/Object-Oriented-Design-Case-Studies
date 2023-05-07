package OODPracticeExample.ATM;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.NoSuchElementException;

public class ATM {
    RMI_ATM_servicesHandler rmiAtmServicesHandlerProxy;
    ATMServices atmServicesProxy;
    static volatile ATM atmUniqueInstance;


    public static ATM getATMUniqueInstance() {
        if (atmUniqueInstance == null) {
            synchronized (ATM.class) {
                if (atmUniqueInstance == null) {
                    atmUniqueInstance = new ATM();
                }
            }
        }
        return atmUniqueInstance;
    }

    private ATM() {
        try {
            this.rmiAtmServicesHandlerProxy = (RMI_ATM_servicesHandler) Naming.lookup("rmi://127.0.0.1/RMI_ATM_servicesHandlerC");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void lookUpATMServicesProxy(MetaData metaData) throws RemoteException {
        String path = rmiAtmServicesHandlerProxy.getATMServicesProxy(metaData);
        try {
            atmServicesProxy = (ATMServices) Naming.lookup("rmi://127.0.0.1/" + path);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void clearATMServicesProxy() {
        atmServicesProxy = null;
    }

    public ATMServices getATMServicesProxy() {
        if (atmServicesProxy != null) {
            return this.atmServicesProxy;
        } else {
            throw new NoSuchElementException("The requested ATMServicesProxy doesn't exist");
        }
    }

    public boolean performTransaction(TransactionType transactionType) {
        Transaction transaction = TransactionFactory.createTransaction(transactionType);

        boolean ack = transaction.execute();
        if (ack) {
            // call the setter method for changing transactionStatus to successful for the respective transaction object
            clearATMServicesProxy();
            StateManager stateManager = StateManager.getStateMngrUnqInst();
            CardReader cardReader = CardReader.getUniqueCardReaderInstance();
            Printer printer = Printer.getPrinterUnqInst();
            printer.printReceipt(transaction);
            stateManager.setState(StateManager.stateMngrUnqInst.inactiveSessionState);
            cardReader.ejectCard();
        } else {
            // call the setter method for changing transactionStatus to failed for the respective transaction object
        }
        return ack;
    }
}
