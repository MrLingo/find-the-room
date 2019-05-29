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
				
				if( isBidirectionalStr.equals("yes;")){
					isBidirectionalBool = true;
				}
				else if( isBidirectionalStr.equals("no;") ){
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
	
	public static void init() throws FileNotFoundException {		
		file = new File(".\\Resources\\Data.txt");
		parseFile(file);
		
		// Test code:
		/*
		graph.addNode(new Node(101, 15, 28, 1, "room"));
		graph.addNode(new Node(102, 9, 6, 1, "room"));
		graph.addNode(new Node(103, 5, 11, 1, "room"));
		graph.addNode(new Node(206, 51, 61, 1, "transit"));
		graph.addNode(new Node(200, 8, 7, 1, "transit"));
		graph.addNode(new Node(201, 82, 70, 2, "room"));
		graph.addNode(new Node(202, 67, 61, 2, "room"));
		graph.addNode(new Node(203, 65, 50, 2, "room"));
		graph.addNode(new Node(204, 15, 28, 2, "room"));
		graph.addNode(new Node(205, 9, 6, 2, "room"));
		graph.addNode(new Node(212, 70, 50, 2, "transit"));
		graph.addNode(new Node(215, 75, 65, 2, "transit"));
		graph.addNode(new Node(301, 75, 35, 3, "room"));
		graph.addNode(new Node(302, 80, 20, 3, "room"));
		graph.addNode(new Node(303, 67, 61, 3, "room"));
		graph.addNode(new Node(304, 184, 73, 3, "room"));
		// 16 nodes total
		
		graph.addLink(101, 102, "walk", 3 , true);
		graph.addLink(101, 206, "lift", 8 , true); // Transit
		graph.addLink(101, 103, "walk", 2 , true);
		graph.addLink(102, 103, "walk", 1 , true);
		graph.addLink(103, 200, "lift", 1 , true); // Transit
		graph.addLink(200, 201, "lift", 4 , true); // Transit
		graph.addLink(201, 202, "walk", 6 , true);
		graph.addLink(202, 206, "lift", 2 , true); // Transit
		graph.addLink(202, 212, "climb", 3 , true); // Transit
		graph.addLink(202, 203, "walk", 11 , true);
		graph.addLink(206, 205, "lift", 9 , true); // Transit
		graph.addLink(205, 203, "walk", 4 , true);
		
		graph.addLink(203, 212, "climb", 5 , true); // Transit
		graph.addLink(203, 204, "walk", 2 , true);
		graph.addLink(205, 215, "climb", 3 , true); // Transit
		
		graph.addLink(204, 215, "climb", 2 , true); // Transit
		graph.addLink(215, 304, "climb", 4 , true); // Transit
		graph.addLink(304, 303, "walk", 1 , true);
		
		graph.addLink(303, 301, "walk", 7 , true);
		graph.addLink(301, 302, "walk", 4 , true);
		graph.addLink(301, 212, "climb", 1 , true); // Transit
		*/
	}

	public static void main(String[] args) throws FileNotFoundException {
		init();
		findPath(202, 103, new MinRoomsSearch(graph));
	}
}
