package japl.imp.unary;

import java.sql.DriverManager;

import japl.basis.AplRuntimeException;
import japl.basis.UnaryFunction;

public class ConnectToDatabase implements UnaryFunction{
    

    @Override
    public Object applyOne(Object obj) {
        try {
            return DriverManager.getConnection(obj.toString());
        } catch (Exception e) {
            throw new AplRuntimeException("problems establishing connection "+ obj,e);
        }
    }

}
