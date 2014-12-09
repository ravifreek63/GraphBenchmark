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
			}
		}
		System.out.println("Size:" + allNodes.size());
	}
	
	private void generateSamples(){
		double frac = (double)_graph.getFraction()/100;
		System.out.println("Generating Samples For Thread -" + _workerId + ", fraction ::" + frac);
		int numberNodes = (int)((double)_graph.getNumNodes() * frac);
		Random random = new Random();
		int sample = -1;
		_samples = new int[_numberSamplesPerThread];
		for (int count = 0; count < _numberSamplesPerThread; count++){
			do{
				sample = random.nextInt(numberNodes);
			} while(sample != _graph.getRoot().getNodeId());
			_samples[count] = sample;
		}
	}
	
	@Override
	public void run() {
		System.out.println("Running Thread -" + Integer.toString(_workerId));
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
