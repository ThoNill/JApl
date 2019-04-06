package japl.imp.unary;

import japl.basis.AplRuntimeException;
import japl.basis.UnaryFunction;

public class Index implements UnaryFunction{

    @Override
    public Object applyOne(Object obj) {
        if (obj instanceof Integer) {
            return generate(0,((Integer)obj).intValue());
        }
        throw new AplRuntimeException("Argument " + obj + " is not an integer");
    }

    private Object generate(int start,int end) {
        if (start <=end) {
            return increment(start, end);
        }
        return decrement(start, end);
    }

    
    private Object increment(int start,int end) {
        Integer[] res = new Integer[end-start+1];
        for(int i = start;i<= end;i++) {
            res[i] = i;
        }
        return res;
    }

    private Object decrement(int start,int end) {
        Integer[] res = new Integer[start-end+1];
        for(int i = start;i>= end;i--) {
            res[i] = i;
        }
        return res;
    }

}
