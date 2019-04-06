package japl.data;


public class AttributedArray {
    private Object[] array;
    protected Attributes attributes;
    
    AttributedArray(Object[] array,Attributes attributes) {
        this.array = array;
        this.attributes = attributes;
    }
    
    public Object[] getArray() {
        return array;
    }
    
    public Object get(String name) {
        return attributes.get(array,name);
    }
    
    public AttributedArray set(String name,Object value) {
        attributes.set(array,name,value);
        return this;
    }

    public String[] getNames() {
        return attributes.getNames();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(int i=0;i< array.length;i++) {
            builder.append(getNames()[i]);
            builder.append(": ");
            builder.append(getArrayObject(i).toString());
            builder.append(" ");
        }
        return builder.toString();
    }

    private Object getArrayObject(int i) {
        return (array[i]==null) ? "null" : array[i];
    }


}
