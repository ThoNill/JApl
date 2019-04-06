package japl.random;

import java.util.concurrent.Callable;
import java.util.function.BiFunction;

import cern.jet.random.tdouble.engine.DoubleMersenneTwister;
import cern.jet.random.tdouble.engine.DoubleRandomEngine;
import japl.basis.UnaryFunction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

public class RandomStream implements UnaryFunction,
        Callable<DoubleRandomEngine> {

    public enum Art {
        AS_INT, AS_DOUBLE
    }

    private Art art;

    public RandomStream(Art art) {
        super();
        this.art = art;
    }

    public Art getArt() {
        return art;
    }

    @Override
    public Object apply(Object t) {
        int count = 0;
        if (t instanceof Integer) {
            count = ((Integer) t).intValue();
        }
        return Flux.generate(this, createMapFunction(count));
    }

    private BiFunction<DoubleRandomEngine, SynchronousSink<Object>, DoubleRandomEngine> createMapFunction(
            int count) {
        boolean withLimit = count > 0;
        return new BiFunction<DoubleRandomEngine, SynchronousSink<Object>, DoubleRandomEngine>() {
            int c = count;

            @Override
            public DoubleRandomEngine apply(DoubleRandomEngine t,
                    SynchronousSink<Object> u) {

                switch (art) {
                case AS_DOUBLE:
                    u.next(t.nextDouble());
                    break;
                case AS_INT:
                    u.next(t.nextInt());
                    break;
                default:
                    break;
                }
                if (withLimit) {
                    c--;
                    if (c <= 0) {
                        u.complete();
                    }
                }

                return t;
            }
        };
    }

    @Override
    public DoubleRandomEngine call() throws Exception {
        return new DoubleMersenneTwister(
                (int) (System.currentTimeMillis() % Integer.MAX_VALUE));
    }

}