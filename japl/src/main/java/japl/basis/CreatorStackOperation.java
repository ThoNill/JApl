package japl.basis;

public class CreatorStackOperation implements StackOperation{
    private Creator creator;
    
    public CreatorStackOperation(Creator f) {
        super();
        this.creator = f;
    }

    @Override
    public void perform(WorkingStack stack) {
        stack.push(creator.create());

    }

    public Creator getCreator() {
        return creator;
    }
    
    @Override
    public String toString() {
        return "Creator: " + creator.toString();
    }

}
