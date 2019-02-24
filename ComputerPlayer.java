public class ComputerPlayer extends Game {
	private String token;
	private Move lastMove;

  //Constructors
  public ComputerPlayer(String aToken, Move theLastMove) { //default
    super();
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
  
  /** Setter method to set the move for the computer
  * @param tokenToMove, the piece that the computer wants to move 
  */
  public void setMove(String tokenToMove) {
    Piece p1 = new Piece(); //randomly select one existing chess piece on the board
    p1.CalculatePieceMoves("newPiece", newPosition, "newTeam"); //compute all legal destinations in which that piece could move
    p1.getAllValidMoves(tokenToMove);
    p1.isValidMove(tokenToMove); 
    if (p1.isValidMove(move) != true;) {
      Piece p2 = new Piece(); //if there are no legal destinations, choose a new piece
    }
    //Randomly select one of the computed legal destinations to move the piece to
    
  }
   
}

