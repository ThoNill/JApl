package japl.basis;

import java.util.HashMap;

public class BinaryFunctionDictonary extends HashMap<String,BinaryFunction>{

    public BinaryFunction getFunction(String name) {
           BinaryFunction v = get(name);
           if (v==null) {
               throw new AplRuntimeException("UnaryFuction " + name + "does not exist");
           }
           return v;
    }

    
}
