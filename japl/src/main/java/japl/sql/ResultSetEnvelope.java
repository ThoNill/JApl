package japl.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class ResultSetEnvelope implements AutoCloseable {
    private Statement stmt;
    private ResultSet result;

    ResultSetEnvelope(Connection con, String stmtString)
            throws SQLException {
        super();
        this.stmt = con.createStatement();
        this.result = stmt.executeQuery(stmtString);
    }

    public boolean next() throws SQLException {
        return result.next();
    }

    @Override
    public void close() throws Exception {
        if (!result.isClosed()) {
            result.close();
        }
        if (stmt.isClosed()) {
            stmt.close();
        }
    }

    private Object getObject(int columnIndex) throws SQLException {
        return result.getObject(columnIndex);
    }

    public Object getObject(String columnLabel) throws SQLException {
        return result.getObject(columnLabel);
    }

    public int getColumnCount() throws SQLException {
        return result.getMetaData().getColumnCount();
    }

    public Object[] getArray() throws SQLException {
        int size = getColumnCount();
        Object[] array = new Object[size];
        for (int i = 1; i <= size; i++) {
            array[i - 1] = getObject(i);
        }
        return array;
    }
}
