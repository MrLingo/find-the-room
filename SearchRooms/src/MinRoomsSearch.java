import java.util.ArrayList;

public class MinRoomsSearch implements Searchable{
	Graph myMap;
	int startName;
	ArrayList<Integer> finalPath = new ArrayList<Integer>();
	int roomsVisited = 0;
	int totalCost;
	int transitRooms = 0, standardRooms = 0;
	String parent;
	
	public MinRoomsSearch(Graph g) {
		this.myMap = g;
	}

	@Override
	public boolean search(int startName, int endName) {	
		if (!myMap.containsNode(startName) || !myMap.containsNode(endName)) {
			return false;
		}
		
		this.startName = startName;
		Node startNode = myMap.getNode(startName);
		
		ArrayList<Node> queue = new ArrayList<>();
		queue.add(startNode);	
		Node temp;
		
		while (!queue.isEmpty()) {
			temp = queue.get(0);
			setParentAndCost(temp);
			
			roomsVisited++;		
			if (temp.roomType.equals("transit")) {
				transitRooms++;
			}
			else {
				standardRooms++;
			}
			
			// If the parent is Null, the case is different.
			if (temp.parent == null) {
                parent = "None";
			}
			else {
				parent = Integer.toString(temp.parent.roomNumber);
			}

					
			System.out.println("Temp node is: " + temp.roomNumber
					+ " , parent " + parent
					+ " , distance " + temp.distanceToGoal);
			
			if (temp.roomNumber == endName) {
				printPath(endName);
				return true;
			}
			
			temp.isTested = true;
			
			for (Node node : myMap.getLinkedNodes(temp.roomNumber)) {
				if (!node.isExpanded && !queue.contains(node) && myMap.checkFloor(temp, node)) {		
					queue.add(node);
				}
			}
			
			queue.remove(0);
			myMap.sortByDistance(queue);
			temp.isExpanded = true;
			
		}//end while
		
		printPath(endName);
		return (myMap.getNode(endName).parent != null);
	}
	
	private void setParentAndCost(Node node) {	
		Node temp;
		int euclidianDistance;
		for (Link l : node.links) {
			if(l.toNodeName == startName) continue;
			temp = myMap.getNode(l.toNodeName);
			euclidianDistance = (int) myMap.findDistance(node.roomNumber, temp.roomNumber);
			
            // Compare the distance to goal of the parent node to the child one. If it's less assign it to the child.
			if ((temp.parent == null) || (temp.distanceToGoal > node.distanceToGoal + euclidianDistance)) {
				temp.parent = node;
				temp.distanceToGoal = node.distanceToGoal + euclidianDistance;		
			}
			
		} // end for
		
	} // end setParentAndCost
	
	public void printPath(int name) {	
		int numOfConnections;
		Node node = myMap.getNode(name);
		ArrayList<String> path = new ArrayList<>();
		do {
			path.add(Integer.toString(node.roomNumber));
			node = node.parent;
		} while (node != null);
		
		System.out.println("\nThe path is: ");
		for (int i = path.size() - 1; i >= 0; i--) {
			System.out.print(path.get(i) + " | ");
		}
		numOfConnections = path.size() - 1;
		totalCost = numOfConnections * 2;
		
		System.out.println("\nRooms visited: " + Integer.toString(roomsVisited) + "\nTransit Rooms: " + transitRooms + "\nStandardRooms: " + standardRooms + " \nEuclidian Distance: " + myMap.getNode(name).distanceToGoal + "\nCost: " + totalCost);	
	}
}