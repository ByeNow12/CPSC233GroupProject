import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Arrays;

/**
* 2019-04-07
* Author: Shavonne
* Test for Move class, tests for the information required for a player to make a move
*/
public class MoveTest {
	
	/**
	* Tests the constructor for making a move 
	*/
	@Test
	public void test_constructorWithParams() {
		//Test setup, create Move object with parameters to test constructor
		Move m = new Move("white", 2, 1, 3, 1); 
		//Verification for the parameters of the constructor
		assertEquals("Expected token to be white", "white", m.getTeam());
		assertEquals("Expected row of piece to move from to be 2", 2, m.getFromRow());
		assertEquals("Expected column of piece to move from to be 1", 1, m.getFromCol());  
		assertEquals("Expected row to move piece to, to be 3", 3, m.getToRow());
		assertEquals("Expected column to move piece to, to be 1", 1, m.getToCol());
	}
	
	/**
	* Tests if the getter returns the correct team, in this case, white 
	*/
	@Test 
	public void test_getTeam_whiteTeam() {
		//Test setup
		Move m = new Move("white", 2, 1, 3, 1);
		//Verification
		assertEquals("Team making a move should be white", "white", m.getTeam());
	}
	
	/**
	* Tests if the getter returns the correct team, in this case, black
	*/
	@Test 
	public void test_getTeam_blackTeam() {
		//Test setup
		Move m = new Move("black", 2, 1, 3, 1);
		//Verification
		assertEquals("Team making a move should be black", "black", m.getTeam());
	}
	
	/**
	* Tests if the getter returns the correct row that the piece is moving from
	*/
	@Test
	public void test_getFromRow_row2() {
		//Test setup
		Move m = new Move("white", 2, 1, 3, 1);	
		//Verification
		assertEquals("Expected row to move from to be the second row", 2, m.getFromRow());
	}
	
	/**
	* Tests if the getter returns the correct column that the piece is moving from
	*/
	@Test
	public void test_getFromCol_col1() {
		//Test setup
		Move m = new Move("white", 2, 1, 3, 1);	
		//Verification
		assertEquals("Expected column to move to, to be the first column", 1, m.getFromCol());
	}
	
	/**
	* Tests if the getter returns the correct row that the piece is moving to
	*/
	@Test
	public void test_getToRow_row3() {
		//Test setup
		Move m = new Move("white", 2, 1, 3, 1);	
		//Verification
		assertEquals("Expected row to move from to be the third row", 3, m.getToRow());
	}
	
	/**
	* Tests if the getter returns the correct column that the piece is moving to
	*/
	@Test
	public void test_getToCol_col1() {
		//Test setup
		Move m = new Move("white", 2, 1, 3, 1);	
		//Verification
		assertEquals("Expected column to move to, to be the first column", 1, m.getToCol());
	}
	
	/**
	* Tests if the getter is returning the correct row and column that the user is moving the piece from
	*/
	@Test 
	public void test_getFrom() {
		//Test setup, create Move object with parameters to test the integer array with row and column of where to move from
		Move m = new Move("white", 2, 2, 3, 2);
		int[] expectedResult = new int[] {2, 2};
		//Verification 
		assertArrayEquals("Expected integer array with row and column number to move from", expectedResult, m.getFrom());
	}
	
	/**
	* Tests if the getter is returning the correct row and column that the user is moving the piece to
	*/
	@Test
	public void test_getTo() {
		//Test setup, create Move object with parameters to test the integer array with row and column of where to move to 
		Move m = new Move("white", 2, 2, 3, 2);
		int[] expectedResult = new int[] {3, 2};
		//Verification 
		assertArrayEquals("Expected integer array with row and column number to move to", expectedResult, m.getTo());
	}
	
	/**
	* Tests if the String displays the correct information about making a move
	*/
	@Test
	public void test_toString() {
		//Test setup, create Move object with parameters to test the toString 
		Move m = new Move("white", 2, 2, 3, 2);
		//Verification 
		assertEquals("Expected string to display team colour and where the row and column numbers the piece will from and to", "white (2,2) to (3,2)", m.toString());
	}
}
