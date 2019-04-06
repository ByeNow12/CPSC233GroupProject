package logic_package;

/**
* 2019-03-20
* Author: Riley
* AI class that evaluates the game condition and makes non-random moves to play against the player
*/
public class AI extends ComputerPlayer {

	private String[][] currentBoard;
	private String[][][] hBoards;
	private int[][] pieces;
	private char team;

	public AI(char team){
		super(team);
	}
 	/** 
	* Getter method for getting the move for the computer player
  	* @param
  	* @return 
 	*/
 	@Override
  	public Move getMove(GameConfiguration currentConfig) {
		return null;
 	}

	/**
	*Calculates the most advantageous capture the piece in the specified position can make - assumes errors handled before method call
	*@param pos - the position of the piece for which you want to calculate the best move
	*@param board - the current game board
	*@return an array with the difference in points values between the piece and the piece it can take and the position of that piece, in the form {diff, row, col}
  	*/

	private int[] getBestCapture(int[] pos, String[][] board){

		int pieceValue;
		int takenValue;
		int value;
		boolean[][] validMoves;
		int[] move;
		String type = board[pos[0]][pos[1]].substring(2);

		if (type.equals("Pa")){
			validMoves = Piece.calculatePawnMoves(board, pos, team);
			pieceValue = Piece.PAWN_VALUE;
		}
		else if (type.equals("Bi")){
			validMoves = Piece.calculateBishopMoves(board, pos, team);
			pieceValue = Piece.BISHOP_VALUE;
		}
		else if (type.equals("Kn")){
			validMoves = Piece.calculateKnightMoves(board, pos, team);
			pieceValue = Piece.KNIGHT_VALUE;
		}
		else if (type.equals("Ro")){
			validMoves = Piece.calculateRookMoves(board, pos, team);
			pieceValue = Piece.ROOK_VALUE;
		}
		else if (type.equals("Qu")){
			validMoves = Piece.calculateQueenMoves(board, pos, team);
			pieceValue = Piece.QUEEN_VALUE;
		}
		else if (type.equals("Ki")){
			validMoves = Piece.calculateKingMoves(board, pos, team);
			pieceValue = Piece.KING_VALUE;
		}

		for (int row = 0; row < 8; i++){
			for (int col = 0; col < 8; j++){
				if (!validMoves[i][j]) continue;	//if it's not a valid move, skip it

				char pTeam = board[row][col].charAt(0);
				if (pTeam == '0' || pTeam == team) continue;//skips if empty square (not yet being considered) or if position is already occupied by friendly piece

				String pType = board[row][col].substring(2);
				int pValue;

				if (tType.equals("Pa")) pValue = Piece.PAWN_VALUE;
				else if (tType.equals("Bi")) pValue = Piece.BISHOP_VALUE;
				else if (tType.equals("Kn")) pValue = Piece.KNIGHT_VALUE;
				else if (tType.equals("Ro")) pValue = Piece.ROOK_VALUE;
				else if (tType.equals("Qu")) pValue = Piece.QUEEN_VALUE;
				else if (tType.equals("Ki")) pValue = Piece.KING_VALUE;

				if (pValue > takenValue){
					takenValue = pValue;
					move = {row,col};
				}
			}
		}
		value = takenValue - pieceValue;
		return {value, move[0], move[1]};
}

 	public static void main(String[] args) {

	}
}
