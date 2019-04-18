package su;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@NoArgsConstructor
@AllArgsConstructor
public class KosAVLTreeSet<E> implements NavigableSet<E> {

    private Node<E> root;
    private Comparator<E> comp;
    private int size = 0;

    public KosAVLTreeSet(Comparator<E> comp) {
        this.comp = comp;
    }

    /**
     * Возвращает наибольший элемент в наборе,
     * но строго меньше чём заданный если такого элемента нет, то в результате будет возвращено null.
     */
    @Override
    public E lower(E e) {
        return headSet(e).last();
    }

    /**
     * Возвращает наибольший элемент в наборе,
     * но меньше чём заданный или равный ему, в случае отсутствия такого элемента будет возвращено null.
     */
    @Override
    public E floor(E e) {
        if (contains(e)) return e;
        return headSet(e, false).last();
    }

    /**
     * Возвращает ближайший элемент в наборе,
     * но который больше или равняется заданному, в случае отсутствия такого элемента будет возвращено null.
     */
    @Override
    public E ceiling(E e) {
        if (contains(e)) return e;
        return tailSet(e).first();
    }

    /**
     * Возвращает ближайший элемент в наборе, но строго больше чём заданный,
     * в случае отсутствия такого элемента будет возвращено null.
     */
    @Override
    public E higher(E e) {
        return tailSet(e, false).first();
    }

    /**
     * получает и удаляет из сета наименьший элемент,
     * или возвращает null в случае если сет пустой.
     */

    @Override
    public E pollFirst() {
        E e = findMin();
        if (e != null) {
            remove(e);
        }
        return e;
    }

    /**
     * получает и удаляет из сета наибольший элемент,
     * или возвращает null в случае если сет пустой.
     */
    @Override
    public E pollLast() {
        E e = findMax();
        if (e != null) {
            remove(e);
        }
        return e;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean contains(Object o) {
        return contains((E) o, root);
    }

    @Override
    public Iterator<E> iterator() {
        return new KosIterator<>(root);
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size()];
        Iterator<E> it = iterator();
        IntStream.range(0, array.length).forEach(i -> {
            array[i] = it.next();
        });
        return array;
    }

    @Override
    public <T> T[] toArray(T[] array) {
        int size = size();
        T[] newArr = array.length >= size ? array :
                (T[]) java.lang.reflect.Array
                        .newInstance(array.getClass().getComponentType(), size);
        Iterator<E> it = iterator();

        IntStream.range(0, size).forEach(i -> newArr[i] = (T) it.next());

        return newArr;
    }

    @Override
    public boolean add(E e) {
        boolean b = false;
        if (!contains(e)) {
            root = insert(e, root);
            size++;
            b = true;
        }
        return b;
    }

    @Override
    public boolean remove(Object o) {
        boolean b = false;
        if (contains(o)) {
            root = delete((E) o, root);
            size--;
            b = true;
        }
        return b;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return c != null && !c.isEmpty() && c.stream().anyMatch(this::contains);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c != null) c.forEach(this::add);
        return c != null && !c.isEmpty();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        List<?> toRemove = null;
        if (c != null) {
            toRemove = this.stream().filter(it -> !c.contains(it)).collect(Collectors.toList());
            toRemove.forEach(this::remove);
        }
        return toRemove != null && !toRemove.isEmpty();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        c.stream().filter(this::contains).forEach(this::remove);
        return containsAll(c);
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    @Override
    public NavigableSet<E> descendingSet() {
        KosAVLTreeSet<E> kosSet = new KosAVLTreeSet<>(this.comp);
        kosSet.addAll(this);
        reverseTree(kosSet.root);
        return kosSet;
    }

    @Override
    public Iterator<E> descendingIterator() {
        return descendingSet().iterator();
    }

    @Override
    public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        return getSubSet(fromElement, fromInclusive, toElement, toInclusive);
    }

    @Override
    public NavigableSet<E> headSet(E toElement, boolean inclusive) {
        return getHeadSet(toElement, inclusive);
    }

    @Override
    public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
        return getTailSet(fromElement, inclusive);
    }

    @Override
    public Comparator<? super E> comparator() {
        return comp;
    }

    @Override
    public SortedSet<E> subSet(E fromElement, E toElement) {
        return getSubSet(fromElement, true, toElement, false);
    }

    @Override
    public SortedSet<E> headSet(E toElement) {
        return getHeadSet(toElement, false);
    }

    @Override
    public SortedSet<E> tailSet(E fromElement) {
        return getTailSet(fromElement, true);
    }

    @Override
    public E first() {
        return findMin();
    }

    @Override
    public E last() {
        return findMax();
    }

    private int compare(E e1, E e2) {
        return (comp == null) ? ((Comparable<? super E>) e1).compareTo(e2) : comp.compare(e1, e2);
    }

    private Node<E> insert(E e, Node<E> node) {
        if (node == null) return new Node<>(e, null, null);

        int compare = compare(e, node.element);
        if (compare != 0) {
            if (compare < 0) {
                node.left = insert(e, node.left);
            } else {
                node.right = insert(e, node.right);
            }
            return balance(node);
        }
        return null;
    }

    private boolean contains(E e, Node<E> node) {
        while (node != null) {
            int compare = compare(e, node.element);
            if (compare < 0) {
                node = node.left;
            } else if (compare > 0) {
                node = node.right;
            } else {
                return true;
            }
        }
        return false;
    }

    private Node<E> delete(E e, Node<E> node) {
        if (node != null) {
            int compare = compare(e, node.element);
            if (compare < 0) {
                node.left = delete(e, node.left);
            } else if (compare > 0) {
                node.right = delete(e, node.right);
            } else if (node.left != null && node.right != null) {
                node.element = findMin(node.right).element;
                node.right = delete(node.element, node.right);
            } else {
                node = (node.left != null) ? node.left : node.right;
            }
            return balance(node);
        }
        return null;
    }

    private E findMin() {
        E min = null;
        if (!isEmpty()) min = findMin(root).element;
        return min;
    }

    private E findMax() {
        E max = null;
        if (!isEmpty()) max = findMax(root).element;
        return max;
    }

    private Node<E> balance(Node<E> node) {
        if (node != null) {
            if (height(node.left) - height(node.right) > 1) {
                if (height(node.left.left) >= height(node.left.right)) {
                    node = smallLeftRotation(node);
                } else {
                    node = bigLeftRotation(node);
                }
            } else if (height(node.right) - height(node.left) > 1) {
                if (height(node.right.right) >= height(node.right.left)) {
                    node = smallRightRotation(node);
                } else {
                    node = bigRightRotation(node);
                }
            }
            node.height = Math.max(height(node.left), height(node.right)) + 1;
        }
        return node;
    }

    private Node<E> findMin(Node<E> node) {
        if (node != null) {
            while (node.left != null) {
                node = node.left;
            }
        }
        return node;
    }

    private Node<E> findMax(Node<E> node) {
        if (node != null) {
            while (node.right != null) {
                node = node.right;
            }
        }
        return node;
    }

    private Predicate<E> checkLeft(E fromElement, boolean fromInclusive) {
        return fromInclusive
                ? e -> compare(fromElement, e) <= 0
                : e -> compare(fromElement, e) < 0;
    }

    private Predicate<E> checkRight(E toElement, boolean toInclusive) {
        return toInclusive
                ? e -> compare(toElement, e) >= 0
                : e -> compare(toElement, e) > 0;
    }


    private NavigableSet<E> getHeadSet(E toElement, boolean inclusive) {
        return this
                .stream()
                .filter(checkRight(toElement, inclusive))
                .collect(Collectors.toCollection(() -> new KosAVLTreeSet<>(this.comp)));
    }

    private NavigableSet<E> getTailSet(E fromElement, boolean inclusive) {
        return this
                .stream()
                .filter(checkLeft(fromElement, inclusive))
                .collect(Collectors.toCollection(() -> new KosAVLTreeSet<>(this.comp)));
    }

    private NavigableSet<E> getSubSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        return this
                .stream()
                .filter(checkLeft(fromElement, fromInclusive).and(checkRight(toElement, toInclusive)))
                .collect(Collectors.toCollection(() -> new KosAVLTreeSet<>(this.comp)));
    }

    @Override
    public String toString() {
        return toString(root);
    }

    private String toString(Node<E> node) {
        if (node == null) {
            return "";
        } else {
            return toString(node.left) + " " + node.element + " " + toString(node.right);
        }
    }

    private int height(Node<E> node) {
        return node == null ? -1 : node.height;
    }

    private Node<E> smallLeftRotation(Node<E> node) {
        Node<E> left = node.left;
        node.left = left.right;
        left.right = node;
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        left.height = Math.max(height(left.left), node.height) + 1;
        return left;
    }

    private Node<E> smallRightRotation(Node<E> node) {
        Node<E> right = node.right;
        node.right = right.left;
        right.left = node;
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        right.height = Math.max(height(right.right), node.height) + 1;
        return right;
    }

    private Node<E> bigLeftRotation(Node<E> node) {
        node.left = smallRightRotation(node.left);
        return smallLeftRotation(node);
    }

    private Node<E> bigRightRotation(Node<E> node) {
        node.right = smallLeftRotation(node.right);
        return smallRightRotation(node);
    }

    private void reverseTree(Node<E> node) {
        Node<E> temp = node.right;
        node.right = node.left;
        node.left = temp;

        if (node.left != null) {
            reverseTree(node.left);
        }

        if (node.right != null) {
            reverseTree(node.right);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Set))
            return false;
        Collection<?> c = (Collection<?>) o;
        if (c.size() != size())
            return false;
        else
            return containsAll(c);
    }

    private class KosIterator<E> implements Iterator<E> {
        private Stack<Node<E>> stack;
        public KosIterator(Node<E> root) {
            stack = new Stack<>();
            Node<E> localNode = root;

            while (localNode != null) {
                stack.push(localNode);
                localNode = localNode.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.empty();
        }

        @Override
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
}




