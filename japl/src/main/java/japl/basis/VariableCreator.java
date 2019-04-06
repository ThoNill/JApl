package japl.basis;


public class VariableCreator implements Creator{
    private Variable variable;

    public VariableCreator(Variable variable) {
        super();
        this.variable = variable;
    }

    @Override
    public Object create() {
        return variable.getValue();
    }
    
    @Override
    public String toString() {
        return "variable " +  variable.getName();
    }

    public Object getVariable() {
        return variable;
    }

}
