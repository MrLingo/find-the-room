import java.util.Stack;

public class AvoidStairsSearch implements Searchable {
	Graph myMap;
	int totalCost;
	int numberOfRoomsVisited = 0, transitRooms = 0, standardRooms = 0;
	
	public AvoidStairsSearch(Graph g) {
		this.myMap = g;
	} 
	
	public boolean search(int startName, int endName) {
		if( !myMap.containsNode(startName) || !myMap.containsNode(endName)){
			return false;
		}
		
		// 'Catch' the start node name and create a stack structure.
		Node startNode = myMap.getNode(startName);
		Stack<Node> stack = new Stack<Node>();
		stack.push(startNode);
		
		Node temp;
		
		while(!stack.isEmpty()) {
			temp = stack.pop();
			System.out.println("Temp node is: " + temp.roomNumber + " (" + temp.roomType + ")" );
			if( temp.roomType.equals("transit") ) {
				transitRooms++;
			}
			else{
				standardRooms++;
			}
			
			numberOfRoomsVisited++;
			
			if(temp.roomNumber == endName) {
				printPath(numberOfRoomsVisited, transitRooms, standardRooms);
				return true;
			}
			
			temp.isTested = true;
			
			for(Node node : myMap.getLinkedNodes(temp.roomNumber)) {
				if(!node.isTested && !stack.contains(node) && myMap.checkFloor(temp, node)) {		
					if(avoidStairs(node)) {
						node.parent = temp;
						stack.push(node);
						calculateCost(node.parent, node);	
					}
					else {
						continue;
					}
				} // end if
			} // end for
			
			temp.isExpanded = true;
		} //end while
			
		return false;
	}
	
	// Check if the connection has 'climb' action.
	public boolean avoidStairs(Node node){
		for(Link l : node.links) {
		    if( l.action.equals("climb") ){
		    	return false;
		    }
		} // end for
		return true;
	}
	
	public void calculateCost(Node parent, Node child) {
		for(Link l : parent.links) {
			if( l.toNodeName == child.roomNumber) {
				System.out.println(l.length);
				totalCost += l.length;
				break;	
			}
		} // for	
	}
	
	public void printPath(int allRooms, int transitRooms, int stdRooms) {
		String numOfRooms = Integer.toString(allRooms);
		String transit = Integer.toString(transitRooms);
		String standard = Integer.toString(stdRooms);
		System.out.println("\nNumber of rooms visited: " + numOfRooms + "\n" + 
		"Transit rooms: " + transit + "\n"+ 
		"Standard rooms: " + standard + " \nTotal cost of: " + totalCost);
		}
}
