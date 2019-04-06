package japl.data;

import japl.basis.AplRuntimeException;

public class Attributes {
    private String[] names;

    public Attributes(String... names) {
        this.names = names;
    }

    public String[] getNames() {
        return names;
    }

    private int toPosition(String name) {
        for (int i = 0; i < names.length; i++) {
            if (names[i].equals(name)) {
                return i;
            }
        }
        throw new AplRuntimeException("Attribut + " + name + " not found");
    }

    Object get(Object[] array, String name) {
        return array[toPosition(name)];
    }

    void set(Object[] array, String name, Object value) {
        array[toPosition(name)] = value;
    }

    public AttributedArray createAttributedData(Object obj) {
        if (obj instanceof AttributedArray) {
            AttributedArray a = (AttributedArray) obj;
            Object[] projArray = new Object[names.length];
            for (int i = 0; i < names.length; i++) {
                projArray[i] = a.get(names[i]);
            }
            return createAttributedDataFromArray(projArray);
        }
        if (obj instanceof Object[]) {
            return createAttributedDataFromArray((Object[]) obj);
        } else {
            return createAttributedDataFromArray(new Object[] { obj });
        }
    }

    protected AttributedArray createAttributedDataFromArray(Object[] array) {
        return new AttributedArray(array, this);
    }
}
