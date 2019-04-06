package japl.imp.unary;

import japl.basis.AplRuntimeException;
import japl.basis.UnaryFunction;

public class UnaryArithFunctions implements UnaryFunction {
    public enum Art { FAKULTÄT }
    private Art art;
  
    public UnaryArithFunctions(Art art) {
        super();
        this.art = art;
    }

    @Override
    public Object applyOne(Object omega) {
        int n = ((Integer)omega).intValue();
        switch( art) {
        case FAKULTÄT:
            long prod = 1;
            for(int i=1;i<=n;i++) {
                prod = prod * i;
            }
            return new Long(prod);
            default:
            break;
        
        }
        throw new AplRuntimeException("Not valid combination " + omega.getClass().getName() + " for operation " + art);
    }

}
