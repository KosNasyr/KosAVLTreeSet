package su;

import lombok.Data;

@Data
public class Node<E> {

    E element;
    int height;
    Node<E> left;
    Node<E> right;

    Node(E element) {
        this(element, null, null);
    }

    Node(E element, Node<E> left, Node<E> right) {
        this.element = element;
        this.left = left;
        this.right = right;
        this.height = 0;
    }

    @Override
    public String toString() {
        return "Node = " + element + "{" +
                " left=" + left +
                ", right=" + right +
                '}';
    }
}