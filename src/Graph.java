import java.util.*;
import java.io.*;

public class Graph {
	private Node _root;
	private static String DATA_PATH = "";
	private int NUM_NODES;
	private int SCALE ; 
	private int BRANCH_FACTOR;
	private static final int NULL_NODE = -1;
	private Node[]_nodes;
	
	public Node getRoot() { 
		int nodeId = 0;
		while(true){
			if(_nodes[nodeId] != null && _nodes[nodeId].getEdgeList().size() > 2){
				return _nodes[nodeId];
			}
			nodeId++;
		}
	}
	
	public Node find(int searchNodeId){
		ArrayList<Node> childrenList;
		ArrayList<Node> list1 = new ArrayList<Node>();
		ArrayList<Node> list2 = new ArrayList<Node>();
		boolean[] seenNode = new boolean[NUM_NODES];
		Node childNode = null;
		list1.add(_root);
		int uniqueNodesSeen = 0;
		boolean found = false;
		// Iterate over list1 and add children to list2
		while(!found){
			list2 = new ArrayList<Node>();
			for(Node currentNode:  list1){				
				childrenList = currentNode.getEdgeList();
				for (Node child : childrenList){
					if(!seenNode[child.getNodeId()]){
						list2.add(child);
						seenNode[child.getNodeId()] = true;
						uniqueNodesSeen++;
				} 
			}
				childNode = currentNode.getChildIfExists(searchNodeId);
				if(childNode != null){
					found = true;
				    break;
				}    
				    
			}
			list1 = new ArrayList<Node>();
			for(Node currentNode: list2){				
				childrenList = currentNode.getEdgeList();
				for (Node child : childrenList){
					if(!seenNode[child.getNodeId()]){
						list1.add(child);
						seenNode[child.getNodeId()] = true;
						uniqueNodesSeen++;
				} 
			}
				childNode = currentNode.getChildIfExists(searchNodeId);
				if(childNode != null){
					found = true;
					break;
				}
			}
			if((list1.size() == 0 && list2.size() == 0)){
				break;
			}
		}
			System.out.println("Unique Nodes Visited : " + uniqueNodesSeen);
			return childNode;
	}
	
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
	
	private int getValue(double n, double f){
		if(n >= f)
			return 0;
		return 1;
	}
	
	public int[][] generateRandomGraph(){
		Random random = new Random();
		long lStartTime = System.nanoTime();
		int numberOfEdges = getNumNodes() * BRANCH_FACTOR;
		int[][] edgeList = new int[numberOfEdges][2];
		for(int i=0; i < numberOfEdges; i++){
			do{
				edgeList[i][0] = random.nextInt(NUM_NODES);
				edgeList[i][1] = random.nextInt(NUM_NODES);
			} while(edgeList[i][0]  == edgeList[i][1]);
		}
		long lEndTime = System.nanoTime();
		long difference = lEndTime - lStartTime; 
		System.out.println("Total Time Taken : " + (double)difference / Math.pow(10, 9));
		return edgeList;
	}
	
	public int[][] generateGraph(){
		long lStartTime = System.nanoTime();
		System.out.println("Generating the graph.");
		int numberOfEdges = getNumNodes() * BRANCH_FACTOR;
		double _A_Param = 0.57, _B_Param = 0.19, _C_Param = 0.19;
		double ab = _A_Param + _B_Param;
		double _c_norm = _C_Param/(1 - ab);
		double _a_norm = _A_Param/(ab);
		int[][] edgeList = new int[numberOfEdges][2];
		int ii_bit;
		int jj_bit;
		double l_value;
		Random random = new Random();
		for(int count = 1; count <= SCALE; count++){
			System.out.println("Scale:" + count);
			for(int i=0; i < numberOfEdges; i++){
				ii_bit = getValue(random.nextDouble(), ab);
				l_value = _c_norm * ii_bit + _a_norm * (1-ii_bit);
				jj_bit = getValue(random.nextDouble(), l_value);
				edgeList[i][0] = edgeList[i][0] + (int)Math.pow(2, count - 1) * ii_bit;     
				edgeList[i][1] = edgeList[i][1] + (int)Math.pow(2, count - 1) * jj_bit;
			}
		}
		long lEndTime = System.nanoTime();
		long difference = lEndTime - lStartTime;
		System.out.println("Graph generation done. Time taken : " + (double)difference/Math.pow(10, 9));
		return edgeList;
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
	
	
	public void createRelationships(int[][] edgeList){
		long lStartTime = System.nanoTime();
		int numberOfEdges = getNumNodes() * BRANCH_FACTOR;
		for(int i=0; i < numberOfEdges; i++){
			createEdgeBetween(edgeList[i][0], edgeList[i][1]);
		}
		long lEndTime = System.nanoTime();
		long timeDifference = lEndTime - lStartTime;
		System.out.println("TimeTaken for creating the relationships : " 
		+ (double)timeDifference / Math.pow(10, 9));
	}
	
	public void createRelationshipsFile(){
		long numberOfRelationships = getNumNodes() * BRANCH_FACTOR;
		long count = 0;
		double frac = 0.1;
		FileInputStream fis = null;
		int from, to;
		try {
			File file = new File(DATA_PATH);
			fis = new FileInputStream(file);
		    byte[] data = new byte[(int)file.length()];
		    fis.read(data);
		    String s = new String(data, "UTF-8");
		    	 String[] lines = s.split("\\n");
		    	 for(int lineNumber = 0; lineNumber < lines.length; lineNumber++){
		    		 String[] parts = lines[lineNumber].split("\\s+");
		    		 from = Integer.parseInt(parts[1]);
				        to = Integer.parseInt(parts[2]);
				        if(from != to)
				        	createEdgeBetween(from, to);
				        count++;
				        if((double)count/numberOfRelationships  >= frac){
				        	System.out.println("frac : " + frac + " read.");
				        	frac += 0.1;
				        }
		    	 }
		} catch(IOException e){
		  e.printStackTrace();
		}finally {
		    try {
		    	 fis.close();
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
		Node[] vertices = new Node[NUM_NODES];
		int currentIndex = 0, addIndex = 0, nodeId;
		
		int count;
		Node currentNode;
		for (count = 0; count < NUM_NODES; count++){
			parent[count] = NULL_NODE;
			vertices[currentIndex] = new Node(NULL_NODE);
		}
		
		vertices[addIndex] = _nodes[source];
		parent[source] = source;
		
		while(currentIndex < NUM_NODES){
			currentNode = vertices[currentIndex];
			currentIndex++;
			if(currentNode.getNodeId() == NULL_NODE)
				break;
			childrenList = currentNode.getEdgeList();
			for (Node child : childrenList){
				nodeId = child.getNodeId();
				if(parent[nodeId] == NULL_NODE){
					parent[nodeId] = currentNode.getNodeId();
					if(addIndex < NUM_NODES){
						vertices[addIndex] = child;
						addIndex++;
					}
				} 
			}
			if (childrenList.contains(destination))
				break;
		}
		System.out.println("Source : " + source + ",Destination : " + destination 
				+ ", Verticies traversed :: " + addIndex);
		return parent;
	}
	
	public Graph(String scale, String branchFactor){
		try{
			int _scale = Integer.parseInt(scale);
			int _branchFactor = Integer.parseInt(branchFactor);
				setScale(_scale);
				setBranchFactor(_branchFactor);
				setDataPath();
				System.out.println("Generating Nodes.");
				generateNodes();
				System.out.println("Generating Nodes Done.");
				System.out.println("Creating Relationships.");
				long lStartTime = System.nanoTime();
				createRelationships(generateRandomGraph());
				long lEndTime = System.nanoTime();
				long timeDifference = lEndTime - lStartTime;
				System.out.println("Total time taken for the creating of the graph in seconds: " 
				+ (double)timeDifference/Math.pow(10, 9));
				System.out.println("Creating Relationships Done.");
				_root = getRoot();
			} catch (NumberFormatException e){
				System.out.print(e.toString());
			}
	}
}

