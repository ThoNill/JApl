package japl.imp.binary;

import japl.basis.AplRuntimeException;
import japl.basis.BinaryFunction;
import japl.data.ArrayBuilder;

public class BinaryArithFunctions implements BinaryFunction{
    public enum Art { MODULO, RECHTS,LINKS, CONCAT, MAX, MIN }
    private Art art;
    
    public BinaryArithFunctions(Art art) {
        super();
        this.art = art;
    }

    @Override
    public Object applyOne(Object alpha, Object omega) {
        switch( art) {
        case CONCAT:
            Object[] a = ArrayBuilder.toArray(alpha);
            Object[] o = ArrayBuilder.toArray(omega);
            Object[] concat = new Object[a.length + o.length];
            for (int i=0;i < a.length;i++) {
                concat[i] = a[i];
            }
            for (int i = 0;i < o.length;i++) {
                concat[i+a.length] = o[i];
            }
            return concat;
        case LINKS:
            return alpha;
        case MAX:
            if (alpha instanceof Integer && omega instanceof Integer) {
                return Math.max(((Integer)omega).intValue(),((Integer)alpha).intValue());
            }
            if (alpha instanceof Double && omega instanceof Double) {
                return Math.max(((Double)omega).doubleValue(),((Double)alpha).doubleValue());
            }
    
            break;
        case MIN:
            if (alpha instanceof Integer && omega instanceof Integer) {
                return Math.min(((Integer)omega).intValue(),((Integer)alpha).intValue());
            }
            if (alpha instanceof Double && omega instanceof Double) {
                return Math.min(((Double)omega).doubleValue(),((Double)alpha).doubleValue());
            }
    
            break;
        case MODULO:
            if (alpha instanceof Integer && omega instanceof Integer) {
                return ((Integer)omega).intValue() %  ((Integer)alpha).intValue();
            }
            break;
        case RECHTS:
            return omega;
        default:
            break;
        
        }
        throw new AplRuntimeException("Not valid combination " + alpha.getClass().getName() + " " + omega.getClass().getName() + " for operation " + art);
    }

}
