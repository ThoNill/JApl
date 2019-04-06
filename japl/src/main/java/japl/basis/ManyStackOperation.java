package japl.basis;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

public class ManyStackOperation implements StackOperation {
    private List<StackOperation> ops = new ArrayList<>();
    private int currentPosition;
    private StackOperation[] aops;
    private boolean aplMode;

    public ManyStackOperation(boolean aplMode) {
        super();
        this.aplMode = aplMode;
    }

    public void add(StackOperation op) {
        ops.add(op);
    }

    public StackOperation lastOp() {
        if (ops.isEmpty()) {
            return null;
        }
        return ops.get(ops.size() - 1);
    }

    private void applyListOfOperations(WorkingStack stack) {
        initOperationArray();

        currentPosition = aops.length - 1;
        while (currentPosition >= 0) {
            StackOperation currentOp = aops[currentPosition];
            currentPosition--;
            stack.apply(currentOp);
        }
    }

    private void initOperationArray() {
        if (aops == null) {
            aops = new StackOperation[ops.size()];
            ops.toArray(aops);
            if (!aplMode) {
                ArrayUtils.reverse(aops);
            }
        }
    }

    StackOperation popOperation() {
        if (currentPosition >= 0) {
            StackOperation op = aops[currentPosition];
            currentPosition--;
            return op;
        } else {
            return null;
        }
    }

    @Override
    public void perform(WorkingStack stack) {
        ManyStackOperation saveOperations = stack.getOperations();
        stack.setOperations(this);
        WorkingStack subStack = stack.createSubStack();
        subStack.setOperations(this);
        applyListOfOperations(subStack);
        if (!subStack.isEmpty()) {
            stack.push(subStack.reduce());
        }
        stack.setOperations(saveOperations);
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("start(\n");
        for (StackOperation op : ops) {
            buffer.append(op.toString());
            buffer.append("\n");
        }
        buffer.append(")end\n");
        return buffer.toString();
    }
}
