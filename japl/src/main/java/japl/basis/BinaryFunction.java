package japl.basis;

import reactor.core.publisher.Flux;

public interface BinaryFunction {
    
    default Object applyOne(Object alpha,Object omega) {
       throw new AplRuntimeException("allyOne is not implemented");
    }
  
    
    default boolean isOperator() {
        return false;
    }
    
    default Object apply(Object alpha,Object omega) {
        if (alpha instanceof Object[] && omega instanceof Object[] && !isOperator()) {
            return applySideBySide((Object[])alpha,(Object[])omega);
        }
        if (alpha instanceof Object[]) {
            return applyAlphaArray((Object[])alpha,omega);
        }
        if (omega instanceof Object[]) {
            return applyOmegaArray(alpha,(Object[])omega);
        }
        if (omega instanceof Flux<?>) {
            Flux<?> flux = (Flux<?>)omega;
            return flux.map(new BinaryFunctionEnvelop(alpha,this));
        }
        return applyOne(alpha,omega);
    }
    
    default Object applyOmegaArray(Object alpha,Object[] omegas) {
        Object[] res = new Object[omegas.length];
        for(int i=0;i<res.length;i++) {
            res[i] = apply(alpha,omegas[i]);
        }
        return res;
    }
    
    default Object applyAlphaArray(Object[] alphas,Object omega) {
        Object[] res = new Object[alphas.length];
        for(int i=0;i<res.length;i++) {
            res[i] = apply(alphas[i],omega);
        }
        return res;
    }

    default Object applySideBySide(Object[] alphas,Object[] omegas) {
        if (alphas.length != omegas.length) {
            throw new AplRuntimeException("Array length must match ");
        }
        Object[] res = new Object[alphas.length];
        for(int i=0;i<res.length;i++) {
            res[i] = apply(alphas[i],omegas[i]);
        }
        return res;
    }

  
}
