package japl.random;

import cern.jet.random.tdouble.engine.DoubleMersenneTwister;
import cern.jet.random.tdouble.engine.DoubleRandomEngine;
import japl.basis.UnaryFunction;

public class RandomArray implements UnaryFunction {
    private static DoubleRandomEngine rngEngine = new DoubleMersenneTwister((int) (System.currentTimeMillis() % Integer.MAX_VALUE));
    public enum Art {
        AS_INT,AS_DOUBLE
    }
    
    private Art art;
    
   
    public RandomArray(Art art) {
        super();
        this.art = art;
    }


    @Override
    public Object applyOne(Object obj) {
        if (obj instanceof Integer) {
            int count = ((Integer)obj).intValue();
            switch (art) {
            case AS_DOUBLE:
                return createDoubleArray(count);
            case AS_INT:
                return createIntArray(count);
                default:
            }
        }
        return null;
    }


    private Object createIntArray(int count) {
        int[] array = new int[count];
        for (int i=0;i<array.length;i++) {
             synchronized (rngEngine) {
                array[i] = rngEngine.nextInt();
            }
        }
        return array;
    }

    private Object createDoubleArray(int count) {
        double[] array = new double[count];
        for (int i=0;i<array.length;i++) {
            synchronized (rngEngine) {
                array[i] = rngEngine.nextDouble();
            }
        }
        return array;
    }


    public Art getArt() {
        return art;
    }
    
}