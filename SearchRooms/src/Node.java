import java.util.ArrayList;

public class Node {	
	public int roomNumber;
	public int x;
	public int y;
	public int floorNumber;
	public String roomType;
	public int distanceToGoal = 0;
	Node parent = null;
	
	boolean isTested = false;
	boolean isExpanded = false;
	
	public ArrayList<Link> links = new ArrayList<>();
	
	public Node(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	public Node(int roomNumber, int x, int y, int floorNumber, String roomType) {
		this(roomNumber);
		this.x = x;
		this.y = y;
		this.floorNumber = floorNumber;
		this.roomType = roomType;
	}
	
	public void reset() {
		this.isExpanded = false;
		this.isTested = false;
		this.distanceToGoal = 0;
		this.parent = null;
	}

}