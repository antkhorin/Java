package expression;
public class CheckedAbs extends AbstractUnaryOperation {
    public CheckedAbs(AbstractOperation operation1) {
        this.operation1 = operation1;
    }

    protected int count(int a) throws Exception {
        if (a == Integer.MIN_VALUE) {
            throw new Exception("overflow");
        } else {
            return a < 0 ? -a : a;
        }
    }
}