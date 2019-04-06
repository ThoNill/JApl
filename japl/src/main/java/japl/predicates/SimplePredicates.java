package japl.predicates;

import java.util.function.Predicate;

import japl.basis.AplRuntimeException;
import japl.basis.BinaryFunction;
import japl.basis.UnaryFunction;
import reactor.core.publisher.Flux;

public class SimplePredicates implements BinaryFunction, UnaryFunction {
    private static final String NEED_BOOLEAN_VALUE = "Need Boolean value";

    public enum Art {
        GET, LET, GT, LT, EQ, UNEQ, AND, OR, NOT
    }

    private Double small = 0.000001;

    private Art art;

    public SimplePredicates(Art art) {
        super();
        this.art = art;
    }

    @Override
    public Object apply(Object alpha, Object omega) {
        if (omega instanceof Flux<?>) {
            Flux<?> flux = (Flux<?>) omega;

            SimplePredicates pr = this;
            Predicate<Object> p = new Predicate<Object>() {
               
                public Object apply(Object omega) {
                    return pr.applyOne(alpha, omega);
                }

                @Override
                public boolean test(Object omega) {
                    return pr.applyOne(alpha, omega);
                }
            };

            return flux.filter(p);
        }
        return BinaryFunction.super.apply(alpha, omega);
    }

    @Override
    public Boolean applyOne(Object alpha, Object omega) {

        if (alpha instanceof Integer && omega instanceof Integer) {
            int a = ((Integer) alpha).intValue();
            int o = ((Integer) omega).intValue();
            return integerValues(a, o);

        }
        if (alpha instanceof Double && omega instanceof Double) {
            Double a = (Double) alpha;
            Double o = (Double) omega;
            return doubleValues(a, o);

        }
        if (alpha instanceof Boolean && omega instanceof Boolean) {
            Boolean a = (Boolean) alpha;
            Boolean o = (Boolean) omega;
            return booleanValues(a, o);

        }
        throw new AplRuntimeException(NEED_BOOLEAN_VALUE);
    }

    private Boolean booleanValues(Boolean a, Boolean o) {
        switch (art) {
        case AND:
            return a.booleanValue() && o.booleanValue();
        case NOT:
            return !o.booleanValue();
        case OR:
            return a.booleanValue() || o.booleanValue();
        default:
            throw new AplRuntimeException(NEED_BOOLEAN_VALUE);
        }
    }

    private Boolean doubleValues(Double a, Double o) {
        switch (art) {
        case EQ:
            return Math.abs(a - o) <= small;
        case GET:
            return a >= o;
        case GT:
            return a > o;
        case LET:
            return a <= o;
        case LT:
            return a < o;
        case UNEQ:
            return Math.abs(a - o) > small;
        default:
            throw new AplRuntimeException("Need Double or Integer value");

        }
    }

    private Boolean integerValues(int a, int o) {
        switch (art) {
        case EQ:
            return Math.abs(a - o) <= small;
        case GET:
            return a >= o;
        case GT:
            return a > o;
        case LET:
            return a <= o;
        case LT:
            return a < o;
        case UNEQ:
            return Math.abs(a - o) > small;
        default:
            throw new AplRuntimeException("Need Double or Integer value");

        }
    }

    @Override
    public Object applyOne(Object obj) {
        if (obj instanceof Boolean) {
            return !((Boolean) obj).booleanValue();
        }
        throw new AplRuntimeException(NEED_BOOLEAN_VALUE);
    }

}
