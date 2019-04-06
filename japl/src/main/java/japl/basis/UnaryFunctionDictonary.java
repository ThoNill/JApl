package japl.basis;

import java.util.HashMap;

public class UnaryFunctionDictonary extends HashMap<String,UnaryFunction>{

    public UnaryFunction getFunction(String name) {
           UnaryFunction v = get(name);
           if (v==null) {
               throw new AplRuntimeException("UnaryFuction " + name + "does not exist");
           }
           return v;
    }
          
}
