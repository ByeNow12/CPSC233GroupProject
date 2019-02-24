public class GameConfiguration {
	
	private Board board = new Board();
	
	public void update(Move move){
		// to do later
	}
	
	public boolean isValidMove(Move move){ //checks if the argument is a valid move
		return false; //todo actual validation logic here
	}
	
	public boolean hasWon(String token){ //checks if any player has won yet 
		//todo later
		return false;
	}
	
	public Move[] getAllValidMoves(String token){ //gets all the possible valid moves of a piece of a certain player
		//todo later
		return new Move[0];
	}
	
	
}
