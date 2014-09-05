import java.util.*;
import java.io.*;

public class Graph {
	
	private static String DATA_PATH = "";
	private int NUM_NODES;
	private int SCALE ; 
	private int BRANCH_FACTOR;
	private static final int NULL_NODE = -1;
	private Node[] _nodes;
	
	public int getNumberEdges(int nodeId){
		return (_nodes[nodeId].getEdgeList().size());
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
	
	public void generateNodes(){
		NUM_NODES = getNumNodes();
		_nodes = new Node[NUM_NODES];		
		for (int count = 0; count < NUM_NODES; count++){
			_nodes[count] = new Node(count);
		}
	}
	
	public void createEdgeBetween(int from, int to){
		_nodes[from].getEdgeList().add(_nodes[to]);
		// adding an edge to the from node to the to node
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
		int[] parent = new int[NUM_NODES];
		ArrayList<Node> childrenList;
		int[] vertices = new int[NUM_NODES];
		int currentIndex = 0, addIndex = 0, nodeId;
		
		int count;
		Integer currentNode;
		for (count = 0; count < NUM_NODES; count++){
			parent[count] = NULL_NODE;
			vertices[currentIndex] = NULL_NODE;
		}
		
		vertices[addIndex] = source;
		parent[source] = source;
		
		while(currentIndex < NUM_NODES){
			currentNode = vertices[currentIndex];
			currentIndex++;
			if(currentNode == NULL_NODE)
				break;
			childrenList = _nodes[(int)currentNode].getEdgeList();
			for (Node child : childrenList){
				nodeId = child.getNodeId();
				if(parent[nodeId] == NULL_NODE){
					parent[nodeId] = currentNode;
					if(addIndex < NUM_NODES){
						vertices[addIndex] = nodeId;
						addIndex++;
					}
				} 
			}
			if (childrenList.contains(destination))
				break;
		}
		System.out.println("Source : " + source + ",Destination : " + destination 
				+ "Verticies traversed :: " + addIndex);
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

