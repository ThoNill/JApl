package japl.imp.oparators;

import java.util.function.Function;

import japl.basis.AplRuntimeException;
import japl.basis.BinaryFunction;
import japl.basis.Variable;
import reactor.core.publisher.Flux;

public class ArrayIndexApl implements BinaryFunction {

    @Override
    public Object apply(Object alpha, Object omega) {
        if (alpha instanceof Variable) {
            return apply(((Variable) alpha).getValue(),omega);
        }
        if (alpha instanceof Object[]) {
            Object[] a = (Object[]) alpha;
            if (omega instanceof Integer) {
                return a[(Integer) omega];
            }
            if (omega instanceof Object[]) {
                return getArray(a, (Object[]) omega);
            }
            if (omega instanceof Flux<?> && alpha instanceof Object[]) {
                Object[] array = (Object[]) alpha;

                Function<Object, Object> f = new Function<Object, Object>() {

                    @Override
                    public Object apply(Object t) {
                        if (t instanceof Integer) {
                            int index = Math.abs(((Integer) t).intValue()) % array.length;
                            return array[index];
                        }
                        throw new AplRuntimeException("must be a integer");
                    }
                };
                return ((Flux<?>) omega).map(f);
            }
        }
        throw new AplRuntimeException("No array for ArrayIndexApl");
    }

    private Object getArray(Object[] a, Object[] omega) {
        Object[] res = new Object[omega.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = a[(Integer) omega[i]];
        }
        return res;
    }

}
