package noesis.algorithms.paths;

import noesis.Network;
import ikor.collection.Evaluator;
import ikor.collection.IndexedPriorityQueue;
import ikor.collection.Indexer;
import ikor.collection.PriorityQueue;

public class AStarPathFinder<V, E> extends SingleSourcePathFinder<V,E> implements PathFinder<V, E>
{
	private Evaluator<E> linkEvaluator;
	private Evaluator<V> heuristicEvaluator;
	
	private double[] g;
	private double[] h;
	
	public AStarPathFinder (Network<V,E> net, int origin, Evaluator<E> linkEvaluator, Evaluator<V> heuristicEvaluator)
	{
		super(net,origin);

		this.linkEvaluator = linkEvaluator;
		this.heuristicEvaluator = heuristicEvaluator;
	}
	
	/* (non-Javadoc)
	 * @see noesis.algorithms.paths.PathFinder#run()
	 */	
	@Override
	public void run()
	{
		PriorityQueue<Integer> open;
		boolean[]              closed;
		double                 linkValue;
		double                 nodeValue;
		int                    size = network.size();
		
		// Initialization
		
		predecessor = new int[size];
		g = new double[size];
		h = new double[size];
		
		open = createPriorityQueue(size);
		closed = new boolean[network.size()];
		
		for (int i=0; i<size; i++) {
			predecessor[i] = -1;
			closed[i] = false;
			g[i] = Double.POSITIVE_INFINITY;
			h[i] = Double.POSITIVE_INFINITY;
		}
		
		g[origin] = 0;
		h[origin] = heuristicEvaluator.evaluate(network.get(origin));
		open.add(origin);
	
		// Search
		
        while (open.size()>0) {
            
        	int vertex = open.get();
        	int degree = network.outDegree(vertex);
        	int target;
        	
        	closed[vertex] = true;
        	
        	for (int j=0; j<degree; j++) {

        		target = network.outLink(vertex,j);

        		if (!closed[target]) {

        			linkValue = linkEvaluator.evaluate( network.get(vertex,target) );
        			nodeValue = g[vertex] + linkValue;

        			if (nodeValue < g[target]) {
        				g[target] = nodeValue;
        				h[target] = heuristicEvaluator.evaluate(network.get(target));
        				predecessor[target] = vertex;
        				open.add(target);
        			}        				
        		}
        	}
        }		
	}
	
	
	private PriorityQueue<Integer> createPriorityQueue (int size)
	{
		Indexer<Integer>             nodeIndexer;
		Evaluator<Integer>           nodeEvaluator;
		PriorityQueue<Integer>       queue;

		nodeIndexer = new AStarNodeIndexer();
		nodeEvaluator = new AStarNodeEvaluator(g,h);
		queue = new IndexedPriorityQueue<Integer>(size,nodeEvaluator,nodeIndexer);
		
		return queue;
	}
	

	// Distances
	
	public final double[] cost ()
	{
		return g;
	}
	
	public final double cost (int node)
	{
		return g[node];
	}
	
	// Ancillary classes
	
	private class AStarNodeEvaluator implements Evaluator<Integer>
	{
		double[] g;
		double[] h;
		
		public AStarNodeEvaluator (double[] g, double[] h)
		{
			this.g = g;
			this.h = h;
		}
		
		@Override
		public double evaluate(Integer object) 
		{
			return g[object] + h[object];
		}
	}
	
	private class AStarNodeIndexer implements Indexer<Integer>
	{
		@Override
		public int index(Integer object) 
		{
			return object;
		}
		
	}	

}
