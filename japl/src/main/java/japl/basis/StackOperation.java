package japl.basis;

@FunctionalInterface 
public interface StackOperation {
    void perform(WorkingStack stack);
}
