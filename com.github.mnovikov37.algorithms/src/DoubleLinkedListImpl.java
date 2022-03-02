import java.util.ListIterator;

public class DoubleLinkedListImpl<E> implements DoubleLinkedList<E>, Iterable<E>{
    private int size;
    private Node<E> first;
    private Node<E> last;

    @Override
    public void addFirst(E value) {
        Node<E> newNode = new Node<>(value, first, null);
        if (isEmpty()) {
            last = newNode;
        } else {
            first.prev = newNode;
        }
        first = newNode;
        size++;
    }

    @Override
    public void addLast(E value) {
        Node<E> newNode = new Node<>(value, null, last);
        if (isEmpty()) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public E removeFirst() {
        E result = null;
        if (!isEmpty()) {
            result = first.item;
            first = first.next;
            first.prev.next = null;
            first.prev = null;
            size--;
        }

        return result;
    }

    @Override
    public E removeLast() {
        E result = null;
        if (!isEmpty()) {
            result = last.item;
            last = last.prev;
            last.next.prev = null;
            last.next = null;
            size--;
        }

        return result;
    }

    @Override
    public E getFirst() {
        return first == null ? null : first.item;
    }

    @Override
    public E getLast() { return last == null ? null : last.item; }

    @Override
    public boolean remove(E value) {
        boolean result = false;

        Node<E> removedNode = jumpTo(value);
        if (removedNode != null) {
            if (removedNode.prev != null) {
                removedNode.prev.next = removedNode.next;
            }
            if (removedNode.next != null) {
                removedNode.next.prev = removedNode.prev;
            }
            removedNode.prev = removedNode.next = null;
            size--;
            result = true;
        }

        return result;
    }

    @Override
    public boolean contains(E value) {
        return jumpTo(value) != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<E> jumpTo(E value) {
        Node<E> result = first;
        while (result != null && !value.equals(result.item)) {
            result = result.next;
        }

        return result;
    }

    @Override
    public ListIterator<E> iterator() {
        return new ListIterator<>() {
            private Node<E> current = first;
            private int index = 0;
            private boolean wasAddRemove = false;
            private boolean rightBound = false;

            @Override
            public boolean hasNext() {
                return current != null && !rightBound;
            }

            @Override
            public E next() {
                E result = null;

                if (hasNext()) {
                    result = current.item;
                    if (current == last) {
                        rightBound = true;
                    } else {
                        current = current.next;
                        index++;
                    }
                }
                wasAddRemove = false;
                return result;
            }

            @Override
            public boolean hasPrevious() {
                return current != null && current.prev != null;
            }

            @Override
            public E previous() {
                E result = null;

                if (hasPrevious()) {
                    current = current.prev;
                    result = current.item;
                    rightBound = false;
                    index--;
                }
                wasAddRemove = false;
                return result;
            }

            @Override
            public int nextIndex() {
                return index;
            }

            @Override
            public int previousIndex() {
                return index - 1;
            }

            @Override
            public void remove() {
                if (wasAddRemove) {
                    throw new IllegalStateException(
                            "remove() or add() can be called only once per call to next() or previous()");
                } else {
                    wasAddRemove = true;
                    Node<E> pr = current.prev;
                    Node<E> nx = current.next;
                    current = current.prev = current.next = null;

                    if (pr == null) {
                        first = nx;
                    } else {
                        pr.next = nx;
                    }
                    if (nx == null) {
                        last = pr;
                        if (pr != null) {
                            current = pr;
                            index--;
                        }
                    } else {
                        nx.prev = pr;
                        current = nx;
                    }
                    size--;
                }
            }

            @Override
            public void set(E e) {
                if (wasAddRemove) {
                    throw new IllegalStateException(
                            "remove() or add() was called. Need to call next() or previous() before calling of set()");
                } else {
                    if (current != null) {
                        current.item = e;
                    }
                }
            }

            @Override
            public void add(E e) {
                Node<E> newNode = new Node<>(e, null, null);
                if (size == 0) {
                    current = first = last = newNode;
                } else {
                    if (current.prev != null) {
                        current.prev.next = newNode;
                    }
                    newNode.prev = current.prev;
                    newNode.next = current;
                    current.prev = newNode;
                    index++;
                }
                size++;
            }
        };
    }

    public String toString() {
        String prefix = "[";
        StringBuilder sb = new StringBuilder(prefix);
        String separator = "<>";
        Node<E> current = first;
        while (current != null) {
            sb.append(current.item.toString()).append(separator);
            current = current.next;
        }
//        for (E value : this) {
//            sb.append(value.toString()).append(separator);
//        }
        if (sb.length() > prefix.length()) {
            sb.setLength(sb.length() - separator.length());
        }
        sb.append("]");
        return sb.toString();
    }
}
