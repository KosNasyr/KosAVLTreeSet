package su;

import java.util.Iterator;
import java.util.Stack;

public class KosIterator<E> implements Iterator<E> {
    private Stack<Node<E>> stack;

    public KosIterator(Node<E> root) {
        stack = new Stack<>();
        Node<E> localNode = root;

        while (localNode != null) {
            stack.push(localNode);
            localNode = localNode.left;
        }
    }

    public boolean hasNext() {
        return !stack.empty();
    }

    public E next() {
        Node<E> node = stack.pop();
        E e = node.element;
        if (node.right != null) {
            node = node.right;
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }
        return e;
    }
}