package japl.imp.oparators;

import japl.basis.AplRuntimeException;
import japl.basis.BinaryFunction;
import japl.basis.Variable;
import japl.basis.VariableDictonary;

public class Setter  implements BinaryFunction{
    private VariableDictonary variables;
    
    public Setter(VariableDictonary variables) {
        super();
        this.variables = variables;
    }

    @Override
    public Object apply(Object alpha, Object omega) {
        if (alpha instanceof Variable) {
            ((Variable)alpha).setValue(omega);
        } else if (alpha instanceof String) {
            Variable v = variables.getVariable(alpha.toString());
            v.setValue(omega);
        } else {
            throw new AplRuntimeException("Object is not a Variable " + alpha);
        }
        return null;
    }

  
}
