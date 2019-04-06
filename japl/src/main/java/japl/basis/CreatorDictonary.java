package japl.basis;

import java.util.HashMap;

public class CreatorDictonary extends HashMap<String, Creator> {
    private HashMap<String, Integer> counts = new HashMap<>();

    public Creator getFunction(String name) {
        Creator v = get(name);
        if (v != null) {
            Integer n = increment(name);
            counts.put(name, n);
        }
        if (v == null) {
            throw new AplRuntimeException("UnaryFuction " + name
                    + "does not exist");
        }
        return v;
    }

    private Integer increment(String name) {
        Integer n = counts.get(name);
        n = (n == null) ? Integer.valueOf(1) : Integer
                .valueOf(n.intValue() + 1);
        return n;
    }

    public int getCount(String name) {
        Integer n = counts.get(name);
        return (n == null) ? Integer.valueOf(0) : n;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((counts == null) ? 0 : counts.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        CreatorDictonary other = (CreatorDictonary) obj;
        if (counts == null) {
            if (other.counts != null)
                return false;
        } else if (!counts.equals(other.counts))
            return false;
        return true;
    }

}
