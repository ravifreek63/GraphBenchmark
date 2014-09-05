import java.util.Random;

public class Worker implements Runnable {
	private Thread _thread;
	private Graph _graph;
	private int _numberSamplesPerThread;
	private int[] _samples;
	private int _workerId;
	
	private void searchGraph(){
		System.out.println("Searching Graph For Thread -" + _workerId);
		for (int count = 0; count < _numberSamplesPerThread; count++){
			_graph.search(0, _samples[count]);
		}
	}
	
	private void generateSamples(){
		System.out.println("Generating Samples For Thread -" + _workerId);
		int numberNodes = _graph.getNumNodes();
		Random random = new Random();
		int sample = -1;
		_samples = new int[_numberSamplesPerThread];
		for (int count = 0; count < _numberSamplesPerThread; count++){
			do{
				sample = random.nextInt(numberNodes);
			} while(_graph.getNumberEdges(sample) == 0);
			_samples[count] = sample;
		}
	}
	
	@Override
	public void run() {
		System.out.println("Running Thread -" + Integer.toString(_workerId));
		  generateSamples();
		  searchGraph();
	}
	
	 
	 public void start (int workerId)
	   {
	      System.out.println("Starting Thread -" + Integer.toString(workerId));
	      if (_thread == null)
	      {
	    	 _thread = new Thread (this, Integer.toString(workerId));
	    	 _thread.start ();
	      }
	      _workerId = workerId;
	   }
	
	public Worker(Graph g, int samples){
		_graph = g; 
		_numberSamplesPerThread = samples;

	}
}
