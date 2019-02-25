public class ComputerPlayer {
	private String token;
	private Move lastMove; //computer considers the last move made by human player to make its move, used when AI developed
	//lastMove will be left alone for now
        private GameConfiguration config; //TODO This needs to be changed so that our gameconfig is passed into a constructor

  	//Constructors
	public ComputerPlayer(String aToken) { //default
    		this.token = aToken;
 	 }
  
	public ComputerPlayer(ComputerPlayer toCopy) { //copy constructor
		this.token = toCopy.token;
    		this.lastMove = toCopy.lastMove;
  	}
  	
 	/** Getter method for getting the move for the computer player
  	* @param currentConfig,
  	* @return a random valid move until we implement the AI
 	*/
  	public Move getMove(GameConfiguration currentConfig) {
		return lastMove; //obtain move based on values from the setter
 	}
  
  	/** Setter method to make a random valid move for the computer player
 	* @param fromRowNum, the row number of the piece the computer wants to move from
	* @param fromColNum, the column number of the piece the computer wants to move from
	* @param toRowNum, the row number the computer wants to move to 
	* @param toColNum, the column number the computer wants to move to 
  	*/
  	public void setMove(int fromRowNum, int fromColNum, int toRowNum, int toColNum) {
		//while the row/column numbers in range of 0-8, Move class called to make a move
		while ((toRowNum >= 0 && toColNum >= 0) && (toRowNum <= 8 && toColNum <= 8)) {
			Move compMove = new Move(token, fromRowNum, fromColNum, toRowNum, toColNum); //call Move class to make a move based on the row/column number 
			if (config.isValidMove(compMove) == true) { //check if move is valid
				compMove.getTo(); //if valid, move piece to desired row and column number 
			}
		}
    	}
	
 	public static void main(String[] args) {
		ComputerPlayer p2 = new ComputerPlayer("black");
		GameConfiguration config = new GameConfiguration();
	}
}

