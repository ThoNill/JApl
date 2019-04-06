package japl.basis;

import java.util.ArrayList;
import java.util.List;

public class ConstantCreator implements Creator{
    private List<Object> values = new ArrayList<>();

    public ConstantCreator() {
        super();
    }
    
    public void add(Object value) {
        values.add(value);
    }

    @Override
    public Object create() {
        if (values.size()==1) {
            return values.get(0);
        }
        return values.toArray();
    }
    
    @Override
    public String toString() {
        if (values.isEmpty()) {
            return "empty";
        }
        if (values.size()==1) {
            return values.get(0).toString();
        }
        StringBuilder buffer = new StringBuilder();
        boolean komma=false;
        for(Object obj : values) {
            if (komma) {
                buffer.append(",");
            }
            buffer.append((obj==null) ? "null" :obj.toString());
            komma = true;
        }
        return buffer.toString();
    }

}
