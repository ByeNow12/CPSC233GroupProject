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
		char pieceColor = token.charAt(0);
		String pieceColorString;
		if (pieceColor == 'w') {
			pieceColorString = "white";
		}
		else {
			pieceColorString = "black";
		}
		String pieceType = token.substring(3);
		String[] pieces = {"Ro", "Kn", "Bi", "Qu", "Ki", "Pa"};
		String[][] boardPositions = board.getBoardPosition();
		for (String type : pieces) {
			if (pieceType == type) {
				if (type.equals("Ro")) {
					return Piece.calculateRookMoves(boardPositions, lastMove, pieceColorString)[currentMove[0]][currentMove[1]];
				}
				else if (type.equals("Kn")) {
					return Piece.calculateKnightMoves(boardPositions, lastMove, pieceColorString)[currentMove[0]][currentMove[1]];
				}
				else if (type.equals("Bi")) {
					return Piece.calculateBishopMoves(boardPositions, lastMove, pieceColorString)[currentMove[0]][currentMove[1]];
				}
				else if (type.equals("Qu")) {
					return Piece.calculateQueenMoves(boardPositions, lastMove, pieceColorString)[currentMove[0]][currentMove[1]];
				}
				else if (type.equals("Ki")) {
					return Piece.calculateKingMoves(boardPositions, lastMove, pieceColorString)[currentMove[0]][currentMove[1]];
				}
				else {
					return Piece.calculatePawnMoves(boardPositions, lastMove, pieceColorString)[currentMove[0]][currentMove[1]];
				}
			}
		}
                return false; // TODO I only put this here to make it compile, it should be checked to make sure it's actually correct
	}
	
	public boolean hasWon(String token){
		boolean wKingPresent = false;
		boolean bKingPresent = false;
		for (int i = 0; i < 8; i++) {
            for (int x = 0; x < 8; x++) {
				if (board.getBoardPosition()[x][i].equals("w_Ki")) {
					wKingPresent = true;
				}
				if (board.getBoardPosition()[x][i].equals("b_Ki")) {
					bKingPresent = true;
				}
			}
		}
		return (!wKingPresent || !bKingPresent);
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