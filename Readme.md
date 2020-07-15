# DFS/BFS Project
-
**Assignment:** The assignment for this project was to use depth-first and breadth-first searches (with stacks and queues, respectively) to solve self selected problems.

I chose the following problems to implemenent using the two methods: Missionaries and Cannibals, Islands, and Sudoku. Each problem will be explained below. I selected these problems based on interest, difficulty, and relevance to the project.

**Project files and directories:**<br>
*Missionaries and Cannibals*<br>
`MissionariesQ.java`, `MissionariesS.java`, `Position.java`, `images`

*Islands*<br>
`IslandsQ.java`, `IslandsS.java`, `Island.txt`

*Sudoku*<br>
`SudokuQ.java`, `SudokuS.java`, `Sudoku.txt`

# Missionaries and Cannibals

## Problem

"On one bank of a river are X missionaries and Y cannibals. There is one boat available that can hold up to two people and that they would like to use to cross the river. If the cannibals ever outnumber the missionaries on either of the river’s banks, the missionaries will get eaten. How can the boat be used to safely carry all the missionaries and cannibals across the river?"

Diagram of search solution for 3 missionaries and 3 cannibals:

<center><img src="https://i.imgur.com/HdXHEJv.png" alt="View.java"
	title="View.java" width="360" height="183" /></center>

Also, an interesting observation is that there are no solutions for moving 4 cannibals/4 missionaries.

## Position.java

I approached this problem by drawing it out. Each river position contains some properties: number of cannibals left/right of river, number of missionaries left/right of river, and the boat's left/right position in the river.

Additionally, to backtrack from the final solution to the initial board state, we need to store the parent position of each position. Each of these parameters are implemented in `Position.java`.

Here, I use `movePossibilities()` to return the possible "children" of each position. This can be thought of as the different combinations of moving missionaries and cannibals across the river. I created this function referencing the previous 8Puzzles' decision to use an iterator returning the neighbors for ease of use in the for-each loop in the solver. However, I chose not to use an iterable because I needed to set the parent position here, and we cannot reference/set a parent node of "this" instance of a node in an iterable.

## (DFS) MissionariesS.java

`solve()` contains the solving method using a stack. A initial board state is first added in with X missionaries on the left, Y cannibals on the left, and the boat to the left.

While the stack is not empty and the problem is not solved, the latest position is used via `stack.peek()`. We go through that position's different move possibilities to find a non-repeated new valid position and push it into the stack. If no possible move exists, we pop off the current position.

## (BFS) MissionariesQ.java

While the queue is not empty and the problem is not solved, a position is dequeued. We enqueue all that position's move possibilities that are not repeats.

For both questions, I used the LinkedList `tracker` to keep track of moves that have already been traveled to, or else there will be infinite loops containing repeat positions.

## MissionariesView.java

This is the GUI for the missionaries and cannibals problem. The cannibals are denoted by the orange cavemen while the missionaries are denoted by Peter Griffin. Press left/right to go through the steps.

<center><img src="https://i.imgur.com/ZIhy89T.png" alt="View.java"
	title="View.java" width="564" height="273" /></center>

# Islands

## Problem

"Given a map of 0s (water) and 1s (land), count the number of islands. An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically."

Visual of problem (islands = 5):
<center><img src="https://i.imgur.com/AYzLazN.png" alt="View.java"
	title="View.java" width="581" height="194" /></center>

The map is located in the text file `Island.txt`.

For both solve methods, we have two arrays: one integer array containing the map and one boolean array containing whether a location has been traveled to. We go through each position inside the map, and if it is a piece of unvisited land, we traverse it via DFS or BFS.

## (DFS) IslandsS.java

While the stack is not empty, we get the current position via `stack.peek()` and updated the boolean array. We find the next, non-repeated valid location from all the move possibilities and push the new location. If there are no valid locations, then we pop from the stack.

## (BFS) IslandsQ.java

While the queue is not empty, we use `queue.dequeue` to obtain the current position and update the boolean array. We enqueue all the valid new locations.

# Sudoku

## Problem

"The objective is to fill a 9×9 grid with digits so that each column, each row, and each of the nine 3×3 subgrids that compose the grid contain all of the digits from 1 to 9."

The Sudoku is located in the `Sudoku.txt` file. The 0s represent blank, fillable spaces.

<center><img src="https://i.imgur.com/oRsaa2q.png" alt="View.java"
	title="View.java" width="506" height="517" /></center>

Here, the red highlights the number that the board uses in the position. In the end, each fillable square in the solved Sudoku will have a red number.

## (DFS) SudokuS.java

While there are more spaces to fill in the Sudoku, we get the next, non-repeated number for the next blank space. If this number exists, we update the sudoku with this number and push the location. If it does not, we pop from the stack and write the tile blank.

## (BFS) SudokuQ.java

While there are more spaces to fill in the Sudoku, we dequeue the current board. We acquire the next blank spot and enqueue copies of the current board with the blank spot filled in with all possible numbers.
