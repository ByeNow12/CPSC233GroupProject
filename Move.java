public class Move {

	private String token;
	private int fromRow; //row that the piece is moving from
	private int fromCol; //column that the piece is moving from
	private int toRow; //row that the piece is moving to
	private int toCol; //column that the piece is moving to

	public Move(String token, int fromRow, int fromCol, int toRow, int toCol){
		this.token = token;
		this.fromRow = fromRow;
		this.fromCol = fromCol;
		this.toRow = toRow;
		this.toCol = toCol;
	}

	/** Retrieves the team of the player making the move (black or white)
	* @return token, the colour of the team as a String
	*/
	public String getTeam() {
		return token;
	}

	/** Gets the row and column that the user is moving the piece from and presents it as an integer array of size 2
	* Changed from int to int[2] so that it accomodates the format of the piece positions in Piece class
	@return fromVector, the user's input as an integer array of size 2
	*/
	public int[] getFrom(){
		int[] fromVector = new int[2];
		fromVector[0] = fromRow;
		fromVector[1] = fromCol;
		return fromVector;
	}

	/** Gets the row and column that the user is moving the piece to and presents it as an integer array of size 2
	* Changed from int to int[2] so that it accomodates the format of the piece positions in Piece class
	@return toVector, the user's input (for the position that they want to move the piece to) as an integer array of size 2
	*/
	public int[] getTo(){
	int[] toVector = new int[2];
		toVector[0] = toRow;
		toVector[1] = toCol;
		return toVector;
	}

	/** For troubleshooting purposes
	* @return token, fromRow, fromCol, toRow, toCol, to check that input is being interpreted correctly
	*/
	public String toString(){
		return token + " ("+fromRow+","+fromCol+") to ("+toRow+","+toCol+")";
	}

}
