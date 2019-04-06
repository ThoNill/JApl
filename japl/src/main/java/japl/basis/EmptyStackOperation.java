package japl.basis;

public class EmptyStackOperation implements StackOperation {

    /*
     * This operation do nothing
     * @see japl.basis.StackOperation#perform(japl.basis.WorkingStack)
     */
    @Override
    public void perform(WorkingStack stack) {
        // Empty action
    }
    
    @Override
    public String toString() {
        return "Empty Operation";
    }

}
