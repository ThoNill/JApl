package japl.imp.binary;

import java.sql.Connection;
import java.sql.Statement;

import japl.basis.AplRuntimeException;
import japl.basis.BinaryFunction;

public class ExecuteSql implements BinaryFunction {

	@Override
	public Object applyOne(Object alpha, Object omega) {
		if (alpha instanceof Connection) {
			try {
				Connection con = (Connection) alpha;
				String stmtString = omega.toString().trim();
				
				try (Statement stmt = con.createStatement()) {
					if (stmtString.toLowerCase().startsWith("select")) {
						return stmt.execute(stmtString);
					} else {
						return stmt.executeUpdate(stmtString);
					}
				} catch (Exception e) {
					throw new AplRuntimeException("problems executing " + omega.toString(), e);
				} 
			} catch (Exception e) {
				throw new AplRuntimeException("problems executing " + omega.toString(), e);
			}

		} else {
			throw new AplRuntimeException("is not a connection " + alpha);
		}
	}

}
