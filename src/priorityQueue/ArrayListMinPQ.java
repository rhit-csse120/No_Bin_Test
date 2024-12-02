package priorityQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import comparingShapes.Circle;

/**
 * An implementation of the Priority Queue interface using an array list.
 * 
 * @author Matt Boutell and <<TODO: Your name here>>>. Created Mar 29, 2014.
 * 
 * @param <T> Generic type of PQ elements
 */
public class ArrayListMinPQ<T extends Comparable<T>> {
	// Requirement: The methods below must use the items list.
	// You may not add other fields to this ArrayListMinPQ class,
	// but you may add local variables to the methods.
	private ArrayList<T> items;

	public ArrayListMinPQ() {
		this.items = new ArrayList<>();
	}

	public T findMin() {
		// This is also known as peekMin
		// Done: implement
		if (this.isEmpty()) {
			return null;
		}
		return items.get(items.size()-1);
	}

	public T deleteMin() {
		if(this.isEmpty()) {
			return null;
		}
		return items.remove(items.size()-1);
	}

	public void insert(T item) {
		this.items.add(item);
		Collections.sort(items, new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				return o2.compareTo(o1);
			}
			
		} );
	}

	public int size() {
		return this.items.size();

	}

	public boolean isEmpty() {
		return (this.size() == 0);
	}

	public void clear() {
		items = new ArrayList<>();
	}
}
