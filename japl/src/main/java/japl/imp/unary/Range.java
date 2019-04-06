package japl.imp.unary;

import japl.basis.UnaryFunction;
import reactor.core.publisher.Flux;

public class Range implements UnaryFunction{

 
    @Override
    public Object applyOne(Object obj) {
        Integer count = (Integer)obj;
        return Flux.range(1, count.intValue());
    }
    

}
