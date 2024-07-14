public class AbstractUnaryArgument extends Argument {
    protected Argument child;

    public Argument getChild() {
        return child;
    }

    @Override
    public Argument clone() {
        AbstractUnaryArgument self = (AbstractUnaryArgument) super.clone();
        self.child = self.child.clone();
        return self;
    }
}
