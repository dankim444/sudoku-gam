/**
 * Execution: java Cell
 *
 * Description: Represents an individual cell in the 9x9 sudoku grid. Each cell has
 * a position in the grid, an integer value from 1-9, a halfwidth, and a boolean state
 * indicating whether or not a cell is clickable. A cell is clickable if and only if
 * if it is an original value from the file. All other cells are able to be changed by
 * the player.
 *
 **/

public class Cell {
    // Instance variables that define each cell
    private int x;
    private int y;
    private int val;
    private double halfwidth;
    private boolean clickable;

    // A constructor that instantiates and delegates information to each cell.
    public Cell(int x, int y, int val, double halfwidth, boolean clickable) {
        this.x = x;
        this.y = y;
        this.val = val;
        this.halfwidth = halfwidth;
        this.clickable = clickable;
    }

    /**
     * Inputs: Two doubles representing the x and y positions of a mouse click
     * Outputs: A boolean
     * Description: Checks if a cell is in the mouse cursor's position.
     */
    public boolean checkClick(double mouseX, double mouseY) {
        double top = y + 0.5;
        double bottom = y - 0.5;
        double left = x - 0.5;
        double right = x + 0.5;
        return mouseY < top && mouseY > bottom && mouseX > left && mouseX < right;
    }

    /**
     * Inputs: N/A
     * Outputs: N/A
     * Description: Draws a cell as a square with a specified position and halfwidth.
     */
    public void drawCell() {
        PennDraw.square(x, y, halfwidth);
    }

    /**
     * Inputs: N/A
     * Outputs: An integer
     * Description: Getter that returns the value that a cell stores.
     */
    public int getVal() {
        return val;
    }

    /**
     * Inputs: N/A
     * Outputs: A boolean
     * Description: Getter that returns a cell's clickable state.
     */
    public boolean getBoolean() {
        return clickable;
    }

    /**
     * Inputs: An integer representing a new val
     * Outputs: N/A
     * Description: A setter that changes a cell's current value to the value
     *              specified in the parameter.
     */
    public void setVal(int val) {
        this.val = val;
    }
}

