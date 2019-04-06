package japl.xml;

import japl.basis.UnaryFunction;

public class XMLUnaryFunction implements UnaryFunction{

    @Override
    public Object applyOne(Object obj) {
       XMLReaderStream s = new XMLReaderStream(obj.toString());
       return s.create();
    }

}
