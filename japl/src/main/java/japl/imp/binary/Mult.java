package japl.imp.binary;

import java.math.BigDecimal;
import java.math.BigInteger;

import cern.colt.matrix.tdouble.DoubleMatrix2D;
import cern.colt.matrix.tdouble.algo.DenseDoubleAlgebra;
import japl.basis.BinaryFunction;


public class Mult implements BinaryFunction
{
    @Override
    public Object applyOne(Object alpha, Object omega) {
        if (alpha instanceof Integer && omega instanceof Integer) {
            return ((Integer)alpha).intValue() * ((Integer)omega).intValue();
        }
        if (alpha instanceof Double && omega instanceof Double) {
            return ((Double)alpha).doubleValue() * ((Double)omega).doubleValue();
        }
        if (alpha instanceof Float && omega instanceof Float) {
            return((Float)alpha).floatValue() * ((Float)omega).floatValue();
        }
        if (alpha instanceof BigInteger && omega instanceof BigInteger) {
            return ((BigInteger)alpha).multiply((BigInteger)omega);
        }
        if (alpha instanceof BigDecimal && omega instanceof BigDecimal) {
            return ((BigDecimal)alpha).multiply((BigDecimal)omega);
        }
        if (alpha instanceof DoubleMatrix2D && omega instanceof DoubleMatrix2D) {
            return new DenseDoubleAlgebra().mult((DoubleMatrix2D)alpha, (DoubleMatrix2D)omega);
        }

        if (alpha instanceof Integer) {
            return repeat((omega == null) ? "" :omega.toString(),((Integer)alpha).intValue());
        }
        
        if (omega instanceof Integer) {
            return repeat((alpha == null) ? "" : alpha.toString(),((Integer)omega).intValue());
        }
        return ((alpha == null) ? "" : alpha.toString()) + ((omega == null) ? "" :omega.toString());
    }

    private Object repeat(String text, int anz) {
        StringBuilder buffer = new StringBuilder(anz * text.length());
        for(int i=0;i<anz;i++) {
            buffer.append(text);
        }
        return buffer.toString();
    }

}
