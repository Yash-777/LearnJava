package com.github.core.utilcollections;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Vector;

/**
 * <ul>Push and Pop Functions of a List Interface
 * <li><b>LinkedList</b>Linked lists provide very fast insertion or deletion of a list member.
 * As each linked list contains a pointer to the next member in the list. 
 * It is non-linear data structure.
 * 
 * </li>
 * <li><b>Stack</b> is a subclass of Vector, liner type data structure.
 * It works in <b>LIFO</b> manner(Last in first out).
<br />
Example: They are like a stack of coins. Coin1, Coin2,...
<br />
Apart from the methods inherited from its parent class Vector, Stack defines following methods:
<ol>
<li> boolean empty():Tests if this stack is empty. Returns true if the stack is empty,
and returns false if the stack contains elements.</li>
<li> Object peek( ):Returns the element on the top of the stack, but does not remove it.</li>
<li> Object pop( ):Returns the element on the top of the stack, removing it in the process.</li>
<li> Object push(Object element):Pushes element onto the stack. element is also returned.</li>
</ol>
</li>
https://www.quora.com/What-is-the-difference-between-a-stack-and-a-linked-list
 * @author yashwanth.m
 *
 */
public class CollectionsPushPop {
	public static void main(String[] args) {
		
		// Deque.class « Stack methods « push(E e), pop()
		LinkedList<String> linkedList = new LinkedList<String>();
		/* Deque<E>
public class LinkedList<E>
             extends AbstractSequentialList<E>
			         extends AbstractList<E>
					         extends AbstractCollection<E> implements List<E>
             implements List<E>, Deque<E>, Cloneable, java.io.Serializable
			            extends Collection<E>
		*/
		// Add few Elements
		linkedList.add("Jack");
		linkedList.add("Robert");
		linkedList.add("Chaitanya");
		linkedList.add("kate");
		
		// Display LinkList elements
		System.out.println("LinkedList contains: "+linkedList);
		
		// push Element First to the list
		linkedList.push("Fisrst NEW ELEMENT");
		// add Element Last to the list
		linkedList.add("Last NEW ELEMENT");
		
		System.out.println("LinkedList contains: "+linkedList);
		linkedList.pop(); // Removes First Entry
		linkedList.remove(); // Removes First Entry
		System.out.println("LinkedList contains: "+linkedList);
		linkedList.removeLast();
		System.out.println("LinkedList contains: "+linkedList);
		
		/*
public class
Stack<E> extends Vector<E>
                 extends AbstractList<E>
				         extends AbstractCollection<E> implements List<E>
						         implements Collection<E>
				 implements List<E>, RandomAccess, Cloneable, java.io.Serializable
				            List<E> extends Collection<E>
		 */
		// Push() and Add() methods adds the element last.
		Stack<Integer> stack = new Stack<>();
		stack.add(10);
		stack.push(20);
		
		stack.add(100);
		stack.push(200);
		
		// Display after push operation
		System.out.println("Stack contains: "+stack);
		
		stack.pop(); // Removes Last
		System.out.println("Stack contains: "+stack);
		stack.removeElementAt(0);// Removes based on index, remove()-not available.
		System.out.println("Stack contains: "+stack);
		stack.remove(0);// Removes based on index, remove()-not available.
		System.out.println("Stack contains: "+stack);
		
		// Non Push() and Pop() functions in List Interface.
		ArrayList<Integer> al = new ArrayList<>();
		al.add(10);
		al.add(20);
		System.out.println("Array List Contains : "+ al);
		al.remove(0);
		System.out.println("Array List Contains : "+ al);
		
		Vector<Integer> v = new Vector<>();
		v.add(10);
		v.add(20);
		System.out.println("Vector list contains : "+ v);
		v.remove(1);
		System.out.println("Vector list contains : "+ v);
	}
}
