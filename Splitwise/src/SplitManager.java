package OODPracticeExample.Splitwise;

public class SplitManager {
    private volatile static SplitManager splitMgrUnqInst;

    private SplitManager() {
    }

    public static SplitManager getSplitMgrUniInst() {
        if (splitMgrUnqInst == null) {
            synchronized (SplitManager.class) {
                if (splitMgrUnqInst == null) {
                    splitMgrUnqInst = new SplitManager();
                }
            }
        }
        return splitMgrUnqInst;
    }

    public void distributeExpense(ExpenseMetaData expenseMetaData) {
        SplitStrategyManager splitStrategyManager = SplitStrategyManager.getSplitStrgMngrUnqInst();
        SplitStrategy splitStrategy = splitStrategyManager.getSplitStrategy(expenseMetaData.getSplitFormat());
        splitStrategy.distributeExpense(expenseMetaData);
    }
}
