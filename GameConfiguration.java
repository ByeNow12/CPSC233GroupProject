public class GameConfiguration {
	
	private Board board = new Board();
	
	public void update(Move move){
		// to do later
	}
	
	public boolean isValidMove(Move move){
		return false; //todo actual validation logic here
	}
	
	public boolean hasWon(String token){
		//todo later
		return false;
	}
	
	public Move[] getAllValidMoves(String token){
		//todo later
		return new Move[0];
	}
	
	
}