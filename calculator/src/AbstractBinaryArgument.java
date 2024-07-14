public class AbstractBinaryArgument extends Argument {
    protected Argument left;
    protected Argument right;

    public Argument getLeft() {
        return left;
    }

    public Argument getRight() {
        return right;
    }

    @Override
    public Argument clone() {
        AbstractBinaryArgument self = (AbstractBinaryArgument) super.clone();
        self.left = this.left.clone();
        self.right = this.right.clone();
        return self;
    }
}
