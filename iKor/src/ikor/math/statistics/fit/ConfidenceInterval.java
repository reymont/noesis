package ikor.math.statistics.fit;

/**
 * Confidence interval.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class ConfidenceInterval 
{
	private double min;
	private double max;
	private double alpha;

	// Constructors
	
	public ConfidenceInterval (double min, double max)
	{
		this.min = min;
		this.max = max;
	}

	public ConfidenceInterval (double min, double max, double alpha)
	{
		this.min = min;
		this.max = max;
		this.alpha = alpha;
	}

	// Getters
	
	public double min ()
	{
		return min;
	}
	
	public double max ()
	{
		return max;
	}

	public double alpha ()
	{
		return alpha;
	}
	
	public double confidenceLevel ()
	{
		return 1.0 - alpha;
	}
}
