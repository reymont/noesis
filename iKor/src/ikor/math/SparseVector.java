package ikor.math;

// Title:       Sparse vector ADT
// Version:     1.0
// Copyright:   2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org


/**
 * Sparse vector implementation.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class SparseVector extends Vector 
{
	private final static int INITIAL_ARRAY_SIZE = 4;
	
	private int size;
	private int used;
	private int index[];
	private double value[];

	
	public SparseVector (int size)
	{
		this.size = size;
		this.index = new int[INITIAL_ARRAY_SIZE];
		this.value = new double[INITIAL_ARRAY_SIZE];
		this.used = 0;
	}
	
	public void extend ()
	{
		int[] newIndex = new int[2*index.length];
		double[] newValue = new double[2*value.length];
		
		System.arraycopy(index,0,newIndex,0,index.length);
		System.arraycopy(value,0,newValue,0,value.length);
		
		this.index = newIndex;
		this.value = newValue;
	}
	
	private void insert (int pos, int ndx, double val)
	{
		if (used==index.length)
			extend();
		
		for (int i=used; i>pos; i--) {
			index[i] = index[i-1];
			value[i] = value[i-1];
		}
		
		index[pos] = ndx;
		value[pos] = val;
		used++;
		
		if (ndx>=size)
			size = ndx+1;
	}

	
	@Override
	public int size() 
	{
		return size;
	}
	
	/**
	 * Number of nonzero elements in the sparse vector
	 * @return Number of nonzero vector elements
	 */
	public int nonzero()
	{
		return used;
	}
	
	/**
	 * Binary search, O(log n)
	 * 
	 * @param x Index value
	 * @return Index position
	 */

	private int position (int x)
	{
		int left = 0;
		int right = used-1;
		int middle = (left+right)/2;
		
		while ((left<right) && (index[middle]!=x)) {
			
			if (x<index[middle])
				right = middle - 1;
			else
				left = middle + 1;
			
			middle = (left+right)/2;
		}
		
		if ((middle<used) && (index[middle]<x)) {
			middle++;
		}

		return middle;
	}

	@Override
	public double get(int i) 
	{
		int p = position(i);
		
		if ((p>=0) && (p<used) && (index[p]==i))
			return value[p];
		else
			return 0.0;
	}

	@Override
	public void set(int i, double value) 
	{
		int p = position(i);
		
		if ((p>=0) && (p<used) && index[p]==i) {
			this.value[p] = value;
		} else if (value!=0.0) {
			insert(p,i,value);
		}
	}

	// Sparse vector operations
	// ------------------------
	
	// Dot product
	
	@Override
	public double dotProduct (Vector other)
	{
		double result = 0;
		
		for (int i=0; i<used; i++)
			result += value[i] * other.get(index[i]);
		
		return result;
	}

	public double dotProduct (SparseVector other)
	{
		double result = 0;
		int    i = 0;
		int    j = 0;
				
		while ((i<this.used) && (j<other.used)) {
			
			if (this.index[i]==other.index[j]) {
				result += this.value[i]*other.value[j];
				i++;
				j++;
			} else if (this.index[i]<other.index[j]) {
				i++;
			} else { // this.index[i]>other.index[j]
				j++;
			}
		}
		
		return result;
	}
	

	@Override
	public double magnitude2 ()
	{
		double result = 0;
		
		for (int i=0; i<used; i++)
			result += value[i]*value[i];
		
		return result;
	}
	
	@Override
	public double distance2 (Vector other)
	{
		double diff;
		double result = 0;
		
		for (int i=0; i<used; i++) {
			diff = value[i] - other.get(index[i]);
			result += diff*diff;
		}
		
		return result;		
	}	
	
	
	// Statistics
	
	@Override
	public double min ()
	{
		double min = (nonzero()<size())? 0: Double.MAX_VALUE;
		
		for (int i=0; i<used; i++)
			if (value[i]<min)
				min = value[i];
		
		return min;
	}


	@Override
	public double max ()
	{
		double max = (nonzero()<size())? 0: -Double.MAX_VALUE;
		
		for (int i=0; i<used; i++)
			if (value[i]>max)
				max = value[i];
		
		return max;
	}


	@Override
	public double sum ()
	{
		double sum = 0;
		
		for (int i=0; i<used; i++)
			sum += value[i];
		
		return sum;
	}	

	@Override
	public double sum2 ()
	{
		double sum2 = 0;
		
		for (int i=0; i<used; i++)
			sum2 += value[i]*value[i];
		
		return sum2;
	}	
	
}
