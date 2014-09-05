import java.util.*;
import java.io.*;

public class Graph {
	
	private static String DATA_PATH = "";
	private int NUM_NODES;
	private ArrayList<Integer>[] _graphNodes;
	private int SCALE ; 
	private int BRANCH_FACTOR;
	private static final int NULL_NODE = -1;
	
	public int getNumberEdges(int nodeId){
		return (_graphNodes[nodeId].size());
	}
	
	public void setDataPath(){
		DATA_PATH = "/home/tandon/data/toy_" + Integer.toString(SCALE) + "_" + 
				Integer.toString(BRANCH_FACTOR) + ".txt";
	}
	
	public int getNumNodes(){
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
		System.out.println("Created an edge between " + from + "," + to);
		if(from == 0){
			System.out.println("Yes Zero Is Present.");
		}
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
	
	public ArrayList<Integer> addToVertextList(ArrayList<Integer> childrenList, ArrayList<Integer> vertexList,
			int[] parent, Integer currentNode){

		return vertexList;
	}
	
	public int[] search(int source, int destination){
		System.out.println("Source:" + source + "Destination:" + destination);
		int[] parent = new int[NUM_NODES];
		ArrayList<Integer> childrenList = new ArrayList<Integer>();
		ArrayList<Integer> vertexList = new ArrayList<Integer>();
		
		int count;
		Integer currentNode;
		for (count = 0; count < NUM_NODES; count++){
			parent[count] = NULL_NODE;
		}
		vertexList.add(source);
		parent[source] = source;
		
		ListIterator<Integer> iterator = vertexList.listIterator();
		while(iterator.hasNext()){
			currentNode = iterator.next();
			if(currentNode == NULL_NODE)
				break;
			childrenList = _graphNodes[(int)currentNode];
			for (Integer child : childrenList){
				if(parent[child] == NULL_NODE){
					parent[child] = currentNode;
					iterator.add(child);
				} 
			}
			if (childrenList.contains(destination))
				break;
		}
		System.out.println("Verticies traversed" + vertexList.size());
		return parent;
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

