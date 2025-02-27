/**
 * This class represents nodes in avl tree
 */
public class AvlNode <E> {
    AvlNode <E>  left;
    AvlNode <E> right;
    E key;
    int height;

    /**
     * Create a node with a key
     * Height of a node is zero when it does not have a child
     */
    public AvlNode(E key) {
        this.key = key;
        this.height = 0;
    }
}
