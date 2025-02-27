/**
 * This class is to store the parking lots by their capacity constraints
 * It has a root node
 */

public class AVLTree<E extends Comparable<E>> {
    public AvlNode<E> root;
    int size;

    public AVLTree() {
        this.size = 0;
    }

    public void insert(E element) {
        root = insert(root, element);// the main node is root
        size +=1;
    }

    private AvlNode<E> insert(AvlNode<E> node, E element) { // function to insert new parking lot into avl tree that the root of it is the node
        if (node == null) { // if node does not exist
            return new AvlNode<>(element);
        }
        // search a place for the given capacity constraint by moving left or right child
        if (element.compareTo(node.key) < 0) {
            node.right = insert(node.right, element);
        } else if (element.compareTo(node.key) > 0) {
            node.left = insert(node.left, element);
        } else {
            return node; // Duplicate; do nothing
        }

        return balanceANode(node); // update the height of the node and balance the node
    }

    public void delete(E element) {
        root = delete(root, element); // the main node is root
        size -=1;
    }

    private AvlNode<E> delete(AvlNode<E> node, E element) {// function to delete the given parking lot from avl tree that the root of it is the node
        if (node == null) {
            return null;
        }
        // search the given capacity constraint by moving left or right child
        if (element.compareTo(node.key) < 0) {
            node.right = delete(node.right, element);
        } else if (element.compareTo(node.key) > 0 ) {
            node.left = delete(node.left, element);
        } else { // the searched node is found
            if (node.left == null || node.right == null) { // if it does not have two children
                if (node.left != null) {
                    node = node.left; // put the left child in the place of its parent
                } else {
                    node = node.right; // put the right child in the place of its parent
                }

            } else { // if it has two children
                AvlNode<E> smallestLarger = findMin(node.right); // find the min value in right subtree
                node.key = smallestLarger.key; // change the node with smallest larger node in the right subtree
                node.right = delete(node.right, smallestLarger.key); // delete the smallest larger node from right subtree
            }
        }

        // If a null child replaced its parent, return it
        if (node == null) {
            return null;
        }

        return balanceANode(node);// update the height of the node and balance the node
    }
    public E find(AvlNode<E> node, E value){ // finds the given value
        if (node == null)
            return null;
        if (node.key.compareTo(value) < 0) // go left to find the given value
            return find(node.left,value);
        else if (node.key.compareTo(value) > 0) { // go right to find the given value
            return find(node.right,value);
        }
        else return node.key; // if the given value is found
    }

    public boolean contains(E value) {
        return contains(root, value);
    }

    // Recursive helper method for contains
    private boolean contains(AvlNode<E> node, E value) {
        if (node == null) {
            return false; // if reached a null node
        }

        int cmp = value.compareTo(node.key);
        if (cmp > 0) {
            return contains(node.left, value); // Search in the left subtree
        } else if (cmp < 0) {
            return contains(node.right, value); // Search in the right subtree
        } else {
            return true; // value is found
        }
    }


    private AvlNode<E> findMin(AvlNode<E> node) {// find the minimum value in the subtree with the given node as root
        AvlNode<E> current = node;
        while (current.left != null) { // go left until the left child is null
            current = current.left;
        }
        return current;
    }


    private AvlNode<E> balanceANode(AvlNode<E> node) { // to balance a given node
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        int balance = getBalance(node);

        if (balance > 1) { // if the height of the left subtree is greater than the right
            if (getBalance(node.left) < 0) { // left-right rotation case
                node.left = rotateWithRightChild(node.left);
            }
            return rotateWithLeftChild(node); // left-left rotation case
        }

        if (balance < -1) { // if the height of the right subtree is greater than the left
            if (getBalance(node.right) > 0) { // right-left rotation case
                node.right = rotateWithLeftChild(node.right);
            }
            return rotateWithRightChild(node); // right-right rotation case
        }

        return node; // return balanced node
    }

    private AvlNode<E> rotateWithLeftChild(AvlNode<E> parent) {
        AvlNode<E> newParent = parent.left; // the newParent will be left child
        AvlNode<E> newLeft = newParent.right; // new left of the former parent

        // do rotation
        newParent.right = parent;
        parent.left = newLeft;

        // update heights
        parent.height=1 + Math.max(getHeight(parent.left), getHeight(parent.right));
        newParent.height = 1 + Math.max(getHeight(newParent.left), getHeight(newParent.right));

        return newParent;
    }

    private AvlNode<E> rotateWithRightChild(AvlNode<E> parent) {
        AvlNode<E> newParent = parent.right;// the newParent will be right child
        AvlNode<E> newRight = newParent.left; // new right of the former parent

        // do rotation
        newParent.left = parent;
        parent.right = newRight;

        // update heights
        parent.height= 1 + Math.max(getHeight(parent.left), getHeight(parent.right));
        newParent.height = 1 + Math.max(getHeight(newParent.left), getHeight(newParent.right));

        return newParent;
    }



    private int getHeight(AvlNode<E> node) {
        if (node == null)
            return 0; // null nodes have height 0
        return node.height; // return the height of a given node
    }


    private int getBalance(AvlNode<E> node) { // to calculate the balance of a given node
        if (node == null)
            return 0; // null nodes have balance 0
        return getHeight(node.left) - getHeight(node.right);
    }

    public void print(AvlNode<E> node){
        if (node == null)
            return;
        System.out.println(node.key);
        print(node.left);
        print(node.right);
    }
}


