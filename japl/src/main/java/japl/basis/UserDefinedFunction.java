package japl.basis;

import java.util.Deque;

public class UserDefinedFunction implements BinaryFunction, UnaryFunction{
    private Deque<Object> alphaStack;
    private Deque<Object> omegaStack;
    private StackOperation op;
    private boolean unary = false;
    private boolean aplSystem;

    public UserDefinedFunction(StackOperation op,Deque<Object> alphaStack,
            Deque<Object> omegaStack,boolean unary,boolean aplSystem) {
        super();
        this.op = op;
        this.alphaStack = alphaStack;
        this.omegaStack = omegaStack;
        this.unary = unary;
        this.aplSystem = aplSystem;
    }


    @Override
    public Object apply(Object alpha, Object omega) {
        alphaStack.push(alpha);
        omegaStack.push(omega);
        WorkingStack stack = new WorkingStack(aplSystem);
        stack.apply(op);
        omegaStack.pop();
        alphaStack.pop();
        return stack.reduce();
    }


    @Override
    public Object apply(Object omega) {
        omegaStack.push(omega);
        WorkingStack stack = new WorkingStack(aplSystem);
        stack.apply(op);
        omegaStack.pop();
        return stack.reduce();
    }


    public boolean isUnary() {
        return unary;
    }

}
