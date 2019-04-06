package japl.basis;

public class UnaryStackOperation implements StackOperation{
    private UnaryFunction f;
    
    public UnaryStackOperation(UnaryFunction f) {
        super();
        this.f = f;
    }

    @Override
    public void perform(WorkingStack stack) {
        Object o = stack.pop();
        stack.push(f.apply(o));
        
    }
    

    @Override
    public String toString() {
        return "unary operation " + f.getClass().getSimpleName();
    }

    public UnaryFunction getUnaryFunction() {
        return f;
    }

}
