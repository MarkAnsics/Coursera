import java.util.NoSuchElementException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private int nodeSize;
    private Node heads, tails;

    private class Node {
        private Item item;
        private Node next, previous;
    }

    // construct an empty deque
    public Deque() {
        heads = null;
        tails = null;
        nodeSize = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return nodeSize == 0;
    }

    // return the number of items on the deque
    public int size() {
        return nodeSize;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Value is null");
        }
        Node oldHeads = heads;
        heads = new Node();
        heads.item = item;
        heads.next = oldHeads;
        if (tails == null) {
            tails = heads;
        } else {
            heads.next = oldHeads;
            oldHeads.previous = heads;
        }
        nodeSize++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Value is null");
        }
        Node oldTails = tails;
        tails = new Node();
        tails.item = item;
        tails.previous = oldTails;
        if (heads == null) {
            heads = tails;
        } else {
            tails.previous = oldTails;
            oldTails.next = tails;
        }
        nodeSize++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        Item item = heads.item;
        nodeSize--;
        if (isEmpty()) {
            tails = null;
            heads = null;
        } else {
            heads = heads.next;
            heads.previous = null;
        }
        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        Item item = tails.item;
        nodeSize--;
        if (isEmpty()) {
            tails = null;
            heads = null;
        } else {
            tails = tails.previous;
            tails.next = null;
        }
        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = heads;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
    }
}