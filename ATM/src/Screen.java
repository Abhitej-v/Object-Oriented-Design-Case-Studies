package OODPracticeExample.ATM;

public class Screen {
    static volatile Screen screenUniqueInstance;

    public void displayEnterPin() {
        System.out.println(" enter your verification pin");
    }

    public void displayEnterAmount() {
        System.out.println("enter the amount of the transaction");
    }

    public boolean showAmount(long amount) {
        // display the given amount on the screen
        return true;
    }

    public void showSuccessMsg() {
        System.out.println("Transaction is successful");
    }

    public void showFailureMsg() {
        System.out.println("Transaction has failed");
    }

    public static Screen getScreenUniqueInstance() {
        if (screenUniqueInstance == null) {
            synchronized (Screen.class) {
                if (screenUniqueInstance == null) {
                    screenUniqueInstance = new Screen();
                }
            }
        }
        return screenUniqueInstance;
    }

    private Screen() {
    }
}
