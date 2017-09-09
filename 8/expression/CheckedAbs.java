package expression;
import java.math.BigInteger;
public class CheckedAbs<T extends MyNumber<T>> extends AbstractUnaryOperation<T> {
    public CheckedAbs(AbstractOperation<T> operation1) {
        this.operation1 = operation1;
    }

    protected  T count(T a) throws Exception {
        return a.abs();
    }
}