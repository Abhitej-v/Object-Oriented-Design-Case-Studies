package OODPracticeExample.Splitwise;

public class SplitStrategyManager {
    private volatile static SplitStrategyManager splitStrgMgrUnqInst;

    private SplitStrategyManager() {
    }

    public static SplitStrategyManager getSplitStrgMngrUnqInst() {
        if (splitStrgMgrUnqInst == null) {
            synchronized (SplitManager.class) {
                if (splitStrgMgrUnqInst == null) {
                    splitStrgMgrUnqInst = new SplitStrategyManager();
                }
            }
        }
        return splitStrgMgrUnqInst;
    }

    public SplitStrategy getSplitStrategy(SplitFormat splitFormat) {
        if (splitFormat == SplitFormat.Equal) {
            return new EqualSplitStrategy();
        } else if (splitFormat == SplitFormat.Exact) {
            return new ExactSplitStrategy();
        } else if (splitFormat == SplitFormat.Percentage) {
            return new PercentSplitStrategy();
        } else {
            System.out.println("invalid split format");
            return null;
        }
    }
}
