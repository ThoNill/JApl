package japl.imp.binary;

import java.math.BigDecimal;
import java.math.BigInteger;

import cern.colt.matrix.tdouble.DoubleMatrix1D;
import cern.colt.matrix.tdouble.DoubleMatrix2D;
import cern.colt.matrix.tdouble.impl.DenseColumnDoubleMatrix2D;
import cern.colt.matrix.tdouble.impl.DenseDoubleMatrix1D;
import japl.basis.BinaryFunction;

public class Sum implements BinaryFunction {

    @Override
    public Object applyOne(Object alpha, Object omega) {
        if (alpha instanceof Integer && omega instanceof Integer) {
            return Integer.sum((Integer) alpha, (Integer) omega);
        }
        if (alpha instanceof Double && omega instanceof Double) {
            return Double.sum((Double) alpha, (Double) omega);
        }
        if (alpha instanceof Float && omega instanceof Float) {
            return Float.sum((Float) alpha, (Float) omega);
        }
        if (alpha instanceof BigInteger && omega instanceof BigInteger) {
            return ((BigInteger) alpha).add((BigInteger) omega);
        }
        if (alpha instanceof BigDecimal && omega instanceof BigDecimal) {
            return ((BigDecimal) alpha).add((BigDecimal) omega);
        }
        if (alpha instanceof DoubleMatrix2D && omega instanceof DoubleMatrix2D) {
            return addMatrix((DoubleMatrix2D)alpha, (DoubleMatrix2D)omega);
        }
        if (alpha instanceof DoubleMatrix1D && omega instanceof DoubleMatrix1D) {
            return addMatrix((DoubleMatrix1D)alpha, (DoubleMatrix1D)omega);
        }

        return ((alpha == null) ? "" : alpha.toString()) + ((omega == null) ? "" :omega.toString());
    }

    private Object addMatrix(DoubleMatrix2D alpha, DoubleMatrix2D omega) {
        double[][] a = alpha.toArray();
        double[][] o = omega.toArray();
        double[][] r = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            double[] a1 = a[i];
            double[] o1 = o[i];
            double[] r1 = r[i];
            for (int j = 0; j < a[0].length; j++) {
                r1[j] = a1[j] + o1[j];
            }
        }
        return new DenseColumnDoubleMatrix2D(r);
    }


    private Object addMatrix(DoubleMatrix1D alpha, DoubleMatrix1D omega) {
        double[] a = alpha.toArray();
        double[] o = omega.toArray();
        double[] r = new double[a.length];
            for (int j = 0; j < a.length; j++) {
                r[j] = a[j] + o[j];
            }
        return new DenseDoubleMatrix1D(r);
    }

}
