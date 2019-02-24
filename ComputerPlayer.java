///Code does not yet compile/incomplete
public class ComputerPlayer {
	private String token;
	private Move lastMove; //computer considers the last move made by human player to make its move, used when AI developed
	//lastMove will be left alone for now

  	//Constructors
	public ComputerPlayer(String aToken) { //default
    		this.token = aToken;
 	 }
  
	public ComputerPlayer(ComputerPlayer toCopy) { //copy constructor
   		super(toCopy);
		this.token = toCopy.token;
    		this.lastMove = toCopy.lastMove;
  	}
  	
 	/** Getter method for getting the move for the computer player
  	* @param currentConfig, 
  	* @return a random valid move until we implement the AI
 	 */
  	public Move getMove(GameConfiguration currentConfig) {
		return compMove; //obtain move based on values from the setter
 	}
  
  	/** Setter method to make a random valid move for the computer
 	 * @param rowNum, the row number of the piece the computer wants to move
	 * @param colNum, the column number of the piece the computer wants to move
  	*/
  	public void setMove(int rowNum, int colNum) {
		//while the row/column numbers in range of 0-8, Move class called to make a move
		while ((rowNum && colNum) >= 0) && ((rowNum && colNum) <= 8) { 
			Move compMove = new Move(rowNum, colNum); //call Move class to make a move based on the row/column numbers of piece 
			if (compMove.isValidMove() == true) { //check if move is valid
				compMove.getTo(); //if valid, move piece to desired row and column number 
			}
		}
    	}
	
 	public static void main(String[] args) {
		ComputerPlayer p2 = new ComputerPlayer("black");
		GameConfiguration config = new GameConfiguration();
	}
}

