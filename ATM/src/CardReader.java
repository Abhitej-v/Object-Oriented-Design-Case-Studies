package OODPracticeExample.ATM;

public class CardReader {
    RemoteAuthManager authMngr;
    static volatile CardReader cardReaderUniqueInstance;

    public boolean readCardDetails() {
        // read the metadata from the magnetic strip of an ATM card
        MetaData metaData = new MetaData(); // for simplicity and practical reasons.
        return authMngr.validateSession(metaData);
    }

    public void ejectCard() {
        // run routines to eject the card from card reader
        System.out.println("Please collect your card");
    }

    public static CardReader getUniqueCardReaderInstance() {
        if (cardReaderUniqueInstance == null) {
            synchronized (CardReader.class) {
                if (cardReaderUniqueInstance == null) {
                    cardReaderUniqueInstance = new CardReader();
                }
            }
        }
        return cardReaderUniqueInstance;
    }

    private CardReader() {
        this.authMngr = RemoteAuthManager.getUnqRemoteAuthMngrInst();
    }
}
