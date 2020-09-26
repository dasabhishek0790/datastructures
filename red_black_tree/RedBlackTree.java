import java.util.ArrayDeque;
import java.util.Queue;

public class RedBlackTree {
    private Node root;
    private Queue<Node> levelOrderTraversalQueue;

    public RedBlackTree() {

    }

    public void rotateLeft(Node root, Node node) {
        Node node_right = node.right;
        node.right = node_right.left;
        if (node.right != null) {
            node.right.parent = node;
        }

        node_right.parent = node.parent;
        if (node.parent == null) {
            root = node_right;
        } else if (node == node.parent.left) {
            node.parent.left = node_right;
        } else {
            node.parent.right = node_right;
        }

        node_right.left = node;
        node.parent = node_right;
    }

    public void rotateRight(Node root, Node node) {
        Node node_left = node.left;
        node.left = node_left.right;
        if (node.left != null) {
            node.left.parent = node;
        }

        node_left.parent = node.parent;
        if (node.parent == null) {
            root = node_left;
        } else if (node == node.parent.left) {
            node.parent.left = node_left;
        } else {
            node.parent.left = node_left;
        }

        node_left.right = node;
        node.parent = node_left;
    }

    public void levelOrderTraversal(final Node root) {
        if (root == null) {
            return;
        }

        this.levelOrderTraversalQueue = new ArrayDeque<>();
        this.levelOrderTraversalQueue.add(root);

        while (!this.levelOrderTraversalQueue.isEmpty()) {
            final Node ele = this.levelOrderTraversalQueue.remove();
            System.out.println(ele.data);
            if (ele.left != null) {
                this.levelOrderTraversalQueue.add(ele.left);
            }
            if (ele.right != null) {
                this.levelOrderTraversalQueue.add(ele.right);
            }
        }
    }

    public void prepareRedBlackFromBST(Node root, Node newNode) {
        Node parent = null; 
        Node grand_parent = null;
        while ((!newNode.equals(root)) && (!newNode.color.equals(RedBlackColor.BLACK)) && (newNode.parent.color.equals(RedBlackColor.RED))) {
            parent = newNode.parent; 
            grand_parent = newNode.parent.parent;

            if (parent == grand_parent.left) {
                Node uncle = grand_parent.right;
                if (uncle != null && uncle.color.equals(RedBlackColor.RED)) { 
                    grand_parent.color = RedBlackColor.RED;
                    parent.color = RedBlackColor.BLACK; 
                    uncle.color = RedBlackColor.BLACK; 
                    newNode = grand_parent;
                } else {
                    if (newNode == parent.right) { 
                        this.rotateLeft(root, parent); 
                        newNode = parent; 
                        parent = newNode.parent; 
                    }
                    this.rotateRight(root, grand_parent); 
                    swapColors(parent.color, grand_parent.color); 
                    newNode = parent; 
                } 
            } else {
                Node uncle = grand_parent.left; 
                if ((uncle != null) && (uncle.color.equals(RedBlackColor.RED))) { 
                    grand_parent.color = RedBlackColor.RED;
                    parent.color = RedBlackColor.BLACK; 
                    uncle.color = RedBlackColor.BLACK; 
                    newNode = grand_parent; 
                } else {
                    if (newNode == parent.left) { 
                        this.rotateRight(root, parent); 
                        newNode = parent; 
                        parent = newNode.parent; 
                    }
                    rotateLeft(root, grand_parent); 
                    swapColors(parent.color, grand_parent.color); 
                    newNode = parent; 
                }  
            }
        }
        root.color = RedBlackColor.BLACK;
    }

    private void swapColors(RedBlackColor c1, RedBlackColor c2) {
        RedBlackColor temp = c1;
        c1 = c2;
        c2 = temp;
    }

    public void insertNode(int newData) {
        Node newNode = new Node(newData);
        Node root = this.insertInBSTByKey(this.root, newNode);
        this.prepareRedBlackFromBST(root, newNode);
    }

    public void inOrderTraversal(final Node root) {
        if (root == null)
            return;
        inOrderTraversal(root.left);
        System.out.println(root.data);
        inOrderTraversal(root.right);
    }

    Node insertInBSTByKey(final Node root, final Node key) {
        if (root == null)
            return key;
        if (key.data < root.data) {
            root.left = insertInBSTByKey(root.left, key);
            root.left.parent = root;
        } else if (key.data > root.data) {
            root.right = insertInBSTByKey(root.right, key);
            root.right.parent = root;
        }
        return root;
    }

}

class Node {
    int data;
    RedBlackColor color;
    Node left, right, parent;

    public Node(final int data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.parent = null;
        this.color = RedBlackColor.RED;
    }
}