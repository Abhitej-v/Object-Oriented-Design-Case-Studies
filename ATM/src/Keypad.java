package OODPracticeExample.ATM;

import java.util.Scanner;

public class Keypad {
    static volatile Keypad keypadUniqueInstance;

    public int getPin() {
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public int getAmount() {
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public static Keypad getKeypadUniqueInstance() {
        if (keypadUniqueInstance == null) {
            synchronized (Keypad.class) {
                if (keypadUniqueInstance == null) {
                    keypadUniqueInstance = new Keypad();
                }
            }
        }
        return keypadUniqueInstance;
    }

    private Keypad() {
    }

    public long getRecipientAccNum() {
        Scanner sc = new Scanner(System.in);
        long recipientAccNum = sc.nextLong();
        return recipientAccNum;
    }
}
