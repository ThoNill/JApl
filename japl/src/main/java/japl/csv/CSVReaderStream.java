package japl.csv;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.log4j.Logger;
import org.reactivestreams.Publisher;

import com.opencsv.CSVReader;

import reactor.core.publisher.Flux;

class CSVReaderStream implements 
    Function<CSVReader,Publisher<String[]>>,
    Callable<CSVReader>,  Consumer<CSVReader> {
    private static Logger logger = Logger.getLogger(CSVReaderStream.class);
    
    private String filename;

    CSVReaderStream(String filename) {
        super();
        this.filename = filename;
    }

    @Override
    public CSVReader call() throws Exception {
        return new CSVReader(new FileReader(filename));
    }
    
    @Override
    public void accept(CSVReader reader) {
        try {
            reader.close();
        } catch (IOException e) {
            logger.error(e);
        }
    }
       
    
    public Flux<String[]> create() {
        return Flux.using(this, this, this);
    }

    @Override
    public Publisher<String[]> apply(CSVReader reader) {
        return Flux.generate(new CSVReaderSinkConsumer(reader));
    }

}
