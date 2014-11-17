import java.util.ArrayList;
import java.util.Random;

public class Worker implements Runnable {
	private Graph _graph;
	private int _numberSamplesPerThread;
	private int[] _samples;
	private int _workerId;
	private int _edgesTraversed;
	
	private void searchGraph(){
		ArrayList<Node> allNodes = new ArrayList<Node>();
		System.out.println("Searching Graph For Thread -" + _workerId);
		for (int count = 0; count < _numberSamplesPerThread; count++){
			ArrayList<Node> nodes = _graph.find(_samples[count], _workerId);
			if(nodes != null){
				allNodes.addAll(nodes);
				/*if(node.getNodeId() != _samples[count]){
					System.out.println("Something is wrong. Mismatch in nodeId.");
				}*/
			}
			if((count % 1000) == 0)
			System.out.println("Count:" + count);
		}
		System.out.println("Size:" + allNodes.size());
	}
	
	private void generateSamples(){
		System.out.println("Generating Samples For Thread -" + _workerId);
		int numberNodes = _graph.getNumNodes();
		Random random = new Random();
		int sample = -1;
		_samples = new int[_numberSamplesPerThread];
		for (int count = 0; count < _numberSamplesPerThread; count++){
//			System.out.println("Count:" + count);
			do{
				sample = random.nextInt(numberNodes);
			} while(_graph.getNumberEdges(sample) == 0 && sample != _graph.getRoot().getNodeId());
			_samples[count] = sample;
		}
	}
	
	@Override
	public void run() {
		System.out.println("Running Thread -" + Integer.toString(_workerId));
		if(_workerId==0){		
			System.out.println("Triggering system.gc().");
			System.gc();
			System.out.println("system.gc() triggered.");
		} 
			generateSamples();
			searchGraph();
	}
	
	public Worker(Graph g, int samples, int workerId){
		_graph = g; 
		_numberSamplesPerThread = samples;
		_workerId = workerId;
		_edgesTraversed = 0;
	}
}
