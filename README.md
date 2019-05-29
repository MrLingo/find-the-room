# find-the-room

The program reads nodes + links from a text file, written in a specific way and constructing a graph of them ( Building's infrastructure ). The nodes represent rooms and the links the way it can be 'traveled' between them. That depends if a room is normal room or transit one. A transit room could be elevator with links to it 'lift' or stairs with links - 'climb'. Between normal rooms the links represent walking in coridor, therefore - 'walk'.
There are two problems solved here: 
1. Given a start node ( room ) find a path to another room ( if there is any ), by avoiding the stairs.
2. Given a start node ( room ) find a path to another room ( if there is any ), by visiting minimum number of rooms.

## Getting Started

1. Open Eclipse
2. `File/Open Projects from File system...`
3. Select 'Archive' and find the file.
4. Run the project by click on the green button in the top left corner.

### Prerequisites

You will need Eclipse IDE.

## License

This project is licensed under the MIT License - see the [LICENSE.md].

