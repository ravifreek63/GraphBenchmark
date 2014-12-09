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
	
	public void createGraphWithNodes(String scale, String branchFactor, String numNodes, int fraction){
		_graph = new Graph("-1", branchFactor, numNodes, fraction);
	}	
	
	public static void main(String[] args){
		Benchmark benchmark = new Benchmark();
		int fraction = 1;
		if(args.length == 6){
			fraction = Integer.parseInt(args[5]);
		}
		if(args.length<3){
			System.out.println("Insufficient number of arguments");
			System.exit(-1);
		}
		benchmark.createGraphWithNodes("", args[1], args[0], fraction);
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
		if(args.length>=5){
			if(args[4].trim().equals("1")){	
				System.out.println("Triggering Garbage Collection");
				System.gc();
			}
		}
		Statistics.setNumberThreads(_numberThreads);
		// Starting Worker Threads 
		System.out.println("Starting Threads ..... ");
		long lStartTime = System.nanoTime();
		ExecutorService executor = Executors.newFixedThreadPool(_numberThreads);		
		for(int count = 0; count < _numberThreads; count++){
			Worker worker = new Worker(_graph, _numberSamplesPerThread, count);
			executor.execute(worker);
		}
		executor.shutdown();
		while(!executor.isTerminated());
		System.out.println("Workers done ..");
		long lEndTime = System.nanoTime();
		long difference = lEndTime - lStartTime;
		Statistics.setGraphSearchTime(difference);
		Statistics.printStats();
	}
}
