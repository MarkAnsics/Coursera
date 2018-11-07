import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int arraySize;
    private Item[] array;

    // construct an empty randomized queue
    public RandomizedQueue() {
        array = (Item[]) new Object[2];
        arraySize = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return arraySize == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return arraySize;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        if (arraySize == array.length) {
            resize(2 * array.length);
        }
        if (arraySize == 0) {
            array[arraySize++] = item;
            return;
        }
        int randomIndex = StdRandom.uniform(arraySize);
        Item temp = array[randomIndex];
        array[randomIndex] = item;
        array[arraySize++] = temp;
    }

    private void resize(int arrCapacity) {
        Item[] temp = (Item[]) new Object[arrCapacity];
        for (int i = 0; i < arraySize; i++) {
            temp[i] = array[i];
        }
        array = temp;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        if (arraySize == array.length / 4) {
            resize(array.length / 2);
        }
        int randomIndex = StdRandom.uniform(arraySize);
        Item item = array[randomIndex];
        array[randomIndex] = array[--arraySize];
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        int randomIndex = StdRandom.uniform(arraySize);
        return array[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        private int copyArrSize = arraySize;
        private Item[] temp;

        public RandomIterator() {
            temp = (Item[]) new Object[copyArrSize];
            for (int i = 0; i < copyArrSize; i++)
                temp[i] = array[i];
        }

        public boolean hasNext() {
            return copyArrSize > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int tempIndex = StdRandom.uniform(copyArrSize);
            Item item = temp[tempIndex];
            if (tempIndex != copyArrSize - 1) {
                temp[tempIndex] = temp[copyArrSize - 1];
            }
            temp[copyArrSize - 1] = null;
            copyArrSize--;
            return item;
        }
    }

    public static void main(String[] args) {
    }
}