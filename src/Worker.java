
public class Worker implements Runnable {
	private Thread _thread;
	private Graph _graph;
	private int _numberSamplesPerThread;
	private int[] _samples;
	
	private void generateSamples(){
		int numberNodes = _graph.getNumNodes();
		_samples = new int[_numberSamplesPerThread];
		for (int count = 0; count < _numberSamplesPerThread; count++){
			
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int count = 0; count < _numberSamplesPerThread; count++)
			_graph.search(0, _samples[count]);
	}
	
	 
	 public void start (int workerId)
	   {
	      System.out.println("Starting Thread-" + Integer.toString(workerId));
	      if (_thread == null)
	      {
	    	 _thread = new Thread (this, Integer.toString(workerId));
	    	 _thread.start ();
	      }
	   }
	
	public Worker(Graph g, int samples){
		_graph = g; 
		_numberSamplesPerThread = samples;
		generateSamples();
	}
}
