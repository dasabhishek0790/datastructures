import java.util.ArrayDeque;
import java.util.Queue;

import org.graalvm.compiler.graph.Node;

public class RedBlackTree {
    private Node root;
    private Queue<Node> levelOrderTraversalQueue;

    public RedBlackTree() {

    }

    public void rotateLeft(Node root, Node point) {
        Node point_right = point.right;
        point.right = point_right.left;
        if (point.right != null) {
            point.right.parent = point;
        }

        point_right.parent = point.parent;
        if (point.parent == null) {
            root = point_right;
        } else if (point == point.parent.left) {
            point.parent.left = point_right;
        } else {
            point.parent.right = point_right;
        }

        point_right.left = point;
        point.parent = point_right;
    }

    public void rotateRight(Node root, Node point) {
        Node point_left = point.left;
        point.left = point_left.right;
        if (point.left != null) {
            point.left.parent = point;
        }

        point_left.parent = point.parent;
        if (point.parent == null) {
            root = point_left;
        } else if (point == point.parent.left) {
            point.parent.left = point_left;
        } else {
            point.parent.left = point_left;
        }

        point_left.right = point;
        point.parent = point_left;
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