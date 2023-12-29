/**
 * Execution: java SudokuGame filename
 *
 * Description: Represents the Sudoku game. Takes a level description text file and
 * initializes a sudoku board that the player can interact with. The program runs the
 * game until the user wins.
 *
 **/

public class SudokuGame {
    public static void main(String[] args) {
        /**
         * Instantiate a sudoku board with the name of a level description file
         * passed in via command line argument
         */
        SudokuBoard board = new SudokuBoard(args[0]);

        boolean isPlaying = true;
        double mouseX = 0.0;
        double mouseY = 0.0;

        // If the player has not won, keep the game running
        while (isPlaying) {
            // Draws the updated board and its components
            board.drawBoard();

            if (PennDraw.mousePressed()) {
                // Stores the x and y values of the mouse cursor's position
                mouseX = PennDraw.mouseX();
                mouseY = PennDraw.mouseY();
                /**
                 * Highlight the cell that was just clicked, only if it is able to be
                 * clicked on
                 */
                if (board.isClickable(mouseX, mouseY)) {
                    board.clearHighlights();
                    board.highlightCellClicked(mouseX, mouseY);
                }
            }

            /**
             * Store the value of the row and column of the cell at the mouse
             * cursor's position
             */
            int row = board.getRowFromClicked(mouseX, mouseY);
            int col = board.getColFromClicked(mouseX, mouseY);

            char s = 0;
            if (PennDraw.hasNextKeyTyped()) {
                // Stores the value of the character input
                s = PennDraw.nextKeyTyped();

                /**
                 * Only update a cell if the input is valid and if the cell is able
                 * to be clicked on
                 */
                if (board.isValidInput(s) && board.isClickable(mouseX, mouseY)) {
                    board.unhighlightCellClicked(mouseX, mouseY);
                    board.updateCell(mouseX, mouseY, s);

                    // Highlight row if number appears more than once in row
                    if (board.isInRow(row, s)) {
                        board.highlightRow(row);
                    }
                    // Highlight column if number appears more than once in column
                    if (board.isInColumn(col, s)) {
                        board.highlightColumn(col);
                    }
                    // Highlight 3x3 grid if number appears more than once in grid
                    if (board.isInLocalSquare(row, col, s)) {
                        board.highlightLocalBox(row, col);
                    }
                    // Highlight the contradictory values in red
                    if (board.isInRow(row, s) || board.isInColumn(col, s) ||
                            board.isInLocalSquare(row, col, s)) {
                        board.markContradictoryValue(row, col, s);
                    }
                }

                // If delete button is pressed, remove value in cell
                if (s == 8) {
                    board.deleteCell(mouseX, mouseY);
                }
                // If spacebar is pressed, clear all numbers inputted by user
                if (s == 32) {
                    board.clearAllNumbers();
                }
            }

            /**
             * Draws victory screen if there are no empty cells and all cells
             * have been filled in correctly
             */
            if (board.didPlayerWin()) {
                isPlaying = false;
                board.displayVictory();
                PennDraw.setPenColor();
            }
            board.drawBoard();
        }
    }
}
