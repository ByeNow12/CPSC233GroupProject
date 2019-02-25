public class GameConfiguration {

	private Board board;

	public GameConfiguration(){
		board = new Board();
		board.defaultPositions();
	}

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
		if (pieceColor == '0'){ return false;} //just for the random ai move method

		String pieceType = token.substring(2);
		String[][] boardPositions = board.getBoardPosition();
		if (Character.toUpperCase(pieceColor) != move.getTeam().charAt(0)) {
			return false;
		}
		else if (pieceType.equals("Ro")) {
			return Piece.calculateRookMoves(boardPositions, lastMove, pieceColor)[currentMove[0]][currentMove[1]];
		}
		else if (pieceType.equals("Kn")) {
			return Piece.calculateKnightMoves(boardPositions, lastMove, pieceColor)[currentMove[0]][currentMove[1]];
		}
		else if (pieceType.equals("Bi")) {
			return Piece.calculateBishopMoves(boardPositions, lastMove, pieceColor)[currentMove[0]][currentMove[1]];
		}
		else if (pieceType.equals("Qu")) {
			return Piece.calculateQueenMoves(boardPositions, lastMove, pieceColor)[currentMove[0]][currentMove[1]];
		}
		else if (pieceType.equals("Ki")) {
			return Piece.calculateKingMoves(boardPositions, lastMove, pieceColor)[currentMove[0]][currentMove[1]];
		}
		else if (pieceType.equals("Pa")) {
			return Piece.calculatePawnMoves(boardPositions, lastMove, pieceColor)[currentMove[0]][currentMove[1]];
		}
                return false; //just in case some fluke accident occurs
	}

	public boolean hasWon(char token){
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
                if (token == 'w') return !bKingPresent;
		return !wKingPresent;
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
