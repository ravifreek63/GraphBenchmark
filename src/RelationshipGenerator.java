
public class RelationshipGenerator implements Runnable {
	private Graph _graph;
	private int _workerId;
	
	@Override
	public void run() {
		int partitionSize = _graph.getNumNodes() / _graph.getNumberThreads();
		int partitionStart = _workerId * partitionSize;
		int partitionEnd = partitionStart + partitionSize;
		int edgeCount = _graph.getEdgeCount();
		int from, to;
		for(int index = 0; index < edgeCount; index++){
			from = _graph.getEdge(index, 0);
			to = _graph.getEdge(index, 1);
			if(from >= partitionStart && from < partitionEnd){
//				_graph.createEdgeBetween(from, to);
			}
		}
	}
	
	public RelationshipGenerator(Graph g, int id){
		_graph = g;
		_workerId = id;
	}
}
