package japl.imp.binary;


import org.antlr.stringtemplate.StringTemplate;

import japl.basis.AplRuntimeException;
import japl.basis.BinaryFunction;
import japl.basis.Variable;
import japl.data.AttributedArray;

public class InsertTemplate implements BinaryFunction {
    private StringTemplate st;
 
    public InsertTemplate() {
        super();
        st = new StringTemplate("insert into $tablename$ ( $names:{sub | $sub$}; separator=\", \"$ ) values ( $values:{sub | $sub$}; separator=\", \"$ ) ");
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
            String[] values = new String[names.length];
            for(int i=0;i < names.length;i++) {
              values[i] = "'" + array.get(names[i]).toString() + "'";
            }
            StringTemplate nst = st.getInstanceOf();
            nst.setAttribute("tablename", tableName);
            nst.setAttribute("names",names);
            nst.setAttribute("values",values);
            return nst.toString();
        }
        throw new AplRuntimeException("is not a Attributed Array");
     }

    @Override
    public String toString() {
        return "InsertTemplate";
    }

}
