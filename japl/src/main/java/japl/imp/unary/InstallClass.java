package japl.imp.unary;

import japl.basis.AplRuntimeException;
import japl.basis.UnaryFunction;

public class InstallClass implements UnaryFunction{

    @Override
    public Object applyOne(Object obj) {
        try {
            return Class.forName(obj.toString());
        } catch (ClassNotFoundException e) {
            throw new AplRuntimeException("problems searching the class "+ obj,e);
        }
    }

}
