import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int[] iteratorQueue;
        int index = 0;

        public RandomizedQueueIterator() {

            iteratorQueue = new int[size];
            for (int i = 0; i < size; ++i) {
                iteratorQueue[i] = i;
            }

            for(int i = 1; i < size; ++i) {
                int swapIndex = StdRandom.uniformInt(i + 1);
                int temp = iteratorQueue[i];
                iteratorQueue[i] = iteratorQueue[swapIndex];
                iteratorQueue[swapIndex] = temp;
            }
        }

        @Override
        public boolean hasNext() {
            return index < iteratorQueue.length;
        }

        @Override
        public Item next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            return queue[iteratorQueue[index++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    private Item[] queue;
    private int size = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (size == queue.length)
        {
            Item[] resizedQueue = (Item[]) new Object[queue.length * 2];
            for(int i = 0; i < queue.length; i++) {
                resizedQueue[i] = queue[i];
            }
            queue = resizedQueue;
        }

        queue[size] = item;
        ++size;
    }

    // remove and return a random item
    public Item dequeue() {
        if(size == 0) {
            throw new NoSuchElementException();
        }

        int r = StdRandom.uniformInt(size);
        Item item = queue[r];

        --size;
        queue[r] = queue[size];
        queue[size] = null;

        if (queue.length > 4 && size <= queue.length / 4) {
            Item [] resizedQueue = (Item[]) new Object[queue.length / 2];
            for(int i = 0; i < this.size; i++) {
                resizedQueue[i] = queue[i];
            }
            queue = resizedQueue;
        }

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if(size == 0) {
            throw new NoSuchElementException();
        }
        return queue[StdRandom.uniformInt(size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> deque = new RandomizedQueue<>();
        deque.enqueue("1");
        deque.enqueue("2");
        deque.enqueue("3");
        deque.enqueue("4");
        deque.enqueue("0");

        //deque.dequeue();
        //deque.dequeue();
        //deque.dequeue();
        //deque.dequeue();

        System.out.println("size: " + deque.size);

        for (String s: deque) {
            System.out.println(s);
        }
    }
}