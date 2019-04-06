package japl.basis;

import java.util.function.Function;

class BinaryFunctionEnvelop implements Function<Object,Object>{
        private Object alpha;
        private BinaryFunction binary;

        BinaryFunctionEnvelop(Object alpha, BinaryFunction binary) {
            super();
            this.alpha = alpha;
            this.binary = binary;
        }

        @Override
        public Object apply(Object omega) {
            return binary.applyOne(alpha, omega);
        }
        
}
