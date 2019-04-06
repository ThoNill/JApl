package japl.csv;

import java.io.IOException;
import java.util.function.Consumer;

import com.opencsv.CSVReader;

import reactor.core.publisher.SynchronousSink;

class CSVReaderSinkConsumer implements
        Consumer<SynchronousSink<String[]>> {
    private CSVReader reader;

    CSVReaderSinkConsumer(CSVReader reader) {
        super();
        this.reader = reader;
    }

    @Override
    public void accept(SynchronousSink<String[]> client) {
        String[] nextLine;
        try {
            nextLine = reader.readNext();
            if (nextLine != null) {
                client.next(nextLine);
            } else {
                client.complete();
            }
        } catch (IOException e) {
            client.error(e);
        }
    }

}
