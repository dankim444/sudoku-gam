/**
 * Execution: java SudokuBoard
 *
 * Description: Represents underlying matrix for sudoku board. Each row, column, and
 * 3x3 square must contain the numbers 1-9. Cannot have the same number more than once
 * in any given row, column, or 3x3 square. If a player inputs an illegal value (such
 * as repeated values), then the contradictory values are indicated in red and the
 * row, column, and/or 3x3 region where the issue occurs is highlighted. The sudoku
 * puzzle has one and only one solution.
 *
 **/

public class SudokuBoard {
    // Each sudoku board has 9 rows and 9 columns
    public static final int NUM_ROWS = 9;
    public static final int NUM_COLUMNS = 9;

    // Each board is a 2D Cell array
    private Cell[][] board;

    /**
     * Constructor that instantiates and delegates information to a 9x9 sudoku board
     * that is passed in as a text file.
     */
    public SudokuBoard(String filename) {
        // Read in the text file specified by the argument
        In in = new In(filename);

        if (!correctFileLength(filename) || !correctFileChars(filename) ||
                !checkFileRows(filename)) {
            System.out.println("Invalid text file");
        }

        // Change x scale and y scale of coordinate grid
        PennDraw.setXscale(-1, 9);
        PennDraw.setYscale(-1, 9);

        // Declare and initialize grid
        board = new Cell[NUM_ROWS][NUM_COLUMNS];

        // Read in chars from text file and assign information to each cell in board
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLUMNS; col++) {
                char a = in.readChar();
                // If char is represented by a space value, then the cell is
                if (a == ' ') {
                    board[row][col] = new Cell(row, col, a, 0.5, true);
                }
                else {
                    board[row][col] = new Cell(row, col, a - 48, 0.5, false);
                }
            }
            if (row < 8) {
                in.readChar();
            }
        }
        in.close();
    }

    /**
     * Inputs: A String input representing the text file
     * Outputs: N/A
     * Description: Checks if the input file is incorrectly formatted, such as not
     *              9x9 characters.
     */
    public boolean correctFileLength(String filename) {
        In in = new In(filename);
        String a = in.readAll();
        if (a.length() != 89) {
            return false;
        }
        return true;
    }

    /**
     * Inputs: A String input representing the text file
     * Outputs: N/A
     * Description: Checks if the input file uses any invalid characters.
     */
    public boolean correctFileChars(String filename) {
        In in = new In(filename);
        String a = in.readAll();
        boolean result = true;
        for (int i = 0; i < a.length(); i++) {
            if (!(a.charAt(i) == ' ' ||
                    (a.charAt(i) - 48 >= 1 && a.charAt(i) - 48 <= 9) ||
                    a.charAt(i) == '\n')) {
                result = false;
            }
        }
        return result;
    }

    /**
     * Inputs: A String input representing the text file
     * Outputs: N/A
     * Description: Checks if the input file has any repeats in the rows.
     */
    public boolean checkFileRows(String filename) {
        In in = new In(filename);
        boolean result = true;
        while (!in.isEmpty()) {
            String a = in.readLine();
            for (int i = 0; i < a.length(); i++) {
                for (int j = 0; j < a.length(); j++) {
                    if (i != j) {
                        if (a.charAt(i) != ' ' && a.charAt(i) == a.charAt(j)) {
                            result = false;
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * Inputs: N/A
     * Outputs: N/A
     * Description: Handles drawing implementation of the board.
     */
    public void drawBoard() {
        PennDraw.setPenRadius();

        // Draw cells in each row and each column of 9x9 2D Cell array
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLUMNS; col++) {
                board[row][col].drawCell();
                // Draw the original values from the text file on the board
                if (board[row][col].getVal() != ' ') {
                    PennDraw.text(col, (int) Math.abs(row - 8), "" +
                            board[row][col].getVal());
                }
            }
        }

        // Draws boundaries for each 3x3 square within sudoku board
        PennDraw.setPenRadius(0.006);
        PennDraw.line(2.5, 8.5, 2.5, -0.5); // vertical lines
        PennDraw.line(5.5, 8.5, 5.5, -0.5);
        PennDraw.line(-0.5, 5.5, 8.5, 5.5); // horizontal lines
        PennDraw.line(-0.5, 2.5, 8.5, 2.5);

        // Draw borders for sudoku board
        PennDraw.line(-0.5, 8.5, 8.5, 8.5);
        PennDraw.line(8.5, 8.5, 8.5, -0.5);
        PennDraw.line(8.5, -0.5, -0.5, -0.5);
        PennDraw.line(-0.5, -0.5, -0.5, 8.5);
    }

    /**
     * Inputs: N/A
     * Outputs: A boolean value
     * Description: Decides whether or not the player has won. A player has won once
     *              all spaces in the sudoku board have been correctly filled.
     */
    public boolean didPlayerWin() {
        boolean result = true;

        // If there are any unfilled spaces on the board, the player has not won
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLUMNS; col++) {
                if (board[row][col].getVal() == ' ') {
                    result = false;
                }
            }
        }
        return result;
    }

    /**
     * Inputs: Two doubles representing the x and y position of a mouse click and a
     *         char that represents the number the user inputs
     * Outputs: N/A
     * Description: Draws the char s as a text at the specified row and column of the
     *              board. Iterates through the sudoku board to check which cell was
     *              clicked on, then updates that cell's value to the char specified
     *              in the argument.
     */
    public void updateCell(double mouseX, double mouseY, char s) {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLUMNS; col++) {
                if (board[row][col].checkClick(mouseX, mouseY)) {
                    board[8 - col][row].setVal(s - 48);
                }
            }
        }
    }

    /**
     * Inputs: Two doubles representing the position of a mouse's cursor.
     * Outputs: N/A
     * Description: Deletes the value of a cell at a specified position on the board.
     */
    public void deleteCell(double mouseX, double mouseY) {
        PennDraw.setPenColor(PennDraw.YELLOW);
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLUMNS; col++) {
                if (board[row][col].checkClick(mouseX, mouseY)) {
                    board[8 - col][row].setVal(' ');
                    PennDraw.filledSquare(row, col, 0.5);
                }
            }
        }
        PennDraw.setPenColor();
    }

    /**
     * Inputs: A char
     * Outputs: A boolean
     * Description: Checks if a char is a valid input. A valid input is an integer
     *              between 1 and 9, inclusive. Any other values will not appear on
     *              the board.
     */
    public boolean isValidInput(char s) {
        int a = s - 48;
        return a >= 1 && a <= 9;
    }

    /**
     * Inputs: Two doubles representing the x and y position of a mouse click
     * Outputs: N/A
     * Description: Highlights the cell that the user clicks on.
     */
    public void highlightCellClicked(double mouseX, double mouseY) {
        PennDraw.setPenColor(PennDraw.YELLOW);
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLUMNS; col++) {
                if (board[row][col].checkClick(mouseX, mouseY)) {
                    PennDraw.filledSquare(row, col, 0.5);
                }
            }
        }
        PennDraw.setPenColor();
    }

    /**
     * Inputs: Two doubles representing the x and y position of a mouse click
     * Outputs: N/A
     * Description: Unhighlights the cell that the user previously clicked on.
     */
    public void unhighlightCellClicked(double mouseX, double mouseY) {
        PennDraw.setPenColor(PennDraw.WHITE);
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLUMNS; col++) {
                if (board[row][col].checkClick(mouseX, mouseY)) {
                    PennDraw.filledSquare(row, col, 0.5);
                }
            }
        }
        PennDraw.setPenColor();
    }

    /**
     * Inputs: Two doubles representing the x and y position of a mouse click
     * Outputs: A boolean value indicating whether or not a cell is clickable
     * Description: Checks whether or not a cell is able to be clicked on. A cell is
     *              only clickable if it doesn't contain a value that the board
     *              originally starts off with.
     */
    public boolean isClickable(double mouseX, double mouseY) {
        boolean clickable = true;
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLUMNS; col++) {
                if (board[row][col].checkClick(mouseX, mouseY)) {
                    clickable = board[8 - col][row].getBoolean();
                }
            }
        }
        return clickable;
    }

    /**
     * Inputs: Two doubles representing the x and y position of a mouse click
     * Outputs: An integer
     * Description: Returns the row of the cell that was clicked on.
     */
    public int getRowFromClicked(double mouseX, double mouseY) {
        int rowFromClicked = 0;
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLUMNS; col++) {
                if (board[row][col].checkClick(mouseX, mouseY)) {
                    rowFromClicked = 8 - col;
                }
            }
        }
        return rowFromClicked;
    }

    /**
     * Inputs: Two doubles representing the x and y position of a mouse click
     * Outputs: An integer
     * Description: Returns the column of the cell that was clicked on.
     */
    public int getColFromClicked(double mouseX, double mouseY) {
        int colFromClicked = 0;
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLUMNS; col++) {
                if (board[row][col].checkClick(mouseX, mouseY)) {
                    colFromClicked = row;
                }
            }
        }
        return colFromClicked;
    }

    /**
     * Inputs: Two integers, one that specifies the row number and another that
     *         indicates the number to look for
     * Outputs: A boolean
     * Description: Checks if there are repeats for any number in a row. There are
     *              repeats if any number appears more than once.
     */
    public boolean isInRow(int row, int val) {
        int counter = 0;
        // Iterate through row to count how many times a number appears
        for (int col = 0; col < NUM_COLUMNS; col++) {
            if (board[row][col].getVal() == val - 48) {
                counter++;
            }
        }
        // The number is a repeat if it appears more than once
        if (counter != 1) {
            return true;
        }
        return false;
    }

    /**
     * Inputs: Two integers, one that specifies the column number and another that
     *         indicates the number to look for
     * Outputs: A boolean
     * Description: Checks if there are repeats for any number in a column. There are
     *              repeats if any number appears more than once.
     */
    public boolean isInColumn(int col, int val) {
        int counter = 0;
        for (int row = 0; row < NUM_ROWS; row++) {
            if (board[row][col].getVal() == val - 48) {
                counter++;
            }
        }
        if (counter != 1) {
            return true;
        }
        return false;
    }

    /**
     * Inputs: Takes in three integer parameters
     * Outputs: A boolean
     * Description: Checks if there are repeats for any number in a 3x3 grid. There
     *              are repeats if any number appears more than once.
     */
    public boolean isInLocalSquare(int row, int col, int val) {
        int counter = 0;
        int localSquareRow = row - (row % 3);
        int localSquareCol = col - (col % 3);
        for (int r = localSquareRow; r < localSquareRow + 3; r++) {
            for (int c = localSquareCol; c < localSquareCol + 3; c++) {
                if (board[r][c].getVal() == val - 48) {
                    counter++;
                }
            }
        }
        if (counter != 1) {
            return true;
        }
        return false;
    }

    /**
     * Inputs: An integer that specifies the row
     * Outputs: N/A
     * Description: Highlights the row where the contradictory value occurs.
     */
    public void highlightRow(int row) {
        PennDraw.setPenColor(PennDraw.YELLOW);
        for (int col = 0; col < NUM_COLUMNS; col++) {
            PennDraw.filledSquare(col, 8 - row, 0.5);
        }
        PennDraw.setPenColor();
    }

    /**
     * Inputs: An integer that specifies the column
     * Outputs: N/A
     * Description: Highlights the column where the contradictory value occurs.
     */
    public void highlightColumn(int col) {
        PennDraw.setPenColor(PennDraw.YELLOW);
        for (int row = 0; row < NUM_ROWS; row++) {
            PennDraw.filledSquare(col, 8 - row, 0.5);
        }
        PennDraw.setPenColor();
    }

    /**
     * Inputs: Two integers specifying the row and column
     * Outputs: N/A
     * Description: Highlights the 3x3 region where the contradictory value occurs.
     */
    public void highlightLocalBox(int row, int col) {
        PennDraw.setPenColor(PennDraw.YELLOW);
        int localSquareRow = row - (row % 3);
        int localSquareCol = col - (col % 3);
        for (int r = localSquareRow; r < localSquareRow + 3; r++) {
            for (int c = localSquareCol; c < localSquareCol + 3; c++) {
                PennDraw.filledSquare(c, 8 - r, 0.5);
            }
        }
        PennDraw.setPenColor();
    }

    /**
     * Inputs: Two integers representing the row and column, a char representing user
     *         input
     * Outputs: N/A
     * Description: Marks any contradictory values in red if it appears in a
     *              specified row, column, or 3x3 grid.
     */
    public void markContradictoryValue(int row, int col, char s) {
        PennDraw.setPenColor(PennDraw.RED);

        // Mark contradictory value in red if it is found in the row
        for (int c = 0; c < NUM_COLUMNS; c++) {
            if (board[row][c].getVal() == s - 48) {
                PennDraw.filledSquare(c, 8 - row, 0.5);
            }
        }

        // Mark contradictory value in red if it is found in the column
        for (int r = 0; r < NUM_ROWS; r++) {
            if (board[r][col].getVal() == s - 48) {
                PennDraw.filledSquare(col, 8 - r, 0.5);
            }
        }

        // Mark the contradictory value in red if it is found in the 3x3 grid
        int localSquareRow = row - (row % 3);
        int localSquareCol = col - (col % 3);
        for (int r = localSquareRow; r < localSquareRow + 3; r++) {
            for (int c = localSquareCol; c < localSquareCol + 3; c++) {
                if (board[r][c].getVal() == s - 48) {
                    PennDraw.filledSquare(c, 8 - r, 0.5);
                }
            }
        }
        PennDraw.setPenColor();
    }

    /**
     * Inputs: N/A
     * Outputs: N/A
     * Description: Clears the board.
     */
    public void clearHighlights() {
        PennDraw.clear();
        drawBoard();
    }

    /**
     * Inputs: N/A
     * Outputs: N/A
     * Description: Draws the victory screen when the user has won the game.
     */
    public void displayVictory() {
        PennDraw.setPenColor(PennDraw.GREEN);
        PennDraw.setFontBold();
        PennDraw.text(4.5, 4.5, "You Win!");
    }

    /////////////////////////////////////
    // EXTRA CREDIT ///////////////////
    /////////////////////////////////////

    /**
     * Inputs: N/A
     * Outputs: N/A
     * Description: Clears all numbers inputted by user if space bar is pressed.
     */
    public void clearAllNumbers() {
        PennDraw.setPenColor(PennDraw.WHITE);
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLUMNS; col++) {
                if (board[row][col].getBoolean()) {
                    board[row][col].setVal(' ');
                    PennDraw.filledSquare(row, col, 0.5);
                }
            }
        }
        PennDraw.setPenColor();
    }

}

