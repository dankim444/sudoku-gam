/**********************************************************************
 *  Instructions on how to run my program.
 **********************************************************************/
 1. run SudokuGame.java by typing java Sudokugame filename in the terminal window
 2. Use text file sudokuExample.txt in command line argument

/**********************************************************************
 *  A brief description of each file and its purpose.
 **********************************************************************/
SudokuGame.java - Represents the Sudoku game. Takes a level description text file and
initializes a sudoku board that the player can interact with. The program runs the
game until the user wins. 

SudokuBoard.java - This class represents underlying matrix for sudoku and
implements the basic logic of the sudoku game. It includes the implementation for all
the functions called in SudokuGame.java. Each board is a 9x9 grid of squares that
has one and only one solution. Each row, column, and 3x3 square must contain the
numbers 1-9. Cannot have the same number more than once in any given row, column, or
3x3 square. If a player inputs an illegal value (such as repeated values), then the
contradictory values are indicated in red and the row, column, and/or 3x3 region
where the issue occurs is highlighted.

Cell.java - Represents an individual cell in the 9x9 sudoku grid. Each cell has
a position in the grid, an integer value from 1-9, a halfwidth, and a boolean state
indicating whether or not a cell is clickable. A cell is clickable if and only if
it is an original value from the file. All other cells are able to be changed by
the player.
