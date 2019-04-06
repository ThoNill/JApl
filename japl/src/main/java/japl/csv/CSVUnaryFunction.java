package japl.csv;

import japl.basis.UnaryFunction;

public class CSVUnaryFunction implements UnaryFunction{

    @Override
    public Object applyOne(Object obj) {
       CSVReaderStream s = new CSVReaderStream(obj.toString());
       return s.create();
    }

}
