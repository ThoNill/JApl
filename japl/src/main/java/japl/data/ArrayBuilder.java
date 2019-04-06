package japl.data;

import org.apache.commons.lang.ArrayUtils;

import japl.basis.AplRuntimeException;

public class ArrayBuilder {
    
    private static final String DIMENSIONS_ARE_TO_BIG = "Dimensions are to big";

    private ArrayBuilder() {
        super();
    }

    public static Object[] toArray(Object obj) {
        
        if (obj instanceof byte[]) {
            return ArrayUtils.toObject((byte[]) obj);
        }
        if (obj instanceof int[]) {
            return ArrayUtils.toObject((int[]) obj);
        }
        if (obj instanceof long[]) {
            return ArrayUtils.toObject((long[]) obj);
        }
        if (obj instanceof double[]) {
            return ArrayUtils.toObject((double[]) obj);
        }
        if (obj instanceof float[]) {
            return ArrayUtils.toObject((float[]) obj);
        }
        if (obj instanceof Object[]) {
            return (Object[]) obj;

        }
        return new Object[] {obj};
    }

    public static Object createDoubleArray(int[] dim) {
        if (dim.length == 0) {
            return new double[0];
        }
        if (dim.length == 1) {
            return new double[dim[0]];
        }
        if (dim.length == 2) {
            return new double[dim[0]][dim[1]];
        }
        if (dim.length == 3) {
            return new double[dim[0]][dim[1]][dim[2]];
        }
        if (dim.length == 4) {
            return new double[dim[0]][dim[1]][dim[2]][dim[3]];
        }
        if (dim.length == 5) {
            return new double[dim[0]][dim[1]][dim[2]][dim[3]][dim[4]];
        }
        if (dim.length == 6) {
            return new double[dim[0]][dim[1]][dim[2]][dim[3]][dim[4]][dim[5]];
        }
        throw new AplRuntimeException(DIMENSIONS_ARE_TO_BIG);
    }

    public static Object createIntArray(int[] dim) {
        if (dim.length == 0) {
            return new int[0];
        }
        if (dim.length == 1) {
            return new int[dim[0]];
        }
        if (dim.length == 2) {
            return new int[dim[0]][dim[1]];
        }
        if (dim.length == 3) {
            return new int[dim[0]][dim[1]][dim[2]];
        }
        if (dim.length == 4) {
            return new int[dim[0]][dim[1]][dim[2]][dim[3]];
        }
        if (dim.length == 5) {
            return new int[dim[0]][dim[1]][dim[2]][dim[3]][dim[4]];
        }
        if (dim.length == 6) {
            return new int[dim[0]][dim[1]][dim[2]][dim[3]][dim[4]][dim[5]];
        }
        throw new AplRuntimeException(DIMENSIONS_ARE_TO_BIG);
    }

    public static Object createLongArray(int[] dim) {
        if (dim.length == 0) {
            return new long[0];
        }
        if (dim.length == 1) {
            return new long[dim[0]];
        }
        if (dim.length == 2) {
            return new long[dim[0]][dim[1]];
        }
        if (dim.length == 3) {
            return new long[dim[0]][dim[1]][dim[2]];
        }
        if (dim.length == 4) {
            return new long[dim[0]][dim[1]][dim[2]][dim[3]];
        }
        if (dim.length == 5) {
            return new long[dim[0]][dim[1]][dim[2]][dim[3]][dim[4]];
        }
        if (dim.length == 6) {
            return new long[dim[0]][dim[1]][dim[2]][dim[3]][dim[4]][dim[5]];
        }
        throw new AplRuntimeException(DIMENSIONS_ARE_TO_BIG);
    }

    public static Object createObjectArray(int[] dim) {
        if (dim.length == 0) {
            return new Object[0];
        }
        if (dim.length == 1) {
            return new Object[dim[0]];
        }
        if (dim.length == 2) {
            return new Object[dim[0]][dim[1]];
        }
        if (dim.length == 3) {
            return new Object[dim[0]][dim[1]][dim[2]];
        }
        if (dim.length == 4) {
            return new Object[dim[0]][dim[1]][dim[2]][dim[3]];
        }
        if (dim.length == 5) {
            return new Object[dim[0]][dim[1]][dim[2]][dim[3]][dim[4]];
        }
        if (dim.length == 6) {
            return new Object[dim[0]][dim[1]][dim[2]][dim[3]][dim[4]][dim[5]];
        }
        throw new AplRuntimeException(DIMENSIONS_ARE_TO_BIG);
    }

    public static void initDoubleArray(Object a, double[] values) {
        int dimPos = 0;
        int[] dim = getDim(a);
        int[] valuePos = new int[dim.length];
        initDoubleArray(a, dimPos, dim, valuePos, values);
    }

    private static void initDoubleArray(Object a, int dimPos, int[] dim,
            int[] valuePos, double[] values) {
        valuePos[dimPos] = 0;
        int[] mdim = new int[dim.length];
        mdim[dim.length - 1] = 1;
        for (int i = dim.length - 2; i >= 0; i--) {
            mdim[i] = dim[i + 1] * mdim[i + 1];
        }
        if (dimPos == dim.length - 1) {
            for (int i = 0; i < dim[dimPos]; i++) {
                double v = values[getIndex(mdim, valuePos)];
                setValue(a, 0, valuePos, v);
                valuePos[dimPos]++;
            }
        } else {
            for (int i = 0; i < dim[dimPos]; i++) {
                initDoubleArray(a, dimPos + 1, dim, valuePos, values);
                valuePos[dimPos]++;
            }
        }
    }

    private static void setValue(Object a, int dimPos, int[] valuePos, double v) {
        if (a instanceof double[]) {
            ((double[]) a)[valuePos[dimPos]] = v;
        } else {
            if (a instanceof Object[]) {
                Object[] obj = (Object[]) a;
                setValue(obj[valuePos[dimPos]], dimPos + 1, valuePos, v);
            }
        }
    }

    private static int getIndex(int[] mdim, int[] valuePos) {
        int index = 0;
        for (int i = 0; i < mdim.length; i++) {
            index += (mdim[i] * valuePos[i]);
        }
        return index;
    }

    private static int getDepth(Object a) {
        Object a0 = a;
        int d = 1;
        while (a0 instanceof Object[]) {
            Object[] aa = (Object[]) a0;
            a0 = aa[0];
            d++;
        }
        return d;
    }

    private static int[] getDim(Object a) {
        Object a0 = a;
        int[] d = new int[getDepth(a)];
        int pos = 0;
        while (a0 instanceof Object[]) {
            Object[] aa = (Object[]) a0;
            d[pos] = aa.length;
            a0 = aa[0];
            pos++;
        }
        if (a instanceof double[]) {
            Object[] aa = (Object[]) a;
            d[pos] = aa.length;
        }
        return d;
    }
}
