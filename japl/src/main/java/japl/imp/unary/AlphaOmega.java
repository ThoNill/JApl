package japl.imp.unary;

import java.util.Deque;

import japl.basis.Creator;

public class AlphaOmega implements Creator{
    private Deque<Object> stack;

    public AlphaOmega(Deque<Object> stack) {
        super();
        this.stack = stack;
    }

    @Override
    public Object create() {
       return stack.peek();
    }
    

}
