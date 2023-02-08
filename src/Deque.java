import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node{
        public Item item;
        public Node next;
        public Node prev;
    }

    private class DequeIterator implements Iterator<Item>
    {
        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (current == null) {
               throw new NoSuchElementException();
            }
            Node node = current;
            current = node.next;
            return node.item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private Node head = null;
    private Node tail = null;
    private int size = 0;

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null){
            throw new IllegalArgumentException();
        }

        Node node = new Node();
        node.item = item;

        if(size == 0){
            head = node;
            tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
        ++size;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null){
            throw new IllegalArgumentException();
        }

        Node node = new Node();
        node.item = item;

        if(size == 0){
            head = node;
            tail = node;
        } else {
            node.prev = tail;
            tail.next = node;
            tail = node;
        }
        ++size;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        Item item = head.item;
        --size;

        head = head.next;
        if(size == 0) {
            tail = null;
        } else {
            head.prev = null;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        Item item = tail.item;
        --size;

        tail = tail.prev;

        if (size == 0) {
            head = null;
        } else {
            tail.next = null;
        }

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        deque.addFirst("1");
        deque.addLast("2");
        deque.addFirst("3");
        deque.addFirst("4");
        deque.addLast("0");

        deque.removeFirst();
        deque.removeLast();
        deque.removeLast();
        deque.removeFirst();

        for (String s: deque) {
            System.out.println(s);
        }
    }

}