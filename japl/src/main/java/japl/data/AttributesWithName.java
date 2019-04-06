package japl.data;


public class AttributesWithName extends Attributes {
        private String name;

        public AttributesWithName(String name,String... attributaNames) {
            super(attributaNames);
            this.name = name;
        }

        public String getName() {
            return name;
        }
        
        @Override
        protected AntiNplus1Data createAttributedDataFromArray(Object[] array) {
            return new AntiNplus1Data(array, this);
        }
            
}
