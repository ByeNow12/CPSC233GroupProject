/**
* 2019-03-06
* Author: Carmen, Shavonne
* Class contains data needed to make a single move for players
*/
public class Move {

	private String token; //the team represented by colour (black or white)
	private int fromRow; //row that the piece is moving from
	private int fromCol; //column that the piece is moving from
	private int toRow; //row that the piece is moving to
	private int toCol; //column that the piece is moving to
	
	/**
	* Constructor for making a move
	* @param token, a String representing the team colour
	* @param fromRow, the row number for the piece to move from
	* @param fromCol, the column number for the piece to move from
	* @param toRow, the row number for the piece to move to
	* @param toCol, the column number for the piece to move to
	*/
	public Move(String token, int fromRow, int fromCol, int toRow, int toCol){
		this.token = token;
		this.fromRow = fromRow;
		this.fromCol = fromCol;
		this.toRow = toRow;
		this.toCol = toCol;
	}

	/** 
	* Gets the team of the player making the move (black or white)
	* @return token, the colour of the team as a String
	*/
	public String getTeam() {
		return token;
	}
	
	/**
	* Gets the row number of the piece that the player wants to move 
	* @return fromRow, the row number to move from
	*/
	public int getFromRow() {
		return fromRow;
	}
	
	/**
	* Gets the column number of the piece that the player wants to move
	* @return fromCol, the column number to move from
	*/
	public int getFromCol() {
		return fromCol;
	}
	
	/**
	* Gets the row number on the board to move the piece to
	* @return toRow, the row number to move to
	*/
	public int getToRow() {
		return toRow;
	}
	
	/**
	* Gets the column number on the board to move the piece to
	* @return toCol, the column number to move to
	*/
	public int getToCol() {
		return toCol;
	}
	
	/** Gets the row and column that the user is moving the piece from and presents it as an integer array of size 2
	* Changed from int to int[2] so that it accomodates the format of the piece positions in Piece class
	* @return fromVector, the user's input as an integer array of size 2
	*/
	public int[] getFrom(){
		int[] fromVector = new int[2];
		fromVector[0] = fromRow;
		fromVector[1] = fromCol;
		return fromVector;
	}

	/** Gets the row and column that the user is moving the piece to and presents it as an integer array of size 2
	* Changed from int to int[2] so that it accomodates the format of the piece positions in Piece class
	* @return toVector, the user's input (for the position that they want to move the piece to) as an integer array of size 2
	*/
	public int[] getTo(){
	int[] toVector = new int[2];
		toVector[0] = toRow;
		toVector[1] = toCol;
		return toVector;
	}

	/** For troubleshooting purposes, displays the information to make a move
	* @return token, fromRow, fromCol, toRow, toCol, to check that input is being interpreted correctly
	*/
	public String toString(){
		return token + " (" + fromRow + "," + fromCol + ") to (" + toRow + "," + toCol + ")";
	}
}