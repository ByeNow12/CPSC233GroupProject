public class GameConfiguration {
	
	private Board board = new Board();
	
	public Board getBoard() {
		return board;
	}
	
	public void update(Move move){
		int[] lastMove = move.getFrom();
		int[] currentMove = move.getTo();
		String token = board.getBoardPosition()[lastMove[0]][lastMove[1]];
		board.setBoardPositions(lastMove[0], lastMove[1], "0");
		board.setBoardPositions(currentMove[0], currentMove[1], token);
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
	
	public static void main(String[] args) {
		GameConfiguration config = new GameConfiguration();
		Move m = new Move("t", 0, 0, 5, 5);
		Board b = config.getBoard();
		b.defaultPositions();
		b.draw();
		config.update(m);
		b.draw();
	}
	
	
}