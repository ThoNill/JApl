package japl.csv;

import japl.basis.BinaryFunction;
import reactor.core.publisher.Flux;

public class CSVBinaryFunction implements BinaryFunction{

    @Override
    public Object apply(Object alpha, Object omega) {
        CSVWriterSubscriber subscriber = new CSVWriterSubscriber(alpha.toString());
        ((Flux<Object[]>)omega).subscribe(subscriber,t -> subscriber.close(),subscriber);
        return omega;
    }

  

}
