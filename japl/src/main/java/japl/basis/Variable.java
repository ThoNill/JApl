package japl.basis;

public class Variable {
    private String name;
    private Object value;

    Variable(String name) {
        super();
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
    
}
