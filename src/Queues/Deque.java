//package Queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item>{

    private class DequeIterator implements Iterator<Item>{

        private Node current = first;

        @Override
        public void remove(){
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if(current == null){
                throw  new NoSuchElementException();
            }

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

    public Deque() {

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        Node newFirst = new Node(item);

        if(size == 0){
            last = newFirst;
        }
        else{
            newFirst.next = first;
            first.prev = newFirst;
        }

        first = newFirst;
        ++size;
    }

    public void addLast(Item item) {
        Node newLast = new Node(item);

        if(size == 0){
            first = newLast;
        } else {
            newLast.prev = last;
            last.next = newLast;
        }

        last = newLast;
        ++size;
    }

    public Item removeFirst() {
        if(size == 0){
            throw new NoSuchElementException();
        }

        Item item = first.value;

        if(size == 1){
            clear();
        } else if(size == 2) {
            first = last;
            first.next = null;
            first.prev = null;
            //removePenultimate(first, last);
        } else {
            first = first.next;
            first.prev = null;
        }

        --size;
        return item;
    }

    public Item removeLast() {
        if(size == 0){
            throw new NoSuchElementException();
        }

        Item item = last.value;
        if(size == 1){
            clear();
        } else if(size == 2) {
            last = first;
            last.next = null;
            last.prev = null;
        } else {
            last = last.prev;
            last.next = null;
        }

        --size;
        return item;
    }

    private void clear(){
        first = null;
        last = null;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args){
        Deque<String> queue = new Deque<>();
        queue.addFirst("1");
        queue.removeLast();
        queue.addFirst("2");
    }
}
