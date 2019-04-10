package logic_package;

import java.util.ArrayList;

/**
* 2019-03-20
* Author: Riley
* AI class that evaluates the game condition and makes non-random moves to play against the player
*/
public class AI extends ComputerPlayer {

	private String[][] currentBoard;
	private String[][][] hBoards;
	private ArrayList<int[]> pieces;//stores positions of this AI's pieces
	//private char team;

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
  		
  		currentBoard = currentConfig.getBoard().getBoardPosition();
  		char team = super.getTeam();
  		//gets all the pieces' positions
  		pieces.clear();
  		for (int row = 0; row < 8; row++){
  			for (int col = 0; col < 8; col++){
  				if (currentBoard[row][col].charAt(0) == team){
  					pieces.add(new int[] {row,col});
  				}
  			}
  		}
  		
  		int[] moveValues = new int[pieces.size()];
  		for (int i = 0, i < pieces.size(); i++){
  			
  		}
		return null;
 	}

	/**
	*Calculates the most advantageous capture the piece in the specified position can make - assumes errors handled before method call
	*@param pos - the position of the piece for which you want to calculate the best move
	*@param board - the current game board
	*@return an array with the difference in points values between the piece and the piece it can 
	*take and the position of that piece, in the form {diff, row, col}, 
	*returns position as -1,-1 if no valid captures
  	*/

	private int[] getBestCapture(int[] pos, String[][] board){

		int pieceValue = 0;
		int takenValue = 0;
		int value;
		boolean[][] validMoves;
		int[] move;
		String type = board[pos[0]][pos[1]].substring(2);
		char team = super.getTeam();

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
		else{
			return new int[] {0,-1,-1};
		}
		move = new int[] {-1,-1};
		for (int row = 0; row < 8; row++){
			for (int col = 0; col < 8; col++){
				if (!validMoves[row][col]) continue;	//if it's not a valid move, skip it

				char pTeam = board[row][col].charAt(0);
				if (pTeam == '0' /*|| pTeam == team*/) continue;//skips if empty square (not yet being considered) or if position is already occupied by friendly piece

				String tType = board[row][col].substring(2);
				int pValue = 0;

				if (tType.equals("Pa")) pValue = Piece.PAWN_VALUE;
				else if (tType.equals("Bi")) pValue = Piece.BISHOP_VALUE;
				else if (tType.equals("Kn")) pValue = Piece.KNIGHT_VALUE;
				else if (tType.equals("Ro")) pValue = Piece.ROOK_VALUE;
				else if (tType.equals("Qu")) pValue = Piece.QUEEN_VALUE;
				else if (tType.equals("Ki")) pValue = Piece.KING_VALUE;

				if (pValue > takenValue){
					takenValue = pValue;
					move = new int[] {row,col};
				}
			}
		}
		value = takenValue - pieceValue;
		return new int[] {value, move[0], move[1]};
	}
	
	public int[] getBestSpaceMove(int[] pos, String[][] board){
		
		boolean[][] validMoves;
		int[] move;
		String type = board[pos[0]][pos[1]].substring(2);
		char team = super.getTeam();
		
		if (type.equals("Pa")){
			validMoves = Piece.calculatePawnMoves(board, pos, team);
		}
		else if (type.equals("Bi")){
			validMoves = Piece.calculateBishopMoves(board, pos, team);
		}
		else if (type.equals("Kn")){
			validMoves = Piece.calculateKnightMoves(board, pos, team);
		}
		else if (type.equals("Ro")){
			validMoves = Piece.calculateRookMoves(board, pos, team);
		}
		else if (type.equals("Qu")){
			validMoves = Piece.calculateQueenMoves(board, pos, team);
		}
		else if (type.equals("Ki")){
			validMoves = Piece.calculateKingMoves(board, pos, team);
		}
		else{
			return new int[] {-1,-1};
		}
		
		move = new int[] {-1,-1};
		
		for (int row = 2; row < 6; row++){
			for (int col = 2; col < 6; col++){
				//will aim for the center of the board, and get the closest move it can to the very center
				if (!validMoves[row][col]) continue;
				
				if ((row < 5 && row > 2)&&(col < 5 && col > 2)){
					move = new int[] {row,col};
					continue;
				}
				
				if ((row < 5 && row > 2)||(col < 5 && col > 2)){
					move = new int[] {row,col};
					continue;
				}
				move = new int[] {row,col};
			}
		}
		
		return move;
	}

 	public static void main(String[] args) {

	}
}
