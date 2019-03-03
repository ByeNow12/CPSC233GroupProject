import java.util.ArrayList;
import java.util.Arrays;

public class GameConfiguration {

	private Board board;
	
	//Constructor
	public GameConfiguration(){
		board = new Board();
		board.defaultPositions();
	}

	/**
	*getter method for board instance variable
	*@return: board:Board
	*/
	public Board getBoard() {
		return board;
	}

	/**
	* updates board positions in accordance with the passed move object
	*@param: move: Move, the change in the board position to be done.
	*/
	public void update(Move move){
		int[] lastMove = move.getFrom();
		int[] currentMove = move.getTo();
		String token = board.getBoardPosition()[lastMove[0]][lastMove[1]];
		board.setBoardPositions(lastMove[0], lastMove[1], "0");
		board.setBoardPositions(currentMove[0], currentMove[1], token);
	}

	/**
	* Checks if the passed move object is a valid move and returns true if it is
	* @param: move:Move, the change in the board position to be done.
	* @return: boolean, whether or not the move is valid.
	*/
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

	/**
	* Checks if the king in belonging to token is captured. Checks if the game has ended.
	* @param: token: char, the team to be checked for winning
	* @return: boolean, has the team won
	*/
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

	/**
	* returns all the valid moves of the team belonging to token
	* @param: token: char, the team to find all valid moves
	* @return: ArrayList of Move objects
	*/
	public ArrayList<Move> getAllValidMoves(char token){
		ArrayList<Move> moveArrayList = new ArrayList<Move>();
		boolean[][] unconvertedMoves = new boolean[8][8];
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (board.getBoardPosition()[x][y].charAt(0) == token) {
					int[] pos = new int[2];
					pos[0] = x;
					pos[1] = y;
					if (board.getBoardPosition()[x][y].substring(2).equals("Ro")) {
						unconvertedMoves = Piece.calculateRookMoves(board.getBoardPosition(), pos, token);
					}
					else if (board.getBoardPosition()[x][y].substring(2).equals("Pa")) {
						unconvertedMoves = Piece.calculatePawnMoves(board.getBoardPosition(), pos, token);
					}
					else if (board.getBoardPosition()[x][y].substring(2).equals("Kn")) {
						unconvertedMoves = Piece.calculateKnightMoves(board.getBoardPosition(), pos, token);
					}
					else if (board.getBoardPosition()[x][y].substring(2).equals("Bi")) {
						unconvertedMoves = Piece.calculateBishopMoves(board.getBoardPosition(), pos, token);
					}
					else if (board.getBoardPosition()[x][y].substring(2).equals("Ki")) {
						unconvertedMoves = Piece.calculateKingMoves(board.getBoardPosition(), pos, token);
					}
					else if (board.getBoardPosition()[x][y].substring(2).equals("Qu")) {
						unconvertedMoves = Piece.calculateQueenMoves(board.getBoardPosition(), pos, token);
					}
					for (int i = 0; i < 8; i++) {
						for (int n = 0; n < 8; n++) {
							if (unconvertedMoves[i][n]) {
								if (token == 'w') {
									Move move = new Move("white", x, y, i, n);
									moveArrayList.add(move);
								}
								else {
									Move move = new Move("black", x, y, i, n);
									moveArrayList.add(move);
								}
							}
						}
					}
				}
			}
		}
		
		return moveArrayList;
	}
	
	public boolean isCheck(char team) {
		ArrayList<Move> possibleMoves = this.getAllValidMoves(team);
		int[] enemyKingLoc = new int[2];
		enemyKingLoc[0] = 10;
		enemyKingLoc[1] = 10;
		boolean isCheckBoolean = false;
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if (board.getBoardPosition()[x][y].charAt(0) != team && board.getBoardPosition()[x][y].charAt(0) != '0') {
					if (board.getBoardPosition()[x][y].substring(2).equals("Ki")) {
						enemyKingLoc[0] = x;
						enemyKingLoc[1] = y;
					}
				}
			}
		}
		if (enemyKingLoc[0] == 10) {
			return false;
		}
		for (Move move : possibleMoves) {
			if (move.getTo()[0] == enemyKingLoc[0] && move.getTo()[1] == enemyKingLoc[1]) {
				isCheckBoolean = true;
			}
		}
		return isCheckBoolean;
	}

	public static void main(String[] args) {
		GameConfiguration config = new GameConfiguration();
		config.getBoard().draw();
		config.getBoard().setBoardPositions(5, 4, "w_Qu");
		config.getBoard().draw();
		System.out.println(config.isCheck('w'));
	}

}