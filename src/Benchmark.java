import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;


public class Benchmark {
	private static Graph _graph;
	private static int _numberThreads;
	private static int _numberSamplesPerThread;
	
	public Graph getGraph(){
		return _graph;
	}
	
	public void createGraph(String scale, String branchFactor){
		_graph = new Graph(scale, branchFactor);
	}
	
	public static void main(String[] args){
		Benchmark benchmark = new Benchmark();
		benchmark.createGraph(args[0], args[1]);
		if(args.length < 3){
			_numberThreads = 8;
		} else {
			try{
				_numberThreads = Integer.parseInt(args[2]);
			}catch (NumberFormatException e){
				System.out.print(e.toString());
			}
		}
		if(args.length < 4){
			_numberSamplesPerThread = 64;
		} else {
			try{
				_numberSamplesPerThread = Integer.parseInt(args[3]);
			}catch (NumberFormatException e){
				System.out.print(e.toString());
			}
		}	

		// Starting Worker Threads 
		long lStartTime = System.nanoTime();
		ExecutorService executor = Executors.newFixedThreadPool(_numberThreads); 
		for(int count = 0; count < _numberThreads; count++){
			Worker worker = new Worker(_graph, _numberSamplesPerThread, count);
			executor.execute(worker);
		}
		executor.shutdown();
		while(!executor.isTerminated());
		long lEndTime = System.nanoTime();
		long difference = lEndTime - lStartTime;
		System.out.println("Total time taken in milliseconds : " + difference);
	}
}
