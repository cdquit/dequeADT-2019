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
public class LinkedDeque<E> implements DequeADT<E> {
    private Node<E> firstNode;
    private Node<E> lastNode;
    private int numElements;
    
    public LinkedDeque()
    {
        firstNode = null;
        lastNode = null;
        numElements = 0;
    }

    @Override
    public void enqueueRear(E element) {
        Node<E> newNode = new Node<E>(element);
        
        if (lastNode == null) //no element in list
        {
            lastNode = newNode; //point lastNode to the new element
            firstNode = lastNode; //point firstNode to lastNode, only element in the list
        }
        else
        {
            lastNode.next = newNode; //point next link of lastNode to new element
            newNode.previous = lastNode; //point previous link of new element to lastNode
            lastNode = newNode; //point lastNode to the new element
        }
        numElements++;
    }

    @Override
    public E dequeueFront() {
        if (firstNode == null)
            throw new NoSuchElementException("Empty List");
        
        Node<E> removeNode = firstNode;
        
        if (firstNode != lastNode)
            removeNode.next.previous = null; //nulify link to removeNode
        else
            lastNode = null; //only one element in list, reset lastNode to null
        
        firstNode = removeNode.next; //point firstNode to the next element
        removeNode.next = null; //nullify the link to the next element
        numElements--;
        
        return removeNode.element;
    }

    @Override
    public E first() {
        if (firstNode == null)
            throw new NoSuchElementException("Empty List");
        return firstNode.element;
    }

    @Override
    public E last() {
        if (lastNode == null)
            throw new NoSuchElementException("Empty List");
        return lastNode.element;
    }

    @Override
    public void enqueueFront(E element) {
        Node<E> newNode = new Node<E>(element);
        
        if (firstNode == null) //no element in list
        {
            firstNode = newNode; //point firstNode to the newNode
            lastNode = firstNode; //point lastNode to firstNode, only element in list
        }
        else
        {
            newNode.next = firstNode; //point the next link of new element to firstNode
            firstNode.previous = newNode; //point previous link of firstNode to new element
            firstNode = newNode; //point firstNode to the new element
        }
        
        numElements++;
    }

    @Override
    public E dequeueRear() {
        if (lastNode == null)
            throw new NoSuchElementException("Empty List");
        
        Node<E> removeNode = lastNode;
        
        if (lastNode != firstNode)
            removeNode.previous.next = null; //nulify link to removeNode
        else
            firstNode = null; //only one element in list, reset firstNode to null
        
        lastNode =  removeNode.previous; //point lastNode to the previous link
        removeNode.previous = null; //nullify the link to previous element
        numElements--;
        
        return removeNode.element;
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
        return new LinkedIterator<E>(firstNode);
    }
    
    @Override
    public void clear() {
        firstNode = null;
        lastNode = null;
        numElements = 0;
    }
    
    @Override
    public String toString()
    {
        String s = "[";
        
//        Node<E> currentNode = firstNode;
//        while (currentNode != null)
//        {
//            s += currentNode.element.toString();
//            currentNode = currentNode.next;
//            if (currentNode != null)
//                s += ", ";
//        }

        Iterator<E> it = iterator(); //take the iterator()
        while (it.hasNext())
        {
            s += it.next().toString();
            if (it.hasNext())
                s += ", ";
        }
        
        return s + "]";
    }
    
    private class LinkedIterator<E> implements Iterator<E>
    {
        private Node<E> nextNode;
        
        public LinkedIterator(Node<E> firstNode)
        {
            nextNode = firstNode;
        }

        @Override
        public boolean hasNext() {
            return nextNode != null;
        }

        @Override
        public E next() {
            E element = nextNode.element;
            nextNode = nextNode.next;
            return element;
        }

        @Override
        public void remove() {
//            Iterator.super.remove(); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    private class Node<E>
    {
        private E element;
        private Node<E> next;
        private Node<E> previous;
        
        public Node(E element)
        {
            this.element = element;
            next = null;
            previous = null;
        }
    }
    
    public static void main(String[] args)
    {
        LinkedDeque<String> ld = new LinkedDeque<String>();
        //enqueue front only:
        ld.enqueueFront("what");
        ld.enqueueFront("is");
        ld.enqueueFront("the");
        ld.enqueueFront("problem");
        ld.enqueueFront("i");
        ld.enqueueFront("am");
        ld.enqueueFront("the");
        ld.enqueueFront("issue");
        ld.enqueueFront("stupid");
        ld.enqueueFront("me");
        ld.enqueueFront("damn");
        ld.enqueueFront("it");
        ld.enqueueFront("man");
        System.out.println("Print list insert front only : " + ld);
        ld.clear();
        
        //enqueue rear only:
        ld.enqueueRear("what");
        ld.enqueueRear("is");
        ld.enqueueRear("the");
        ld.enqueueRear("problem");
        ld.enqueueRear("i");
        ld.enqueueRear("am");
        ld.enqueueRear("the");
        ld.enqueueRear("issue");
        ld.enqueueRear("stupid");
        ld.enqueueRear("me");
        ld.enqueueRear("damn");
        ld.enqueueRear("it");
        ld.enqueueRear("man");
        System.out.println("Print list insert rear only : " + ld);
        
        //dequeue front and rear:
        System.out.println("Delete front : " + ld.dequeueFront());
        System.out.println("Print size after delete front : " + ld.size());
        System.out.println("Delete front : " + ld.dequeueFront());
        System.out.println("Delete rear : " + ld.dequeueRear());
        System.out.println("Delete rear : " + ld.dequeueRear());
        
        //list is empty after dequeue and number of elements is 0:
        System.out.println("Check if list is empty after deleting : " + ld.isEmpty());
        System.out.println("Print size after deleting : " + ld.size());
        System.out.println("Print list after deleting : " + ld);
        ld.clear();
        
        //dequeue empty list throws exception:
//        System.out.println(ld.dequeueRear());
//        System.out.println(ld.dequeueFront());

        //string representation of empty list prints a blank space:
        System.out.println("Empty list prints blank space : " + ld);

        //enqueue rear and then front:
        ld.enqueueRear("Hey");
        ld.enqueueFront("You");
        ld.enqueueRear("im");
        ld.enqueueFront("confused");
        ld.enqueueRear("what");
        ld.enqueueFront("is");
        ld.enqueueRear("going");
        System.out.println("Print list insert rear and then insert front : " + ld);
        
        //show first and last elements:
        System.out.println("First element : " + ld.first());
        System.out.println("Last element : " + ld.last());
        ld.clear();
        
        //enqueue front and then rear:
        ld.enqueueFront("Hey");
        ld.enqueueRear("You");
        ld.enqueueFront("im");
        ld.enqueueRear("confused");
        ld.enqueueFront("what");
        ld.enqueueRear("is");
        ld.enqueueFront("going");
        System.out.println("Print list insert front and then insert rear : " + ld);
        
        //show first and last elements:
        System.out.println("First element : " + ld.first());
        System.out.println("Last element : " + ld.last());
        
        //list is non-empty after enqueue and show number of elements:
        System.out.println("Check if list is empty after inserting : " + ld.isEmpty());
        System.out.println("Print size after inserting : " + ld.size());
        
        //clear the list and prints size if list is empty:
        ld.clear();
        if (ld.isEmpty())
            System.out.println("Print size after clear : " + ld.size());
    }
}
