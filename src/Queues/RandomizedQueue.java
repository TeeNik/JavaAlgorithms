package Queues;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    public class QueueIterator implements Iterator<Item>{

        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            Item item = current.value;
            current = current.next;
            return item;
        }
    }

    private int size;
    private Node first;
    private Node last;

    private class Node{
        Item value;
        Node next;
        Node prev;

        Node(Item item){
            if(item == null){
                throw new IllegalArgumentException();
            }
            value = item;
        }
    }

    public RandomizedQueue() {

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        Node newFirst = new Node(item);
        newFirst.next = first;
        first.prev = newFirst;
        last = first;
        first = newFirst;
    }

    public Item dequeue() {
        Item item = last.value;
        Node newLast = last.prev;
        newLast.next = null;
        last = newLast;
        
        return item;
    }

    public Item sample() {

    }

    public Iterator<Item> iterator() {

    }

    public static void main(String[] args) {

    }
}