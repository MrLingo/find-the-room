import java.util.ArrayList;

public class AvoidStairsSearch implements Searchable {
    Graph myMap;
	int totalCost;
	int numberOfRoomsVisited = 0, transitRooms = 0, standardRooms = 0;
	long runTime = System.currentTimeMillis();
	long endIt = runTime + 2; // 2 milliseconds
	
	public AvoidStairsSearch(Graph g) {
		this.myMap = g;
	} 
	
	public boolean search(int startName, int endName) {
		if (!myMap.containsNode(startName) || !myMap.containsNode(endName)) {
			return false;
		}
		
		// 'Catch' the start node name and create a stack structure.
		Node startNode = myMap.getNode(startName);
		ArrayList<Node> queue = new ArrayList<>();;	
		Node temp;
		queue.add(startNode);
		
		while (!queue.isEmpty()) {
			temp = queue.get(0);
			
			if (temp.parent != null) {
				System.out.println("Temp node is: " + temp.roomNumber + " (" + temp.roomType + ")");
			}
			else {
				System.out.println("Temp node is: " + temp.roomNumber + " (" + temp.roomType + ")");	
				}
			
			if( temp.roomType.equals("transit") ) {
				transitRooms++;
			}
			else{
				standardRooms++;
			}
			numberOfRoomsVisited++;
			
			if (temp.roomNumber == endName) {
				printPath(endName);
				return true;
			}
			
			// If the algorithm continues forever stop it by counting the milliseconds of it's runtime.
			else if(System.currentTimeMillis() > endIt) {
				return false;
			}
			
			temp.isTested = true;
			
			for (Node node : myMap.getLinkedNodes(temp.roomNumber)) {
				if (!node.isTested && !queue.contains(node)) {	
						for (Link l : temp.links) {
							if (l.action.equals("walk") || l.action.equals("lift")) {
								Node nodeToAdd = myMap.getNode(l.toNodeName);
								queue.add(nodeToAdd);						
							    node.parent = temp;    		    
							}
						} // end inner for				
									
				} // end if
				
			} // end for
			queue.remove(0);
			temp.isExpanded = true;
		} //end while		
		return false;
	}
	
	// Check if the connection has 'climb' action.
	public boolean avoidStairs(Node node){
		for(Link l : node.links) {
		    if (l.action.equals("climb")) {
		    	return false;
		    }
		} // end for
		return true;
	}
	
	public void calculateCost(Node parent, Node child) {
		for (Link l : parent.links) {
			if (l.toNodeName == child.roomNumber) {
				break;	
			}
		} // for	
	}
	
	public void printPath(int name) {		
		int numOfConnections;
		Node node = myMap.getNode(name);
		ArrayList<String> path = new ArrayList<>();
		do {
			path.add(Integer.toString(node.roomNumber));
			node = node.parent;
		} while (node != null);
		
		numOfConnections = path.size() - 1;
		totalCost = numOfConnections * 2;
		
		System.out.println("\nRooms visited: " + Integer.toString(numberOfRoomsVisited) + "\nTransit Rooms: " + transitRooms + "\nStandardRooms: " + standardRooms + "\nCost: " + totalCost);	
		}
}
