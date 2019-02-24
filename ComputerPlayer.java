///Code does not yet compile/incomplete
public class ComputerPlayer extends Game {
	private String token;
	private Move lastMove; //unsure what this is referring to?

  //Constructors
	public ComputerPlayer(String aToken, Move theLastMove) { //default
 		super();
    		this.token = aToken;
 	 }
  
	public ComputerPlayer(ComputerPlay toCopy) { //copy constructor
   		super(toCopy);
		this.token = toCopy.token;
    		this.lastMove = toCopy.lastMove;
  	}
  
 	/** Getter method for getting the move for the computer player
  	* @param currentConfig, 
  	* @return a random valid move until we implement the AI
 	 */
  	public Move getMove(GameConfiguration currentConfig) {
		return lastMove; 
 	}
  
  	/** Setter method to make a random valid move for the computer
 	 * @param rowNum, the row number of the piece the computer wants to move
	 * @param colNum, the column number of the piece the computer wants to move
  	*/
  	public void setMove(Move rowNum, Move colNum ) {
    		Piece p1 = new Piece(); //randomly select one existing chess piece on the board
    		p1.CalculatePieceMoves("newPiece", newPosition, "newTeam"); //compute all legal destinations in which that piece could move
    		p1.getAllValidMoves(tokenToMove);
    		p1.isValidMove(tokenToMove); 
    		
		if (p1.isValidMove(move) != true) { //check if move is valid
      			Move movePiece = new Move();
			movePiece.getTo(); //if move valid, call getTo method from Move class to move piece to row and column number
			//Randomly select one of the computed legal destinations to move the piece to
		}
    	}
	
 	public static void main(String[] args) {
		ComputerPlayer p2 = new ComputerPlayer("black");
		GameConfiguration config = new GameConfiguration();
	}
}

