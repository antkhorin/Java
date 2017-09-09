package expression;
public interface Tabulator {
    public Object[][][] tabulate(String mode, String expression, Integer x1, Integer x2, Integer y1, Integer y2, Integer z1, Integer z2) throws Exception;
}