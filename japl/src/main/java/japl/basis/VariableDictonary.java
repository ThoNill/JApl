package japl.basis;

import java.util.HashMap;

public class VariableDictonary extends HashMap<String,Variable>{

    public Variable getVariable(String name) {
           Variable v = get(name);
           if (v==null) {
               v = new Variable(name);
               put(name,v);
           }
           return v;
    }

    
}
