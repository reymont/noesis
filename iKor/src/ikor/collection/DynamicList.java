package ikor.collection;

// Title:       iKor Collection Framework
// Version:     1.0
// Copyright:   2011
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org

import java.util.ArrayList;
import java.util.Iterator;

import ikor.collection.List;

/**
 * Standard dynamic list implementation, based on java.util.ArrayList 
 * (not-synchronized resizable-array implementation in the Java Collections Framework).
 * 
 * @author Fernando Berzal
 */

public class DynamicList<T> implements List<T>
{
	private ArrayList<T> list = new ArrayList<T>();

	/**
	 * Default constructor
	 */
	public DynamicList ()
	{
	}
	
	/**
	 * Copy constructor
	 * @param array array to be copied
	 */
	
	public DynamicList (T[] array)
	{
		for (int i=0; i<array.length; i++)
			this.add (array[i]);		
	}
	
	/**
	 * Copy constructor
	 * @param list list to be copied
	 */
	
	public DynamicList (ReadOnlyCollection<T> list)
	{
    	for (T t: list) {
    		this.add(t);
    	}		
	}
	
	@Override
	public T get(int index) 
	{	
		if ((index>=0) && (index<list.size()))
			return list.get(index);
		else
			return null;
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public Iterator<T> iterator() {
		return new ListIterator<T>(this);
	}

	@Override
	public int add(T object) {
		if (list.add(object))
			return size()-1;
		else
			return -1;
	}

	@Override
	public boolean remove(T object) {
		return list.remove(object);
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public T set(int index, T object) {
		
		if (index>=list.size()) {
			list.ensureCapacity(index+1);
			while (list.size()<=index)
				list.add(null);
		}
		return list.set(index,object);
	}

	@Override
	public T remove(int index) {
		return list.remove(index);
	}

	@Override
	public boolean contains(T object) {
		return list.contains(object);
	}

	@Override
	public int index(T object) {
		return list.indexOf(object);
	}
	
    /**
     * Returns a collection with all the elements of a given type
     * @param type The type of the elements to be retrieved
     * @return The collection formed by the elements of the specified type
     */
    
    public ReadOnlyList<T> get (Class type)
    {
    	int            i;
    	T              element;
    	List<T> result = new DynamicList<T>();
    	
    	for (i=0; i<size(); i++) {
    		element = get(i);
    		
    		if (type.isInstance(element))
    			result.add(element);
    	}
    	
    	return result;   	
    }
   
    /** 
     * Standard output
     */
    
    public String toString()
    {
        String str = ""; // "[Collection]\n"; 
        
        for (int i = 0; i < size(); i++)
            str += "  " + get(i) + "\n";
        
        return str;
    }


}
