package japl.imp.unary;

import japl.basis.AplRuntimeException;
import japl.basis.UnaryFunction;
import reactor.core.publisher.Flux;

public class CacheStream implements UnaryFunction{

    @Override
    public Object apply(Object obj) {
        if (obj instanceof Flux<?>) {
            return ((Flux<?>)obj).cache();
        }
        throw new AplRuntimeException("Argument " + obj + " is not an Flux ");
    }

}
