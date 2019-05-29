import java.util.ArrayList;

public class MinRoomsSearch implements Searchable{
	Graph myMap;
	int startName;
	ArrayList<Integer> finalPath = new ArrayList<Integer>();
	int roomsVisited = 0;
	
	public MinRoomsSearch(Graph g) {
		this.myMap = g;
	}

	@Override
	public boolean search(int startName, int endName) {	
		if(!myMap.containsNode(startName) || !myMap.containsNode(endName)) {
			return false;
		}
		
		this.startName = startName;
		Node startNode = myMap.getNode(startName);
		ArrayList<Node> queue = new ArrayList<>();
		queue.add(startNode);
		
		Node temp;
		
		while(!queue.isEmpty()) {
			temp = queue.get(0);
			setParentAndCost(temp);
			
			if(temp.parent != null)
			System.out.println("\nTemp node is: " + temp.roomNumber
					+ " , parent " + temp.parent.roomNumber
					+ " , distance " + temp.distanceToGoal);
			
			if(temp.roomNumber == endName) {
				printPath(endName);
				return true;
			}
			
			temp.isTested = true;
			queue.remove(0);
			
			for(Node node : myMap.getLinkedNodes(temp.roomNumber)) {
				if(!node.isExpanded && !queue.contains(node) && myMap.checkFloor(temp, node)) {
					queue.add(node);
					roomsVisited++;
					addToPath(temp.roomNumber);			
				}
			}
			myMap.sortByDistance(queue);
			temp.isExpanded = true;
			
		}//end while
		
		printPath(endName);
		return (myMap.getNode(endName).parent != null);
	}
	
	private void setParentAndCost(Node node) {	
		Node temp;
		int coordinatesCost;
		for(Link l : node.links) {
			if(l.toNodeName == startName) continue;
			temp = myMap.getNode(l.toNodeName);
                        
			coordinatesCost = (int) myMap.findDistance(node.roomNumber, temp.roomNumber);
			
            // Compare the distance to goal of the parent node to the child one. If it's less assign it to the child.
			if((temp.parent == null) || (temp.distanceToGoal > node.distanceToGoal + coordinatesCost)) {
				temp.parent = node;
				temp.distanceToGoal = node.distanceToGoal + coordinatesCost;				
			}
		}
	}
	
	public void addToPath(int room) {
		finalPath.add(room);
	}
	
	public void printPath(int name) {				
		Node node = myMap.getNode(name);
		ArrayList<String> path = new ArrayList<>();
		do {
			path.add(Integer.toString(node.roomNumber));
			node = node.parent;
		}while(node != null);
		
		System.out.println("\nThe path is: ");
		for(int i = path.size() - 1; i >= 0; i--) {
			System.out.print(path.get(i) + " | ");
		}
		System.out.println("\nRooms visited: " + Integer.toString(roomsVisited) + " \nDistance: " + myMap.getNode(name).distanceToGoal);	
	}
}