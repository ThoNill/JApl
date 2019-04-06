package japl.basis;


public class BinaryStackOperation implements StackOperation {

    private BinaryFunction f;
    
    public BinaryStackOperation(BinaryFunction f) {
        super();
        this.f = f;
    }

    private Object calculateAlpha(WorkingStack stack) {
        StackOperation prevOperation = stack.popOperation();
        if (prevOperation instanceof CreatorStackOperation) {
            CreatorStackOperation creatorStackOperation = (CreatorStackOperation) prevOperation;
            if (creatorStackOperation.getCreator() instanceof VariableCreator) {
                VariableCreator vc = (VariableCreator) creatorStackOperation.getCreator();
                return vc.getVariable();
            }
        }
        if (prevOperation instanceof BinaryStackOperation) {
            return ((BinaryStackOperation) prevOperation).getBinaryFunction();
        }
        if (prevOperation instanceof UnaryStackOperation) {
            return ((UnaryStackOperation) prevOperation).getUnaryFunction();
        }

        WorkingStack subStack = stack.createSubStack();
        subStack.setOperations(stack.getOperations());
        prevOperation.perform(subStack);
        return subStack.reduce();

    }

    @Override
    public void perform(WorkingStack stack) {
        Object omege = stack.pop();
        Object alpha = calculateAlpha(stack);
        
        Object res = f.apply(alpha, omege);
        if (res != null) {
            stack.push(res);
        }
    }

    @Override
    public String toString() {
        return "binary operation " + f.getClass().getSimpleName();
    }

    public BinaryFunction getBinaryFunction() {
        return f;
    }
}
