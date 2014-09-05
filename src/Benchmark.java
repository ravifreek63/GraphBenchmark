
public class Benchmark {
	private Graph _graph;
	
	public void createGraph(String scale, String branchFactor){
		_graph = new Graph(scale, branchFactor);
		
	}
	
	public static void main(String[] args){
		Benchmark benchmark = new Benchmark();
		benchmark.createGraph(args[0], args[1]);	
	}

}
