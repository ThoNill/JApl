package japl.imp.unary;

import japl.basis.AplRuntimeException;
import japl.basis.UnaryFunction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



public class StreamToArray implements UnaryFunction {

    @Override
    public Object apply(Object obj) {
        if (obj instanceof Mono) {
            return new Object[] {((Mono<?>)obj).block() };
        }
        if (obj instanceof Flux) {
            return ((Flux<?>)obj).collectList().block().toArray();
        }
        throw new AplRuntimeException("the object " + obj + " is not a Mono or Flux");
    }



}
