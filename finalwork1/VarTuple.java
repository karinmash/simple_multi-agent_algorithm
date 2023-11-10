package mytry;

import java.util.Objects;

public class VarTuple {

    private int i, j;

    public VarTuple(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public VarTuple getOpposite() {
        return new VarTuple(j, i);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        VarTuple varTuple = (VarTuple) o;
        return i == varTuple.i && j == varTuple.j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }

    @Override
    public String toString() {
        return "(" + i + "," + j + ")";
    }
}
