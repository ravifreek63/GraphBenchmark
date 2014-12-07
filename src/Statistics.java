
public class Statistics {
	private static long _graphGenerationTime;
	private static long _graphSearchTime;
	private static int[] _totalEdgesTraversed;
	private static int _numberThreads;
	private static long _gcTime;
	
	private static double toSeconds(long v){
		return ((double)(v)/Math.pow(10, 9));
	}
	
	private static double toMilliseconds(long v){
		return ((double)(v)/Math.pow(10, 6));
	}
	
	public static void setGraphGenerationTime(long t){
		_graphGenerationTime = t;
	}
	
	public static void setGraphSearchTime(long t){
		_graphSearchTime = t;
	}

	public static void setGCTime(long t){
		_gcTime = t;
	}
	
	public static void incrementEdgesTraversed(int v, int index){
		_totalEdgesTraversed[index] += v;
	}
	
	public static void setNumberThreads(int n){
		_numberThreads = n;
		_totalEdgesTraversed = new int[_numberThreads];
	}
	
	private static int totalEdgesTraversed(){
		int sum = 0;
		for(int count = 0; count < _numberThreads; count++){
			sum += _totalEdgesTraversed[count];
		}
		return sum;
	}

	public static void printStats(){
		System.out.println("Printing Statistics ......");
		System.out.println("Time taken for graph generation : " + toSeconds(_graphGenerationTime) + " seconds.");
		System.out.println("Time taken for graph search : " + toSeconds(_graphSearchTime) + " seconds.");
		System.out.println("Time taken for garbage collection : " + toSeconds(_gcTime) + " seconds.");
		System.out.println("Total number of edges traversed : " + totalEdgesTraversed());
		System.out.println("Edge traversal rate : " + (double)totalEdgesTraversed()/toMilliseconds(_graphSearchTime));
	}
}
