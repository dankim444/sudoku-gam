import org.junit.Test;
import static org.junit.Assert.*;

public class boardTest {
    @Test
    public void testGetRow() {
        SudokuBoard board = new SudokuBoard("sudokuExample.txt");
        int expected = board.getRowFromClicked(8,8);
        int actual = 0;

        assertEquals(expected, actual, 0.01);
    }
}
