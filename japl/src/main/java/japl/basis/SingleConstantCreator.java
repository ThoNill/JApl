package japl.basis;


public class SingleConstantCreator implements Creator{
    private Object value;

    public SingleConstantCreator() {
        super();
    }
    
    public void add(Object value) {
        this.value = value;
    }

    @Override
    public Object create() {
        return value;
    }
    
    @Override
    public String toString() {
        return (value==null) ? "null" : value.toString();
    }

}
