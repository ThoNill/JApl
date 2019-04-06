package japl.data;

import cern.colt.matrix.tdouble.DoubleMatrix2D;
import cern.colt.matrix.tdouble.algo.DenseDoubleAlgebra;
import japl.basis.AplRuntimeException;
import japl.basis.UnaryFunction;

public class MatrixUnary implements UnaryFunction{
    public enum Art { DET, TRACE,INV,TRANS,VERTICAL,HORIZONTAL }
    
    private Art art;

    public MatrixUnary(Art art) {
        super();
        this.art = art;
    }

    @Override
    public Object applyOne(Object obj) {
       if (obj instanceof DoubleMatrix2D) {
           DenseDoubleAlgebra a = new DenseDoubleAlgebra();
           DoubleMatrix2D matrix = (DoubleMatrix2D)obj;
           switch (art) {
        case HORIZONTAL:
            return mirrorHorizontal(a, matrix);
        case DET: 
            return a.det(matrix);
        case TRACE: 
            return a.trace(matrix);
        case INV:
            return a.inverse(matrix);
        case TRANS:
            return a.transpose(matrix).copy();
        case VERTICAL:
            return mirrorVertical(a, matrix);
        default:
            break;
               
           }
       }
       throw new AplRuntimeException("Object is not a DoubleMatrix2D");
    }

    private Object mirrorHorizontal(DenseDoubleAlgebra a, DoubleMatrix2D matrix) {
        int c = matrix.columns();
        int[] ind = new int[c];
        int[] work = new int[c];
        for(int i=0;i<c; i++) {
            ind[i] = c - i;
        }
        
        return a.permuteColumns(matrix,ind,work);
    }


    private Object mirrorVertical(DenseDoubleAlgebra a, DoubleMatrix2D matrix) {
        int r = matrix.rows();
        int[] ind = new int[r];
        int[] work = new int[r];
        for(int i=0;i<r; i++) {
            ind[i] = r - i;
        }
        
        return a.permuteRows(matrix,ind,work);
    }

}
