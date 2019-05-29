public class Link {
	public int toNodeName;
	public int length;
	public String action;
	
	public Link(int secondRoom) {
		this.toNodeName = secondRoom;
	}
	
	public Link(int roomNumber, int l, String action) {
		this.action = action;
		this.toNodeName = roomNumber;
		this.length = l;
	}

}


