import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Graph {
	public HashMap<Integer, Node> myGraph = new HashMap<>();

	Comparator<Node> byDistance = (Node n1,Node n2)
			-> Integer.compare(n1.distanceToGoal, n2.distanceToGoal);
	
	public void addNode(Node node) {
		if (node == null || myGraph.containsKey(node.roomNumber)) {
			System.err.println("Room already exists");
			return;
		}
		myGraph.put(node.roomNumber, node);
	}
	
	public void addLink(int firstRoom, int secondRoom, String action, int cost, boolean isBiDirectional) {
		if (myGraph.containsKey(firstRoom) && myGraph.containsKey(secondRoom)) {
			Node startNode = myGraph.get(firstRoom);
			Node endNode = myGraph.get(secondRoom);
			startNode.links.add(new Link(secondRoom, cost, action));
						
			if (isBiDirectional) {
				endNode.links.add(new Link(firstRoom, cost , action));
			}
		}else {
			System.err.println("Wrong or missing node names");
		}
	}
	
	public Node getNode(int startName) {
		return myGraph.get(startName);
	}
	
	public boolean containsNode(int roomNumber) {
		return myGraph.containsKey(roomNumber);
	}
	
	public ArrayList<Node> getLinkedNodes(int roomNumber){
		ArrayList<Node> linkedNodes = new ArrayList<>();
		Node node = myGraph.get(roomNumber);
		for (Link l : node.links) {
			linkedNodes.add(myGraph.get(l.toNodeName));
		}
		return linkedNodes;
	}

    public void sortByDistance(ArrayList<Node> list) {	
		list.sort(byDistance);
	}
	
	public void resetAllNodes() {
		myGraph.forEach((k,v) -> v.reset());
	}
	
	public boolean checkFloor(Node room1, Node room2) {
		if (!room2.roomType.equals("transit") && room1.floorNumber != room2.floorNumber) {
			return false;
		}
		return true;
	}
	
    public double findDistance(int nameOne, int nameTwo) {	
		Node nodeOne = getNode(nameOne);
		Node nodeTwo = getNode(nameTwo);
		
		int distance = (int) Math.sqrt(Math.pow(nodeOne.x - nodeTwo.x, 2)
				+ Math.pow(nodeOne.y - nodeTwo.y, 2));
		return distance;
	}
}
