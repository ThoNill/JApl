package japl.xml;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

import org.reactivestreams.Publisher;

import reactor.core.publisher.Flux;

class XMLReaderStream implements 
    Function<XMLReader,Publisher<String[]>>,
    Callable<XMLReader>,  Consumer<XMLReader> {
    private String filename;

    XMLReaderStream(String filename) {
        super();
        this.filename = filename;
    }

    @Override
    public XMLReader call() throws Exception {
        return new XMLReader(filename);
    }
    
    @Override
    public void accept(XMLReader reader) {
        reader.close();
    }
       
    public Flux<String[]> create() {
        return Flux.using(this, this, this);
    }

    @Override
    public Publisher<String[]> apply(XMLReader reader) {
        return Flux.generate(new XMLReaderSinkConsumer(reader));
    }

}
