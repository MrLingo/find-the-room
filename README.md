# find-the-room

The program reads nodes + links from a text file, written in a specific way and constructing a graph of them ( Building's infrastructure ). The nodes represent rooms and the links the way it can be 'traveled' between them. That depends if a room is normal room or transit one. A transit room could be elevator with links to it 'lift' or stairs with links - 'climb'. Between normal rooms the links represent walking in coridor, therefore - 'walk'.
There are two problems solved here: 
1. Given a start and end node, find a path ( if there is any ), by avoiding stairs.
2. Given a start and end node, find a path ( if there is any ), by visiting minimum number of rooms.

## Prerequisites

Eclipse IDE.

## Getting Started

1. Open Eclipse
2. `File/Open Projects from File system...`
3. Select 'Archive' and find the file.
4. Open the main class.
5. Run the project by click on the green button in the top left corner.




