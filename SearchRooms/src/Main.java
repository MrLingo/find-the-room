import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	static File file;
	static Scanner input;
	static Graph graph = new Graph();
	
	public static void findPath(int startName, int endName, Searchable searcher) {
		graph.resetAllNodes();
		if (searcher.search(startName, endName)) {
			System.out.println("There is a path!");
		}else  {
			System.out.println("No such path found!");
		}
	}
	
	// Take the input from the file.
	public static void parseFile(File file) throws FileNotFoundException {
		String[] splitResult;
		String roomNumberStr, xStr, yStr, floorStr, roomTypeStr;
		int roomNumberInt, xInt, yInt, floorInt;
		
		String firstRoomStr, secondRoomStr, transitionType, costStr, isBidirectionalStr;
		int firstRoomInt, secondRoomInt, costInt;
		boolean isBidirectionalBool = true;
		
		input = new Scanner(file);
		while (input.hasNext()) {
			String nextLine = input.nextLine();
			splitResult = nextLine.split(",");
			
			if(nextLine.isEmpty()) {
				continue;
			}	
			
			// Add nodes.
			else if (splitResult[4].equals("room;") || splitResult[4].equals("transit;")) {
				
				// Save the now separated values in strings.
				roomNumberStr = splitResult[0];
				xStr = splitResult[1];
				yStr = splitResult[2];
				floorStr = splitResult[3];
				roomTypeStr = splitResult[4];
				
				// Parse to integer.
				roomNumberInt = Integer.parseInt(roomNumberStr);
				xInt = Integer.parseInt(xStr);
				yInt = Integer.parseInt(yStr);
				floorInt = Integer.parseInt(floorStr);
				
				// Cutting the ; of roomType string.
				roomTypeStr = roomTypeStr.substring(0, roomTypeStr.length() - 1);
				
				graph.addNode(new Node(roomNumberInt, xInt, yInt, floorInt, roomTypeStr));				
			}
			
			// Add transitions.
			else if (splitResult[4].equals("yes;") || splitResult[4].equals("no;")) {
				firstRoomStr = splitResult[0];
				secondRoomStr = splitResult[1];
				transitionType = splitResult[2];
				costStr = splitResult[3];
				isBidirectionalStr = splitResult[4];
				
				firstRoomInt = Integer.parseInt(firstRoomStr);
				secondRoomInt = Integer.parseInt(secondRoomStr);			
				costInt = Integer.parseInt(costStr);
				
				if (isBidirectionalStr.equals("yes;")) {
					isBidirectionalBool = true;
				}
				else if ( isBidirectionalStr.equals("no;")) {
					isBidirectionalBool = false;
				}
				else {
					System.out.println("Wrong input! Requires yes or no.");
				}
				
				graph.addLink(firstRoomInt, secondRoomInt, transitionType, costInt , isBidirectionalBool);
				
			}else {
			    System.out.println("Wrong line parameters!");
			}		
			
		} // end while
		input.close();
	}
	
	public static void init() throws FileNotFoundException{
            file = new File(".\\find-the-room-master\\SearchRooms\\Resources\\Data.txt");
	    parseFile(file);
	}

	public static void main(String[] args) throws FileNotFoundException {
		init();
		findPath(202, 103, new AvoidStairsSearch(graph));
	}
}
