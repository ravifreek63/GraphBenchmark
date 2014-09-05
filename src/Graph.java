import java.util.*;
import java.io.*;

public class Graph {
	
	private static String DATA_PATH = "";
	private int NUM_NODES;
	private ArrayList<Integer>[] _graphNodes;
	private int SCALE ; 
	private int BRANCH_FACTOR;
	
	public void setDataPath(){
		DATA_PATH = "/home/tandon/data/toy_" + Integer.toString(SCALE) + "_" + 
				Integer.toString(BRANCH_FACTOR) + ".txt";
	}
	
	private int getNumNodes(){
		return (int)Math.pow ( 2, SCALE);
	}
	
	public void setScale(int scale){
		SCALE = scale;
	}
	 
	public void setBranchFactor(int factor){
		BRANCH_FACTOR = factor;	
	}
	
	@SuppressWarnings("unchecked")
	public void generateNodes(){
		NUM_NODES = getNumNodes();
		_graphNodes = (ArrayList<Integer>[])new ArrayList[(int) NUM_NODES];
		for (int count = 0; count < NUM_NODES; count++){
			_graphNodes[count] = new ArrayList<Integer>();
		}
	}
	
	public void createEdgeBetween(int from, int to){
		_graphNodes[from].add(to); // adding an edge to the from node to the to node
	}
	
	public void createRelationships(){
		BufferedReader br = null; 
		try {
		    File file = new File(DATA_PATH);
		    br = new BufferedReader(new FileReader(file));
		    int from, to;
		    String  thisLine = null;
		    while ((thisLine = br.readLine()) != null) {
		      String[] parts = thisLine.split("\\s+");
		        from = Integer.parseInt(parts[1]);
		        to = Integer.parseInt(parts[2]);
		        if(from != to)
		        	createEdgeBetween(from, to);
		   }
		} catch(IOException e){
		  e.printStackTrace();
		}finally {
		    try {
		        br.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
	}
	
	public void search(int source, int destination){
		
	}
	
	public Graph(String scale, String branchFactor){
		try{
			int _scale = Integer.parseInt(scale);
			int _branchFactor = Integer.parseInt(branchFactor);
				setScale(_scale);
				setBranchFactor(_branchFactor);
				setDataPath();
				generateNodes();
				createRelationships();
			} catch (NumberFormatException e){
				System.out.print(e.toString());
			}
	}
}

