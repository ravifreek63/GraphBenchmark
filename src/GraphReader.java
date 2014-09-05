import java.util.*;
import java.io.*;

public class GraphReader {
	
	private static String DATA_PATH = "";
	private int NUM_NODES;
	private ArrayList<Integer>[] _graphNodes;
	private int SCALE ; 
	private int BRANCH_FACTOR;
	
	public void setDataPath(String exponent){
		DATA_PATH = "/home/tandon/data/toy_" + exponent.toString() + "_" + Integer.toString(SCALE) + ".txt";
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
	
	public static void main(String[] args){
		if(args.length < 2){
			System.out.println("Number of arguments lesser than 2");
			System.exit(-1);
		}
		GraphReader graphReader = new GraphReader();
		try {
				graphReader.setScale(Integer.parseInt(args[0]));
				graphReader.setBranchFactor(Integer.parseInt(args[1]));
		} catch (NumberFormatException e){
			System.out.println(e.toString());
		}	
		graphReader.setDataPath(args[0]);
		graphReader.generateNodes();
		graphReader.createRelationships();
	}

}

