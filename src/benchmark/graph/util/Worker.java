package benchmark.graph.util;
import java.util.ArrayList;
import java.util.Random;

public class Worker implements Runnable {
	private Graph _graph;
	private int _numberSamplesPerThread;
	private int[] _samples;
	private int _workerId;
	private int _edgesTraversed;
	private StatsPrinter _statsPrinter;
	
	private void searchGraph(){
//		System.out.println("Searching Graph For Thread -" + _workerId);
		for (int count = 0; count < _numberSamplesPerThread; count++){
				int edgesTraversed = _graph.find(_samples[count], _workerId);
				if(edgesTraversed<0)
					break;
				Statistics.incrementQueriesDone(1, _workerId);
			}
		}
	
	private void generateSamples(){
		double frac = (double)_graph.getFraction()/100;
		int numberNodes = (int)((double)_graph.getNumNodes() * frac);
		Random random = new Random();
		int sample = -1;
		_samples = new int[_numberSamplesPerThread];
		for (int count = 0; count < _numberSamplesPerThread; count++){
			do{
				sample = random.nextInt(numberNodes);
			} while(sample == _graph.getRoot().getNodeId());
			_samples[count] = sample;
		}
	}
	
	@Override
	public void run() {
//		System.out.println("Running Thread -" + Integer.toString(_workerId));
		if(_workerId==0){			
			_statsPrinter.run();
		} else {
			generateSamples();
			searchGraph();
			_statsPrinter.threadDone(_workerId);
		}
	}
	
	public Worker(Graph g, int samples, int workerId, StatsPrinter s){
		_graph = g; 
		_numberSamplesPerThread = samples;
		_workerId = workerId;
		_edgesTraversed = 0;
		_statsPrinter =s ;
	}
}
