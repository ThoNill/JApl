package japl.imp.unary;

import japl.basis.UnaryFunction;
import japl.data.ArrayBuilder;
import reactor.core.publisher.Flux;



public class ArrayToStream implements UnaryFunction {

    @Override
    public Object apply(Object obj) {
        return Flux.fromArray(ArrayBuilder.toArray(obj));
    }



}
