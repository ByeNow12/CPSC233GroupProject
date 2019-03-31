import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Arrays;

/**
* Test for Move class, tests for the information required for a player to make a move
*/
public class MoveTest {
	
	@Test
	public void test_constructorWithParams() {
		Move m = new Move("white", 2, 1, 3, 1);
		assertEquals("Expected token to be white", "white", m.getTeam());
		assertEquals("Expected row of piece to move from to be 2", 2, m.getFromRow());
		assertEquals("Expected column of piece to move from to be 1", 1, m.getFromCol());  
		assertEquals("Expected row to move piece to, to be 3", 3, m.getToRow());
		assertEquals("Expected column to move piece to, to be 1", 1, m.getToCol());
	}
	
	@Test 
	public void test_getFrom() {
		Move m = new Move("white", 2, 2, 3, 2);
		int[] expectedResult = new int[] {2, 2};
		assertArrayEquals("Expected integer array with row and column number to move from", expectedResult, m.getFrom());
	}
	
	@Test
	public void test_getTo() {
		Move m = new Move("white", 2, 2, 3, 2);
		int[] expectedResult = new int[] {3, 2};
		assertArrayEquals("Expected integer array with row and column number to move to", expectedResult, m.getTo());
	}
	
	@Test
	public void test_toString() {
		Move m = new Move("white", 2, 2, 3, 2);
		assertEquals("Expected string to display team colour and where the row and column numbers the piece will from and to", "white (2,2) to (3,2)", m.toString());
	}
}