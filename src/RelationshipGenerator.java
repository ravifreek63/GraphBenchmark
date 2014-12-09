import java.util.Random;


public class RelationshipGenerator implements Runnable {
	private Graph _graph;
	private int _workerId;
	
	@Override
	public void run() {
		int num_nodes = _graph.getNumNodes();
		int partitionSize = _graph.getNumNodes() / _graph.getNumberThreads();
		int partitionStart = _workerId * partitionSize;
		int partitionEnd = partitionStart + partitionSize;
		int from, to;
		Random random = new Random();
		for(int bf = 0; bf < _graph.getBranchFactor(); bf++){
			for(from = partitionStart; from < partitionEnd; from++ ){
			to = random.nextInt(num_nodes);
				_graph.createEdgeBetween(from, to);
			}
		}
	}
	
	public RelationshipGenerator(Graph g, int id){
		_graph = g;
		_workerId = id;
	}
}
