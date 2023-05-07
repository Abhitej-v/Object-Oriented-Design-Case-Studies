package OODPracticeExample.ATM;

public class TransactionFactory {
    public static Transaction createTransaction(TransactionType transactionType) {
        Transaction transaction = null;
        if (transactionType == TransactionType.DepositCash) {
            transaction = new DepositCash();
        } else if (transactionType == TransactionType.TransferFunds) {
            transaction = new TransferFunds();
        } else if (transactionType == TransactionType.CheckBalance) {
            transaction = new CheckBalance();
        } else if (transactionType == TransactionType.DepositCheck) {
            transaction = new DepositCheck();
        } else if (transactionType == TransactionType.WithdrawCash) {
            transaction = new WithdrawCash();
        } else {
            System.out.println("Not a valid transaction");
        }
        return transaction;
    }
}
