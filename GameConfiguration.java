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
		int[] lastMove = move.getFrom();
		int[] currentMove = move.getTo();
		String token = board.getBoardPosition()[lastMove[0]][lastMove[1]];
		pieceColor = token.charAt(0);
		if (pieceColor == 'w') {
			string pieceColorString = "white";
		}
		else {
			string pieceColorString = "black";
		}
		pieceType = token.substring(3);
		Piece selectedPiece = new Piece();
		String[] pieces = {"Ro", "Kn", "Bi", "Qu", "Ki", "Pa"};
		for (type : pieces) {
			if (pieceType == type) {
				if (type == "Ro") {
					return selectedPiece.calculateRookMoves(board, lastMove, pieceColorString)[currentMove[0]][currentMove[1]];
				}
				else if (type.equals("Kn")) {
					return selectedPiece.calculateKnightMoves(board, lastMove, pieceColorString)[currentMove[0]][currentMove[1]];
				}
				else if (type.equals("Bi")) {
					return selectedPiece.calculateBishopMoves(board, lastMove, pieceColorString)[currentMove[0]][currentMove[1]];
				}
				else if (type.equals("Qu")) {
					return selectedPiece.calculateQueenMoves(board, lastMove, pieceColorString)[currentMove[0]][currentMove[1]];
				}
				else if (type.equals("Ki")) {
					return selectedPiece.calculateKingMoves(board, lastMove, pieceColorString)[currentMove[0]][currentMove[1]];
				}
				else {
					return selectedPiece.calculatePawnMoves(board, lastMove, pieceColorString)[currentMove[0]][currentMove[1]];
				}
			}
		}
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