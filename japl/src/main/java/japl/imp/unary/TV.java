package japl.imp.unary;

import japl.basis.UnaryFunction;
import reactor.core.publisher.Flux;

public class TV implements UnaryFunction{

    @Override
    public Object applyOne(Object obj) {
        if (obj instanceof Flux<?>) {
            ((Flux<?>)obj).subscribe(s -> { out(s);System.out.println();} );
        } else {
            System.out.println((obj == null) ? " null" : obj.toString());
        }
        
        return obj;
    }
    
    @Override
    public Object apply(Object obj){
        return applyOne(obj);
    }
 

    private void out(Object obj) {
        if (obj instanceof Object[]) {
            outArray((Object[])obj);
        } else {
            System.out.print((obj == null) ? "null" : obj.toString());
        }
    }

    private void outArray(Object[] objs) {
      System.out.print("[");
      boolean mitKomma=false;
      for(Object o : objs) {
          if (mitKomma) {
              out(", ");
          }
          mitKomma=true;
          out(o);
      }
      System.out.print("]");
    }

}
