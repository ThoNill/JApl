package japl.basis;

import java.util.ArrayDeque;

import org.apache.commons.lang.ArrayUtils;

public class WorkingStack extends ArrayDeque<Object> {
    private ManyStackOperation operations;
    private boolean aplMode;

    public WorkingStack(boolean aplMode) {
        super();
        this.aplMode = aplMode;
    }

    public void setOperations(ManyStackOperation operations) {
        this.operations = operations;
    }

    WorkingStack createSubStack() {
        return new WorkingStack(aplMode);
    }

    public void apply(StackOperation op) {
        op.perform(this);
    }

    Object reduce() {
        Object[] objs = this.toArray();
        if (objs.length == 0) {
            return objs;
        }
        if (objs.length == 1) {
            return objs[0];
        }
        Object[] rev = new Object[objs.length];
        int max = objs.length - 1;
        for (int i = 0; i <= max; i++) {
            rev[i] = objs[max - i];
        }
        if (aplMode) {
            ArrayUtils.reverse(rev);
        }
        return rev;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        Object[] objs = this.toArray();
        for (int i = objs.length - 1; i >= 0; i--) {
            buffer.append("[");
            buffer.append(i);
            buffer.append("]=");
            printObject(buffer, objs[i]);
            buffer.append("\n");
        }
        buffer.append("--\n");
        return buffer.toString();
    }

    private void printObject(StringBuilder buffer, Object obj) {
        if (obj instanceof Object[]) {
            Object[] objs = (Object[]) obj;
            buffer.append(printArray(objs));

        } else {
            buffer.append(obj);
        }
    }

    private String printArray(Object[] objs) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("(");
        boolean komma = false;
        for (Object o : objs) {
            if (komma) {
                buffer.append(",");
            }
            komma = true;
            printObject(buffer, o);
        }
        buffer.append(")");
        return buffer.toString();
    }

    public StackOperation popOperation() {
        return operations.popOperation();
    }

    public ManyStackOperation getOperations() {
        return operations;
    }

}
