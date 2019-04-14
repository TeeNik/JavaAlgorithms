//package Queues;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private class QueueIterator implements Iterator<Item>{

        private int length = current;
        private Item[] copyArray = (Item[]) new Object[length];

        public QueueIterator(){
            for (int i = 0; i < length; ++i){
                copyArray[i] = array[i];
            }
        }

        @Override
        public void remove(){
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return length > 0;
        }

        @Override
        public Item next() {
            if(length == 0){
                throw new NoSuchElementException();
            }
            int rand = StdRandom.uniform(length);
            Item item = copyArray[rand];
            if(rand != length-1){
                copyArray[rand] = copyArray[length-1];
            }
            copyArray[length-1] = null;
            --length;
            return item;
        }
    }

    private int current;
    private Item[] array;

    public RandomizedQueue() {
        array = (Item[])new Object[2];
    }

    public boolean isEmpty() {
        return current == 0;
    }

    public int size() {
        return current;
    }

    private void resize(int newSize){
        Item[] copyArray = (Item[]) new Object[newSize];
        for (int i = 0; i < current; ++i){
            copyArray[i] = array[i];
        }
        array = copyArray;
    }

    public void enqueue(Item item) {
        if (item == null){
            throw new IllegalArgumentException();
        }

        if(current == array.length){
            resize(current*2);
        }
        array[current] = item;
        ++current;
    }

    public Item dequeue() {
        if(current == 0){
            throw  new NoSuchElementException();
        }

        int rand = StdRandom.uniform(current);
        Item item = array[rand];
        if(rand != current-1){
            array[rand] = array[current-1];
        }
        array[current-1] = null;
        if(current == array.length / 4){
            resize(array.length/2);
        }
        --current;
        return item;
    }

    public Item sample() {
        if(current == 0){
            throw new NoSuchElementException();
        }

        int rand = StdRandom.uniform(current);
        Item item = array[rand];
        if(current == array.length / 4){
            resize(array.length/2);
        }
        return item;
    }

    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    public static void main(String[] args){

        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < 5; ++i){
            queue.enqueue(i);
        }

        for (Integer i : queue) {
            System.out.println(i);
        }
    }
}