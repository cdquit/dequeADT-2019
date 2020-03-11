/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handin;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author jmm4115
 */
public class ArrayDeque<E> implements DequeADT<E> {
    private final int INITIAL_CAPACITY = 3;
    private int front;
    private int rear;
    private int numElements;
    private E[] list;
    
    public ArrayDeque()
    {
        front = -1;
        rear = -1;
        numElements = 0;
        list = (E[]) (new Object[INITIAL_CAPACITY]);
    }

    @Override
    public void enqueueRear(E element) {
        if (isEmpty()) //list is empty
        {
            //point front and rear to current location
            front = 0;
            rear = 0;
        }
        else
        {
            if (list.length == numElements) //list is full, expand it
            {
//                last = list.length - 1;
                rear = list.length; //pointing rear to next location after expand
                expandCapacity();
                front = 0; //point front to current location after expand
            }   
            else
                rear = (rear + list.length + 1) % list.length; //increase rear by 1 and wraps around the array
        }
        
        list[rear] = element;
        numElements++;
    }

    @Override
    public E dequeueFront() {
        if (isEmpty())
            throw new NoSuchElementException("Empty List");
        
        E element = list[front];
        
        if (front == rear) //only one element in list.
            clear();
        else
        {
            list[front] = null;
            front = (front + list.length + 1) % list.length; //increase front by one and wraps around the array
            numElements--;
        }
        
        return element;
    }

    @Override
    public E first() {
        if (isEmpty())
            throw new NoSuchElementException("Empty List");
        
        return list[front];
    }

    @Override
    public E last() {
        if (isEmpty())
            throw new NoSuchElementException("Empty List");
        
        return list[rear];
    }

    @Override
    public void enqueueFront(E element) {
        
        if (isEmpty()) //list is empty
        {
            //point front and rear to the current location
            front = 0;
            rear = 0;
        }
        else 
        {
            if (list.length == numElements) //list is full, expand it
            {
                rear = list.length - 1; //point rear to current location after expand
                expandCapacity();
                front = list.length - 1; //point front to next location after expand
            }
            else
                front = (front + list.length - 1) % list.length; //decrease front by one and wraps around the array
        }
        
        list[front] = element;
        numElements++;
    }

    @Override
    public E dequeueRear() {
        if (isEmpty())
            throw new NoSuchElementException("Empty List");
        
        E element = list[rear];
        
        if (front == rear) //only one element in list.
            clear();
        else
        {
            list[rear] = null;
            rear = (rear + list.length - 1) % list.length; //decrease rear by one and wraps around the array
            numElements--;
        }
        
        return element;
    }

    @Override
    public boolean isEmpty() {
        return numElements == 0;
    }

    @Override
    public int size() {
        return numElements;
    }

    @Override
    public Iterator<E> iterator() {
//        return new ArrayIterator<E>(front, list);
        return new ArrayIterator<E>(front);
    }

    @Override
    public void clear() {
        list = (E[]) (new Object[INITIAL_CAPACITY]);
        front = -1;
        rear = -1;
        numElements = 0;
    }
    
    @Override
    public String toString()
    {
        String s = "[";
        
        for (int k = 0; k < list.length; k++) //loop through the list array
        {
            //if list[k] is not null prints the string representation of the object, else prints null
            s += (list[k] != null) ? list[k].toString() : "null";
            if (k != list.length - 1)
                s += ", ";
        }
        
//        Iterator<E> it = iterator();
//        while (it.hasNext())
//        {
//            s += it.next().toString();
//            if (it.hasNext())
//                s += ", ";
//        }
        
        return s + "]";
    }
    
    private void expandCapacity()
    {
        E[] largerArray = (E[]) (new Object[list.length * 2]);
        int index = 0;
        Iterator<E> it = iterator();
        while (it.hasNext())
            largerArray[index++] = it.next();
        
        list = largerArray;
    }
    
    private class ArrayIterator<E> implements Iterator<E>
    {
        private int start;
        private int count;
        
        public ArrayIterator(int front)
        {
            start = front; //starting from first element
            count = 0; //counter to track the number of non-null values
        }
        
        @Override
        public boolean hasNext() {
            if (start == -1) //if start is -1 means list is empty
                return false;
            
            //if array[start] is null then increase start by one so null values are skipped
            while (list[start] == null && count != numElements)
                start = (start + list.length + 1) % list.length;
            
            return count != numElements;
        }

        @Override
        public E next() {
            E element = (E) list[start];
            count++;
            start = (start + list.length + 1) % list.length;
            return element;
        }

        @Override
        public void remove() {
//            Iterator.super.remove(); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    public static void main(String[] args)
    {
        ArrayDeque<String> ad = new ArrayDeque<String>();
        //enqueue front only, INITIAL_CAPACITY == 3:
        ad.enqueueFront("what");
        ad.enqueueFront("is");
        ad.enqueueFront("the");
        ad.enqueueFront("problem");
        ad.enqueueFront("i");
        ad.enqueueFront("am");
        ad.enqueueFront("the");
        ad.enqueueFront("issue");
        ad.enqueueFront("stupid");
        ad.enqueueFront("me");
        ad.enqueueFront("damn");
        ad.enqueueFront("it");
        ad.enqueueFront("man");
        System.out.println("Print list insert front only and testing expand : " + ad);
        ad.clear();
        
        //enqueue rear only, INITIAL_CAPACITY == 3:
        ad.enqueueRear("what");
        ad.enqueueRear("is");
        ad.enqueueRear("the");
        ad.enqueueRear("problem");
        ad.enqueueRear("i");
        ad.enqueueRear("am");
        ad.enqueueRear("the");
        ad.enqueueRear("issue");
        ad.enqueueRear("stupid");
        ad.enqueueRear("me");
        ad.enqueueRear("damn");
        ad.enqueueRear("it");
        ad.enqueueRear("man");
        System.out.println("Print list insert rear only and testing expand : " + ad);
        
        //dequeue front and rear:
        System.out.println("Delete front : " + ad.dequeueFront());
        System.out.println("Print size after delete front : " + ad.size());
        System.out.println("Delete front : " + ad.dequeueFront());
        System.out.println("Delete rear : " + ad.dequeueRear());
        System.out.println("Delete rear : " + ad.dequeueRear());
        
        //list is empty after dequeue and # elements = 0 and print list
        System.out.println("Check if list is empty after deleting : " + ad.isEmpty());
        System.out.println("Print size after deleting : " + ad.size());
        System.out.println("Print list after deleting : " + ad);
        ad.clear();
        
        //dequeue empty list throws exception
//        System.out.println(ad.dequeueFront());
//        System.out.println(ad.dequeueRear());
        
        //empty list prints a blank space:
        System.out.println("Empty list prints nulls : " + ad);
        
        //enqueue rear and then front:
        ad.enqueueRear("Hey");
        ad.enqueueFront("You");
        ad.enqueueRear("im");
        ad.enqueueFront("confused");
        ad.enqueueRear("what");
        ad.enqueueFront("is");
        ad.enqueueRear("going");
//        ad.enqueueFront("on");
        System.out.println("Print list insert rear and then insert front : " + ad);
        
        //show first and last elements:
        System.out.println("First element : " + ad.first());
        System.out.println("Last element : " + ad.last());
        ad.clear();
        
        //enqueue front and then rear:
        ad.enqueueFront("Hey");
        ad.enqueueRear("You");
        ad.enqueueFront("im");
        ad.enqueueRear("confused");
        ad.enqueueFront("what");
        ad.enqueueRear("is");
        ad.enqueueFront("going");
//        ad.enqueueRear("on");
        System.out.println("Print list insert front and then insert rear : " + ad);
        
        //show first and last elements:
        System.out.println("First element : " + ad.first());
        System.out.println("Last element : " + ad.last());
        
        //list is non-empty after enqueue and show # elements:
        System.out.println("Check if list is empty after inserting : " + ad.isEmpty());
        System.out.println("Print size after inserting : " + ad.size());
        
        //clear the list and prints size if list is empty:
        ad.clear();
        if (ad.isEmpty())
            System.out.println("Print size after clear : " + ad.size());
    }
}
