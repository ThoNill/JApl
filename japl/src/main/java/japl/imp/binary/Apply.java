package japl.imp.binary;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.beanutils.MethodUtils;

import japl.basis.AplRuntimeException;
import japl.basis.BinaryFunction;
import japl.basis.StackOperation;
import japl.basis.UnaryFunction;
import japl.basis.UserDefinedFunction;
import japl.basis.Variable;
import japl.basis.WorkingStack;
import japl.sql.SqlQueryFlux;

public class Apply implements BinaryFunction {

	private WorkingStack stack;

	public Apply(WorkingStack stack) {
		this.stack = stack;
	}

	@Override
	public Object apply(Object alpha, Object omega) {
		if (alpha instanceof Variable) {
			return apply(((Variable) alpha).getValue(), omega);
		}
		if (alpha instanceof Connection) {
			try {
				Connection con = (Connection) alpha;
				String stmtString = omega.toString().trim();
				if (stmtString.toLowerCase().startsWith("select")) {
					return SqlQueryFlux.using(con, stmtString);
				} else {
					return executeUpdate(con, stmtString);
				}
			} catch (Exception e) {
				throw new AplRuntimeException("problems executing " + omega.toString(), e);
			}
		}
		if (alpha instanceof UserDefinedFunction) {
			try {
				UserDefinedFunction uf = (UserDefinedFunction) alpha;
				if (uf.isUnary()) {
					return uf.apply(omega);
				} else {
					StackOperation op = stack.popOperation();
					stack.apply(op);
					Object a = stack.pop();
					return uf.apply(a, omega);
				}
			} catch (Exception e) {
				throw new AplRuntimeException("Problems with binary function and parameter " + omega, e);
			}
		}

		if (alpha instanceof BinaryFunction) {
			try {
				StackOperation op = stack.popOperation();
				stack.apply(op);
				return ((BinaryFunction) alpha).apply(stack.pop(), omega);
			} catch (Exception e) {
				throw new AplRuntimeException("Problems with binary function and parameter " + omega, e);
			}
		}
		if (alpha instanceof UnaryFunction) {
			try {
				return ((UnaryFunction) alpha).apply(omega);
			} catch (Exception e) {
				throw new AplRuntimeException("Problems with unary function and parameter " + omega, e);
			}
		}
		try {
			return MethodUtils.invokeMethod(alpha, omega.toString(), null);
		} catch (Exception e) {
			throw new AplRuntimeException("Problems with " + alpha.getClass().getName() + " and method " + omega, e);
		}
	}

	private int executeUpdate(Connection con, String stmtString) throws SQLException {
		try (Statement stmt = con.createStatement()) {
			return stmt.executeUpdate(stmtString);
		}
	}

}
