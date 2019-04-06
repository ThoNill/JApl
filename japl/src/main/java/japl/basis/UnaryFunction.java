package japl.basis;

import java.util.function.Function;

import reactor.core.publisher.Flux;

public interface UnaryFunction extends Function<Object,Object>{
    

    default Object applyOne(Object obj) {
       throw new AplRuntimeException("allyOne is not implemented");
    }
    
    @Override
    default Object apply(Object obj){
        if (obj instanceof Object[]) {
            return applyArray((Object[])obj);
        }
        if (obj instanceof Flux<?>) {
            Flux<?> flux = (Flux<?>)obj;
            return flux.map(this);
        }
     
        return applyOne(obj);
    }
 
        
    default Object applyArray(Object[] objs) {
        Object[] res = new Object[objs.length];
        for(int i=0;i<res.length;i++) {
            res[i] = apply(objs[i]);
        }
        return res;
    }

}
