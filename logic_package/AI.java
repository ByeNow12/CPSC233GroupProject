package logic_package;

import java.util.ArrayList;

/**
* 2019-03-20
* Author: Riley
* AI class that evaluates the game condition and makes non-random moves to play against the player
*/
public class AI extends ComputerPlayer {

	private String[][] currentBoard;
	private ArrayList<int[]> pieces = new ArrayList<int[]>();//stores positions of this AI's pieces

	public AI(char team){
		super(team);
	}
 	/**
	* Getter method for getting the move for the computer player
  	* @param the current GameConfiguration object
  	* @return the move calculated by the computer
 	*/
 	@Override
  	public Move getMove(GameConfiguration currentConfig) {

  		currentBoard = currentConfig.getBoard().getBoardPosition();
  		char team = super.getTeam();
		boolean[][] opMoves = getAllOpponentValidMoves(currentBoard, team);

  		//gets all the pieces' positions
  		pieces.clear();
  		for (int row = 0; row < 8; row++){
  			for (int col = 0; col < 8; col++){
  				if (currentBoard[row][col].charAt(0) == team){
  					pieces.add(new int[] {row,col});
  				}
  			}
  		}

		if (currentConfig.isCheck(team)){
			//first checks if king can move out of check
			int[] kingPos = {0, 0};
			boolean[][] validMoves;
			for (int i = 0; i < pieces.size(); i++){
				int[] pPos = pieces.get(i);
				if (currentBoard[pPos[0]][pPos[1]].equals(team +"_Ki")){
					kingPos = pPos;
					break;
				}
			}

			validMoves = Piece.calculateKingMoves(currentBoard, kingPos, team);

			for (int row = 0; row < 8; row++){
				for (int col = 0; col < 8; col++){
					if (validMoves[row][col] && !opMoves[row][col]){
						return new Move("AI", kingPos[0], kingPos[1], row, col);
					}
				}
			}
		}

		int[][] moves = new int[pieces.size()][];
		int best = -1;

		for (int i = 0; i < pieces.size(); i++){
			moves[i] = getBestCapture(pieces.get(i), currentBoard, team, opMoves);
			if (best == -1 && moves[i][0] > 0) best = i;
			else if (best > -1 && moves[i][0] > moves[best][0]) best = i;
		}

		if (best > -1){
			return new Move("AI", pieces.get(best)[0], pieces.get(best)[1], moves[best][1], moves[best][2]);
		}

  		for (int i = 0; i < pieces.size(); i++){
			moves[i] = getBestSpaceMove(pieces.get(i), currentBoard, team, opMoves);
			if (best == -1 && moves[i][0] > 0) best = i;
			else if (best > -1 && moves[i][0] > moves[best][0]) best = i;
  		}
		if (best > -1){
			return new Move("AI", pieces.get(best)[0], pieces.get(best)[1], moves[best][1], moves[best][2]);
		}

		for (int i = 0; i < pieces.size(); i++){
			moves[i] = getSpaceMove(pieces.get(i), currentBoard, team, opMoves);
			if (best == -1 && moves[i][0] > 0) best = i;
			else if (best > -1 && moves[i][0] > moves[best][0]) best = i;
  		}
		if (best > -1){
			return new Move("AI", pieces.get(best)[0], pieces.get(best)[1], moves[best][1], moves[best][2]);
		}
		
		return defaultMove(currentBoard, team, pieces, currentConfig);
 	}

	/**
	*Calculates the most advantageous capture the piece in the specified position can make - assumes errors handled before method call
	*@param pos - the position of the piece for which you want to calculate the best move
	*@param board - the current game board
	*@return an array with the difference in points values between the piece and the piece it can
	*take and the position of that piece, in the form {diff, row, col},
	*returns position as -1,-1 if no valid captures
  	*/

	private int[] getBestCapture(int[] pos, String[][] board, char team, boolean[][] opMoves){

		int pieceValue = 0;
		int value = 0;
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
		else{
			return new int[] {0,-1,-1};
		}

		move = new int[] {-1,-1};
		int best = 0;
		if (opMoves[pos[0]][pos[1]]) value = pieceValue;

		for (int row = 0; row < 8; row++){
			for (int col = 0; col < 8; col++){
				if (!validMoves[row][col]) continue;	//if it's not a valid move, skip it

				char pTeam = board[row][col].charAt(0);
				if (pTeam == '0') continue;		//skips if empty square (not yet being considered) or if position is already occupied by friendly piece

				String pType = board[row][col].substring(2);
				int pValue = value;

				if (pType.equals("Pa")) pValue = Piece.PAWN_VALUE;
				else if (pType.equals("Bi")) pValue = Piece.BISHOP_VALUE;
				else if (pType.equals("Kn")) pValue = Piece.KNIGHT_VALUE;
				else if (pType.equals("Ro")) pValue = Piece.ROOK_VALUE;
				else if (pType.equals("Qu")) pValue = Piece.QUEEN_VALUE;
				else if (pType.equals("Ki")) pValue = Piece.KING_VALUE;
				
				board[row][col] = "0";
				if (getAllOpponentValidMoves(board, team)[row][col]) pValue -= pieceValue;
				
				if (pValue > best){
					best = pValue;
					move[0] = row;
					move[1] = col;
				}
				board[row][col] = pTeam + "_" + pType;
			}
		}
		return new int[] {best, move[0], move[1]};
	}

	/**
	*Calculates the most advantageous move into an empty square the piece in the specified position can make - assumes errors handled before method call
	*@param pos - the position of the piece for which you want to calculate the best move
	*@param board - the current game board
	*@return an array with value of the returen move and the end position of the move, in the form {value, row, col},
	*returns position as -1,-1 if no good valid moves
  	*/

	private int[] getBestSpaceMove(int[] pos, String[][] board, char team, boolean[][] opMoves){

		int pValue = 0;		//will assign piece values based on which is most advantageous to have in the middle of the board
		boolean[][] validMoves;
		int[] move;
		String type = board[pos[0]][pos[1]].substring(2);

		if (type.equals("Pa")){
			validMoves = Piece.calculatePawnMoves(board, pos, team);
			pValue = Piece.PAWN_VALUE;
		}
		else if (type.equals("Bi")){
			validMoves = Piece.calculateBishopMoves(board, pos, team);
			pValue = Piece.BISHOP_VALUE;
		}
		else if (type.equals("Kn")){
			validMoves = Piece.calculateKnightMoves(board, pos, team);
			pValue = Piece.KNIGHT_VALUE;
		}
		else if (type.equals("Ro")){
			validMoves = Piece.calculateRookMoves(board, pos, team);
			pValue = Piece.ROOK_VALUE;
		}
		else if (type.equals("Qu")){
			validMoves = Piece.calculateQueenMoves(board, pos, team);
			pValue = Piece.QUEEN_VALUE;
		}
		else if (type.equals("Ki")){
			validMoves = Piece.calculateKingMoves(board, pos, team);
			pValue = -1;		//Having a king in the middle is a bad idea
		}
		else{
			return new int[] {0,-1,-1};
		}

		move = new int[] {-1,-1};
		if (opMoves[pos[0]][pos[1]]) pValue += 10; //this makes this piece the priority to move if it is about to be captured

		for (int row = 2; row < 6; row++){
			for (int col = 2; col < 6; col++){
				//will aim for the center of the board, and get the closest move it can to the very center
				if (!validMoves[row][col]) continue;

				if (opMoves[row][col]) continue;

				if ((row < 5 && row > 2)&&(col < 5 && col > 2)){
					move = new int[] {row,col};
					continue;
				}

				if ((row < 5 && row > 2)||(col < 5 && col > 2)){
					move = new int[] {row,col};
					continue;
				}
			}
		}

		if (move[0] == -1) pValue = -1;

		return new int[] {pValue, move[0], move[1]};
	}

	private int[] getSpaceMove(int[] pos, String[][] board, char team, boolean[][] opMoves){	//creating another method because cretain peices are less useless on the sides than others, so different values and different move selection logic

		int pValue = 0;		
		boolean[][] validMoves;
		int[] move;
		String type = board[pos[0]][pos[1]].substring(2);

		if (type.equals("Pa")){
			validMoves = Piece.calculatePawnMoves(board, pos, team);
			pValue = 2;
		}
		else if (type.equals("Bi")){
			validMoves = Piece.calculateBishopMoves(board, pos, team);
			pValue = 2;
		}
		else if (type.equals("Kn")){
			validMoves = Piece.calculateKnightMoves(board, pos, team);
			pValue = 0;
		}
		else if (type.equals("Ro")){
			validMoves = Piece.calculateRookMoves(board, pos, team);
			pValue = 1;
		}
		else if (type.equals("Qu")){
			validMoves = Piece.calculateQueenMoves(board, pos, team);
			pValue = 1;
		}
		else if (type.equals("Ki")){
			validMoves = Piece.calculateKingMoves(board, pos, team);
			pValue = 1;		
		}
		else{
			return new int[] {0,-1,-1};
		}

		move = new int[] {-1,-1};
		if (opMoves[pos[0]][pos[1]]) pValue += 3;

		for (int row = 2; row < 6; row++){
			for (int col = 2; col < 6; col++){
			
				if (!validMoves[row][col]) continue;

				if (opMoves[row][col]) continue;
				
				move[0] = row;
				move[1] = col;
			}
		}

		if (move[0] == -1) pValue = -1;

		return new int[] {pValue, move[0], move[1]};
	}

	private Move defaultMove(String[][] board, char team, ArrayList<int[]> pieces, GameConfiguration config){

		int[] pos = new int[] {-1,-1};
		boolean[][] validMoves;
		
		for (int i = 0; i < pieces.size(); i++){
			int[] pPos = pieces.get(i);
			if (board[pPos[0]][pPos[1]].equals(team + "_Pa")){
				pos = pPos;
				break;
			}
		}


		if (pos[0] >= 0){
			validMoves = Piece.calculatePawnMoves(board, pos, team);
			for (int row = 0; row < 8; row++){
				for (int col = 0; col < 8; col++){
					if (validMoves[row][col]) return new Move("AI", pos[0], pos[1], row, col);
				}
			}
		}

		for (int i = 0; i < pieces.size(); i++){
			int[] pPos = pieces.get(i);
			if (board[pPos[0]][pPos[1]].equals(team + "_Bi")){
				pos = pPos;
				break;
			}
		}


		if (pos[0] >= 0){
			validMoves = Piece.calculateBishopMoves(board, pos, team);
			for (int row = 0; row < 8; row++){
				for (int col = 0; col < 8; col++){
					if (validMoves[row][col]) return new Move("AI", pos[0], pos[1], row, col);
				}
			}
		}

		for (int i = 0; i < pieces.size(); i++){
			int[] pPos = pieces.get(i);
			if (board[pPos[0]][pPos[1]].equals(team + "_Kn")){
				pos = pPos;
				break;
			}
		}


		if (pos[0] >= 0){
			validMoves = Piece.calculateKnightMoves(board, pos, team);
			for (int row = 0; row < 8; row++){
				for (int col = 0; col < 8; col++){
					if (validMoves[row][col]) return new Move("AI", pos[0], pos[1], row, col);
				}
			}
		}

		return super.getRandomMove(config);
	}

	private boolean[][] getAllOpponentValidMoves(String[][] board, char team){
		boolean[][] validMoves = new boolean[8][8];
		for (int row = 0; row < 8; row++){
			for (int col = 0; col < 8; col++){
				char pTeam = board[row][col].charAt(0);
				if (pTeam == '0' || pTeam == team) continue;
				String type = board[row][col].substring(2);
				if (type.equals("Pa")){
					validMoves = combine(validMoves, Piece.calculatePawnMoves(board, new int[] {row,col}, team));
				}
				else if (type.equals("Bi")){
					validMoves = combine(validMoves, Piece.calculateBishopMoves(board, new int[] {row,col}, team));
				}
				else if (type.equals("Kn")){
					validMoves = combine(validMoves, Piece.calculateKnightMoves(board, new int[] {row,col}, team));
				}
				else if (type.equals("Ro")){
					validMoves = combine(validMoves, Piece.calculateRookMoves(board, new int[] {row,col}, team));
				}
				else if (type.equals("Qu")){
					validMoves = combine(validMoves, Piece.calculateQueenMoves(board, new int[] {row,col}, team));
				}
				else if (type.equals("Ki")){
					validMoves = combine(validMoves, Piece.calculateKingMoves(board, new int[] {row,col}, team));		
				}
			}
		}
		return validMoves;
	}

	private boolean[][] combine(boolean[][] b1, boolean[][] b2){
		boolean[][] combined = new boolean[8][8];
		for (int row = 0; row < 8; row++){
			for (int col = 0; col < 8; col++){
				combined[row][col] = b1[row][col] || b2[row][col];
			}
		}
		return combined;
	}

 	public static void main(String[] args) {

	}
}
