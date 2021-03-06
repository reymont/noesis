package ikor.math;

// Title:       2D Vector
// Version:     1.0
// Copyright:   1998-2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org


/**
 * Immutable 2D vector.
 * 
 * @author Fernando Berzal
 */

public class Vector2D implements java.io.Serializable 
{
	double x,y;
	
	public Vector2D (double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	
	public double x()
	{
		return x;
	}
	
	public double y()
	{
		return y;
	}
	
	public double length ()
	{
		return Math.sqrt(x*x+y*y);
	}
	
	public double squaredLength ()
	{
		return x*x + y*y;
	}
	
	public Vector2D normalize()
	{
		double length = this.length();
		
		if (length> Configuration.EPSILON) {
			return new Vector2D (this.x/length, this.y/length);
		} else {
			return null;
		}
	}
	
	public Vector2D reverse ()
	{
		return new Vector2D(-x, -y);
	}
	
	
	public Vector2D add (Vector2D v)
	{
		return new Vector2D (this.x + v.x, this.y + v.y);
	}
	
	public Vector2D substract (Vector2D v)
	{
		return new Vector2D (this.x - v.x, this.y - v.y);
	}

	public Vector2D multiply (double a)
	{
		return new Vector2D (a*this.x, a*this.y);
	}

	public Vector2D divide (double a)
	{
		return new Vector2D (this.x/a, this.y/a);
	}
	
	public double dotProduct (Vector2D v)
	{
		return this.x*v.x + this.y*v.y;
	}
	
	public double projection (Vector2D v)
	{
		double length = v.length();
		
		if (length>0)
			return dotProduct(v)/v.length();
		else
			return 0;
	}
	
	/**
	 * Angle between two vectors. The dot product won't give all possible values 
	 * between 0� and 360�, or -180� and +180�, hence we resort the atan2 function. 
	 * @param v The other vector
	 * @return Angle between vectors (between -Math.PI and Math.PI)
	 */
	public double angle (Vector2D v)
	{
		double angle = Math.atan2(v.y,v.x)- Math.atan2(this.y, this.x);
		
		if ( (this.squaredLength()==0) || (v.squaredLength()==0) )
			return 0;
		if (angle > Math.PI)
			return angle - 2*Math.PI;
		else if (angle < -Math.PI)
			return angle + 2*Math.PI;
		else
			return angle;
	}
	
	public double distance (Vector2D v)
	{
		double dx = this.x - v.x;
		double dy = this.y - v.y;
		
		return Math.sqrt(dx*dx+dy*dy);
	}

	public double squaredDistance (Vector2D v)
	{
		double dx = this.x - v.x;
		double dy = this.y - v.y;
		
		return dx*dx+dy*dy;
	}	
	
	
	/**
	 * Given a normalized vector, reflect the vector 
	 * (as the path of a ball hitting a wall)
	 * @param normal Normal to the hitting surface
	 * @return Reflected vector
	 */
	public Vector2D reflect (Vector2D normal)
	{
		Vector2D displacement = normal.multiply ( 2*this.dotProduct(normal) ).reverse();
			
		return this.add (displacement);
	}
	
	
	// Overriden Object methods
	
	@Override
	public boolean equals (Object obj)
	{
		if (this==obj)
			return true;
		else {
			
			if (!(obj instanceof Vector2D)) {
				return false;
			} else {
				
				Vector2D v = (Vector2D) obj;
				
				return ( Math.abs(this.x-v.x) < Configuration.EPSILON )
					&& ( Math.abs(this.y-v.y) < Configuration.EPSILON );
			}
		}
	}
	
	@Override 
	public int hashCode ()
	{
		return this.toString().hashCode(); 
	}

	
	@Override
	public String toString ()
	{
		return "("+x+", "+y+")";
	}

}
