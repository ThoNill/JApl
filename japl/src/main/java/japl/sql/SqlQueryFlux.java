package japl.sql;

import java.sql.Connection;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.log4j.Logger;

import reactor.core.publisher.Flux;

public class SqlQueryFlux implements
        Function<ResultSetEnvelope, Flux<Object[]>>,
        Callable<ResultSetEnvelope>, Consumer<ResultSetEnvelope> {
    private static Logger logger = Logger.getLogger(SqlQueryFlux.class);
    
    private Connection con;
    private String stmtString;

    private SqlQueryFlux(Connection con, String stmtString) {
        super();
        this.con = con;
        this.stmtString = stmtString;
    }

    @Override
    public void accept(ResultSetEnvelope t) {
        try {
            t.close();
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public Flux<Object[]> apply(ResultSetEnvelope result) {
          return Flux.generate(new ResultSetSinkConsumer(result));
    }

    @Override
    public ResultSetEnvelope call() throws Exception {
        return new ResultSetEnvelope(con,stmtString);
    }
    
    public static Flux using(Connection con,String stmtString) {
        SqlQueryFlux stream = new SqlQueryFlux(con, stmtString);
        return Flux.using(stream, stream, stream);
    }
    
}