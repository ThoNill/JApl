package japl.imp.binary;


import org.antlr.stringtemplate.StringTemplate;

import japl.basis.BinaryFunction;
import japl.basis.Variable;
import japl.basis.VariableDictonary;
import japl.data.AttributedArray;

public class ApplyTemplate implements BinaryFunction {
    private VariableDictonary variables;
    private StringTemplate st;
    private String lastString;

    public ApplyTemplate(VariableDictonary variables) {
        super();
        this.variables = variables;
    }

    @Override
    public Object applyOne(Object alpha, Object omega) {
        if (alpha instanceof Variable) {
            return applyOne(((Variable) alpha).getValue(),omega);
        }
        String newString = alpha.toString();
        if (st == null || !newString.equals(lastString)) {
            st = new StringTemplate(newString);
            lastString = newString;
        }
        StringTemplate nst = st.getInstanceOf();
        nst.setAttribute("vars", variables);
        nst.setAttribute("omega", omega);
        if (omega instanceof AttributedArray) {
            AttributedArray array = (AttributedArray)omega;
            for(String name : array.getNames()) {
              nst.setAttribute(name,array.get(name));
            }
        }
        return nst.toString();
     }

}
