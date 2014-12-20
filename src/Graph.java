import java.util.*;
import java.io.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Graph {
	private Node _root;
	private static String DATA_PATH = "";
	private int NUM_NODES;
	private int SCALE ; 
	private int BRANCH_FACTOR;
	private int NUMBER_EDGES;
	private static final int NULL_NODE = -1;
	private Node[]_nodes;
	private int[][] _edgeList;
	private int _numberThreads; // number of threads for the graph generation
	private int _fraction;
	private StatsPrinter _statsPrinter;
	
	public int getFraction(){return _fraction;}
	public int getEdge(int index, int number){
		return _edgeList[index][number];
	}
	
	public int getNumberThreads(){
		return _numberThreads;
	}
	
	public void setRoot() {
		int nodeId = 0;
		while(true){
			if(_nodes[nodeId] != null && _nodes[nodeId].getEdgeList().size() > 2){
				_root = _nodes[nodeId];
				break;
			}
			System.out.println("EdgeListSize::" + _nodes[nodeId].getEdgeList().size());
			nodeId++;
		}
	}
	
	public Node getRoot() { 
		return _root;
	}
	
	public int find(int searchNodeId, int workerId){
		int edgesTraversed = 0; // variable 3 is edgesTraversed
		ArrayList<Node> childrenList; //
		ArrayList<Node> list1 = new ArrayList<Node>(); // variable 5 is list1
		ArrayList<Node> list2 = new ArrayList<Node>(); // variable 6 is list2 
		// (aload_0) is called to get the NUM_NODES field here - Object Access #1
		HashMap<Integer, Boolean> seenNodes = new HashMap<Integer, Boolean>(); 
		Node childNode = null; // variable 8 is childNode
		list1.add(_root); // Object Access #2, field 9 is root, (aload_0)
		seenNodes.put(_root.getNodeId(), true);
		//  Object Access #3 invoking method on list1 object(aload n)
//		int uniqueNodesSeen = 0;
		boolean found = false; // found is variable at position 10 
		// Iterate over list1 and add children to list2
		while(!found){
			list2.clear();
			for(Node currentNode:  list1){				
				childrenList = currentNode.getEdgeList();
				Statistics.incrementEdgesTraversed(childrenList.size(), workerId);
				edgesTraversed += childrenList.size();
				for (Node child : childrenList){
					if(seenNodes.get(child.getNodeId()) == null){
						list2.add(child);
					seenNodes.put(child.getNodeId(), true);
//						uniqueNodesSeen++;
				} 
			}
				childNode = currentNode.getChildIfExists(searchNodeId);
				if(childNode != null){
					found = true;
				    break;
				}    
				    
			}
			list1.clear();
			for(Node currentNode: list2){				
				childrenList = currentNode.getEdgeList();
				Statistics.incrementEdgesTraversed(childrenList.size(), workerId);
				edgesTraversed += childrenList.size();
				for (Node child : childrenList){
					if(seenNodes.get(child.getNodeId()) == null){
						list1.add(child);
						seenNodes.put(child.getNodeId(), true);
//						uniqueNodesSeen++;
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
			return edgesTraversed;
	}
	
	public int getNumberEdges(int nodeId){
		return (_nodes[nodeId].getEdgeList().size());
	}
	
	public void setDataPath(){
		DATA_PATH = "/home/tandon/data/toy_" + Integer.toString(SCALE) + "_" + 
				Integer.toString(BRANCH_FACTOR) + ".txt";
	}
	
	public void setNumNodes(int n){
		NUM_NODES = n;
	}
	
	public int getNumNodes(){
//		if(SCALE==-1)
			return NUM_NODES;
//		return (int)Math.pow ( 2, SCALE);
	}
	
	public void setScale(int scale){
		SCALE = scale;
	}
	 
	public int getBranchFactor(){
		return BRANCH_FACTOR;
	}
	
	public void setBranchFactor(int factor){
		BRANCH_FACTOR = factor;	
	}
	
	public void setNumberEdges(){
		NUMBER_EDGES = getNumNodes() * BRANCH_FACTOR;
	}
	
	public int getEdgeCount(){
		return NUMBER_EDGES;
	}
	
	private int getValue(double n, double f){
		if(n >= f)
			return 0;
		return 1;
	}
	
	public int[][] generateRandomGraphMT(){
		Random random = new Random();
		long lStartTime = System.nanoTime();
		int numberOfEdges = getNumNodes() * BRANCH_FACTOR;
		int[][] edgeList = new int[numberOfEdges][2];
		
		return edgeList;
	}
	
	public int[][] generateRandomGraph(){
		Random random = new Random();
		long lStartTime = System.nanoTime();
		int[][] edgeList = new int[NUMBER_EDGES][2];
		for(int i=0; i < NUMBER_EDGES; i++){
			do{
				edgeList[i][0] = random.nextInt(NUM_NODES);
				edgeList[i][1] = random.nextInt(NUM_NODES);
			} while(edgeList[i][0]  == edgeList[i][1]);
		}
		long lEndTime = System.nanoTime();
		long difference = lEndTime - lStartTime; 
		System.out.println("Total Time Taken for generating the random graph"
				+ " : " + (double)difference / Math.pow(10, 9));
		return edgeList;
	}
	
	public int[][] generateGraph(){
		long lStartTime = System.nanoTime();
		System.out.println("Generating the graph.");
		double _A_Param = 0.57, _B_Param = 0.19, _C_Param = 0.19;
		double ab = _A_Param + _B_Param;
		double _c_norm = _C_Param/(1 - ab);
		double _a_norm = _A_Param/(ab);
		int[][] edgeList = new int[NUMBER_EDGES][2];
		int ii_bit;
		int jj_bit;
		double l_value;
		Random random = new Random();
		for(int count = 1; count <= SCALE; count++){
			System.out.println("Scale:" + count);
			for(int i=0; i < NUMBER_EDGES; i++){
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
		System.out.println("Generating " + NUM_NODES + " nodes.");
		_nodes = new Node[NUM_NODES];		
		for (int count = 0; count < NUM_NODES; count++){
			_nodes[count] = new Node(count, getBranchFactor());
		}		
	}
	
	public void createEdgeBetween(int from, int to){
		_nodes[from].getEdgeList().add(_nodes[to]);
	}
	
	public void createRelationships(){
		long lStartTime = System.nanoTime();
		for(int i=0; i < NUMBER_EDGES; i++){
			createEdgeBetween(_edgeList[i][0], _edgeList[i][1]);
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
	public void init(int _scale, int _branchFactor, int numNodes){
		setScale(_scale);
		setBranchFactor(_branchFactor);
		setNumNodes(numNodes);
		setNumberEdges();
//		_edgeList = new int[NUMBER_EDGES][2];
		_numberThreads = 8;
	}	
	
	public void init(int _scale, int _branchFactor){
		System.out.println("Setting the parameters.");
		setScale(_scale);
		setBranchFactor(_branchFactor);
		setNumberEdges();
		_numberThreads = 8;
	}
	
	public void setEdge(int from, int to, int index){
		_edgeList[index][0] = from;
		_edgeList[index][1] = to;
	}
	
	public Graph(String scale, String branchFactor, String numNodes, int fraction){
		try{
			_fraction = fraction;
			init(Integer.parseInt(scale), Integer.parseInt(branchFactor),  Integer.parseInt(numNodes)); 
			System.out.println("Generating Nodes.");
			setNumNodes(Integer.parseInt(numNodes));
			generateNodes();
			System.out.println("Generating Nodes Done.");
			System.out.println("Generating Graph.");
			long lStartTime = System.nanoTime();
			ExecutorService executor = Executors.newFixedThreadPool(_numberThreads); 
			/*for(int count = 0; count < _numberThreads; count++){
				GraphGenerator worker = new GraphGenerator(this, count);
				executor.execute(worker);
			}
			executor.shutdown();
			while(!executor.isTerminated());
			System.out.println("Generating Graph Done.");*/
			long lEndTime = System.nanoTime();
			long timeDifference = lEndTime - lStartTime;
			lStartTime = System.nanoTime();				
			executor = Executors.newFixedThreadPool(_numberThreads); 
			for(int count = 0; count < _numberThreads; count++){
				RelationshipGenerator worker = new RelationshipGenerator(this, count);
				executor.execute(worker);
			}
			executor.shutdown();
			while(!executor.isTerminated());
			lEndTime = System.nanoTime();
			timeDifference = lEndTime - lStartTime;
			Statistics.setGraphGenerationTime(timeDifference);
			System.out.println("Creating Relationships Done.");
			setRoot();
			_nodes = null;
		} catch (NumberFormatException e){
			System.out.print(e.toString());
		}
	}
	
	public Graph(String scale, String branchFactor){
		try{
				init(Integer.parseInt(scale), Integer.parseInt(branchFactor)); 
				System.out.println("Generating Nodes.");
				generateNodes();
				System.out.print("Generating Nodes Done.");
				System.out.println("Generating Graph.");
				long lStartTime = System.nanoTime();
				ExecutorService executor = Executors.newFixedThreadPool(_numberThreads); 
				for(int count = 0; count < _numberThreads; count++){
					GraphGenerator worker = new GraphGenerator(this, count);
					executor.execute(worker);
				}
				executor.shutdown();
				while(!executor.isTerminated());
				System.out.println("Generating Graph Done.");
				long lEndTime = System.nanoTime();
				long timeDifference = lEndTime - lStartTime;
				System.out.println("Creating Relationships.");
				lStartTime = System.nanoTime();				
				executor = Executors.newFixedThreadPool(_numberThreads); 
				for(int count = 0; count < _numberThreads; count++){
					RelationshipGenerator worker = new RelationshipGenerator(this, count);
					executor.execute(worker);
				}
				executor.shutdown();
				while(!executor.isTerminated());
				lEndTime = System.nanoTime();
				timeDifference = lEndTime - lStartTime;
				Statistics.setGraphGenerationTime(timeDifference);
				System.out.println("Creating Relationships Done.");
				_root = getRoot();
			} catch (NumberFormatException e){
				System.out.print(e.toString());
			}
	}
}

