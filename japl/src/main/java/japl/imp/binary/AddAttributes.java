package japl.imp.binary;

import java.util.Arrays;

import japl.basis.BinaryFunction;
import japl.data.Attributes;
import japl.data.AttributesWithName;
import reactor.core.publisher.Flux;

public class AddAttributes implements BinaryFunction {

    @Override
    public Object apply(Object alpha, Object omega) {
        if (alpha instanceof String) {
            return apply(new Object[] { alpha },omega);
        }
        if (alpha instanceof Object[] && omega instanceof Flux<?>) {
            Attributes a = createAttributes(alpha);
            Flux<?> flux = (Flux<?>) omega;
            return flux.map(x -> a.createAttributedData(x));
        }
        return null;
    }

    private Attributes createAttributes(Object alpha) {
        Object[] array = (Object[])alpha;
        if (array.length == 2 && array[1] instanceof Object[]) {
            return createNamedAttributes(array);
        } else {
            return createSimpleAttributes(array);
        }
    }

    private Attributes createNamedAttributes(Object[] array) {
        String name = array[0].toString();
        String[] attributes = convertToStringArray((Object[])array[1]);
        return new AttributesWithName(name,attributes);
    }

    private Attributes createSimpleAttributes(Object[] array) {
        String[] attributes = convertToStringArray(array);
        return new Attributes(attributes);
    }

    private String[] convertToStringArray(Object[] array) {
        Arrays.stream(array).map(x -> x.toString()).toArray();
        String[] attributes = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            attributes[i] = array[i].toString();
        }
        return attributes;
    }

}
