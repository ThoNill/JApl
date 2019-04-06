package japl.imp.binary;


import org.antlr.stringtemplate.StringTemplate;

import japl.basis.AplRuntimeException;
import japl.basis.BinaryFunction;
import japl.basis.Variable;
import japl.data.AttributedArray;

public class DeleteTemplate implements BinaryFunction {
    private StringTemplate st = new StringTemplate("delete from $tablename$ where $fieldname$ = $id$ ");
 
    public DeleteTemplate() {
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
            return nst.toString();
        }
        throw new AplRuntimeException("is not a Attributed Array");
     }


    @Override
    public String toString() {
        return "DeleteTemplate";
    }

}
