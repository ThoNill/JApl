package japl.data;

class AntiNplus1Data extends AttributedArray  {

    AntiNplus1Data(Object[] array, AttributesWithName attributes) {
        super(array, attributes);
    }

    public String getName() {
        return ((AttributesWithName) attributes).getName();
    }
    
    public String getId() {
        return getArray()[0].toString();
    }


}
