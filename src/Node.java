import java.util.ArrayList;


public class Node {
	private int _nodeId;
	private ArrayList<Node> _edgeList;
	
	public int getNodeId() { return _nodeId; }
	public void setNodeId(int nodeId) { _nodeId = nodeId; }
	public ArrayList<Node> getEdgeList() { return _edgeList; }
	public Node(int id){
		setNodeId(id);
		_edgeList = new ArrayList<Node>();
	}
	public boolean isParentOf(int nodeId){
		for (Node child : _edgeList){
			if (child.getNodeId() == nodeId)
				return true;
		}
		return false;
	}
	public Node getChildIfExists(int nodeId){
		for (Node child : _edgeList){
			if (child.getNodeId() == nodeId)
				return child;
		}
		return null;
	}
}
