package japl.data;

import java.util.Comparator;

class AntiNplus1DataComparator  implements
        Comparator<AntiNplus1Data> {

    @Override
    public int compare(AntiNplus1Data o1, AntiNplus1Data o2) {
        return o1.getId().compareTo(o2.getId());
    }

}
