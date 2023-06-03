package OODPracticeExample.Splitwise;

import java.util.HashMap;
import java.util.Map;

public class Ledger {
    private HashMap<User, Double> userBalanceMap=new HashMap<User, Double>();

    public void updateLedger(User user, Double amount) {
        userBalanceMap.put(user, userBalanceMap.getOrDefault(user, 0.0) + amount);
    }

    public boolean displayFullLedger(String pUserId) {
        boolean ack=false;
        for (Map.Entry<User, Double> userRecord : userBalanceMap.entrySet()) {
            Double balance = userBalanceMap.get(userRecord.getKey());
            if (balance != 0.00) {
                ack=true;
                String userId = userRecord.getKey().getUserId();
                if (balance < 0.00) {
                    System.out.println(pUserId + " owes " + userId + ": " + Math.abs(balance));
                } else {
                    System.out.println(userId + " owes " + pUserId + ": " + Math.abs(balance));
                }
            } else {
                continue;
            }
        }
        return ack;
    }

    public boolean displayLiabilities(String pUserId) {
        boolean ack=false;
        for (Map.Entry<User, Double> userRecord : userBalanceMap.entrySet()) {
            Double balance = userBalanceMap.get(userRecord.getKey());
            if (balance < 0.00) {
                ack=true;
                String userId = userRecord.getKey().getUserId();
                System.out.println(pUserId + " owes " + userId + ": " + Math.abs(balance));
            } else {
                continue;
            }
        }
        return ack;
    }
}
