package japl.imp.binary;


import java.util.HashMap;

import org.antlr.stringtemplate.StringTemplate;

import japl.basis.AplRuntimeException;
import japl.basis.BinaryFunction;
import japl.basis.Variable;
import japl.data.AttributedArray;

public class UpdateTemplate implements BinaryFunction {
    private StringTemplate st = new StringTemplate("update from $tablename$ set $namevalues:{sub | $sub.key$ = $sub.value$}; separator=\", \"$ where $fieldname$ = $id$ ");
 
    public UpdateTemplate() {
        super();
    }

    @Override
    public Object applyOne(Object alpha, Object omega) {
        if (alpha instanceof Variable) {
            return applyOne(((Variable) alpha).getValue(),omega);
        }
        if (omega instanceof AttributedArray) {
            AttributedArray array = (AttributedArray)omega;
            String tableName = alpha.toString();
            String[] names = array.getNames();
            StringTemplate nst = st.getInstanceOf();
            nst.setAttribute("tablename", tableName);
            nst.setAttribute("fieldname",names[0]);
            nst.setAttribute("id",array.get(names[0]));
            nst.setAttribute("namevalues",createNameValuesMap(array, names));
            return nst.toString();
        }
        throw new AplRuntimeException("is not a Attributed Array");
     }

    private HashMap[] createNameValuesMap(AttributedArray array, String[] names) {
        HashMap[] namevalues = new HashMap[names.length-1];
        for(int i=1;i < names.length;i++) {
            namevalues[i-1] = new HashMap();
            namevalues[i-1].put("key",names[i]);
            namevalues[i-1].put("value","'" + array.get(names[i]).toString() + "'");
        }
        return namevalues;
    }


    @Override
    public String toString() {
        return "UpdateTemplate";
    }

}
