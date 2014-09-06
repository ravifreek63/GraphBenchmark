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
	
	public void generateGraph(){
		
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
		long numberOfRelationships = getNumNodes() * BRANCH_FACTOR;
		long count = 0;
		double frac = 0.1;
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
				createRelationships();
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

