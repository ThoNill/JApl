package japl.imp.unary;

import japl.basis.AplRuntimeException;
import japl.basis.UnaryFunction;
import reactor.core.publisher.Flux;

public class ZipStreams implements UnaryFunction{

    @Override
    public Object apply(Object obj) {
        if (obj instanceof Object[]) {
            Object[] objs = (Object[])obj;
            Flux<?>[] streams = new Flux[objs.length];
            for(int i=0;i < objs.length;i++) {
                streams[i] = (Flux<?>)objs[i];
            }
            return Flux.zip(x -> x,streams);
        }
        throw new AplRuntimeException("Argument " + obj + " is not an array of fluxes ");
    }

}
