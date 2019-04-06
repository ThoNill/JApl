package japl.sql;

import java.util.function.Consumer;

import reactor.core.publisher.SynchronousSink;

class ResultSetSinkConsumer implements Consumer<SynchronousSink<Object[]>> {
    private ResultSetEnvelope reader;
    
    ResultSetSinkConsumer(ResultSetEnvelope reader) {
        super();
        this.reader = reader;
    }

    @Override
    public void accept(SynchronousSink<Object[]> client) {
        try {
            if (reader.next()) {
                client.next(reader.getArray());
            } else {
                client.complete();
            }
        } catch (Exception e) {
            client.error(e);
        }
      }

}
