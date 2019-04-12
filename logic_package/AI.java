package logic_package;

import java.util.ArrayList;

/**
* 2019-03-20
* Author: Riley
* AI class that evaluates the game condition and makes non-random moves to play against the player
*/
public class AI extends ComputerPlayer {

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

  		String[][] currentBoard = currentConfig.getBoard().getBoardPosition();
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
		//first checks if the king is in check
		if (currentConfig.isCheck(team)){
			//checks if king can move out of check
			int[] kingPos = new int[] {0, 0};
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
		//second, it will try to capture a piece
		for (int i = 0; i < pieces.size(); i++){
			moves[i] = getBestCapture(pieces.get(i), currentBoard, team, opMoves);
			if (best == -1 && moves[i][0] > 0) best = i;
			else if (best > -1 && moves[i][0] > moves[best][0]) best = i;
		}

		if (best > -1){
			return new Move("AI", pieces.get(best)[0], pieces.get(best)[1], moves[best][1], moves[best][2]);
		}
		//third, it will try to place a piece in the center (the most tactically advantageous place to be)
  		for (int i = 0; i < pieces.size(); i++){
			moves[i] = getBestSpaceMove(pieces.get(i), currentBoard, team, opMoves);
			if (best == -1 && moves[i][0] > 0) best = i;
			else if (best > -1 && moves[i][0] > moves[best][0]) best = i;
  		}
		if (best > -1){
			return new Move("AI", pieces.get(best)[0], pieces.get(best)[1], moves[best][1], moves[best][2]);
		}
		//fourth, it will see if there are any other safe moves (moves it can make that will not get the piece taken or will get the piece out of danger)
		for (int i = 0; i < pieces.size(); i++){
			moves[i] = getSpaceMove(pieces.get(i), currentBoard, team, opMoves);
			if (best == -1 && moves[i][0] > 0) best = i;
			else if (best > -1 && moves[i][0] > moves[best][0]) best = i;
  		}
		if (best > -1){
			return new Move("AI", pieces.get(best)[0], pieces.get(best)[1], moves[best][1], moves[best][2]);
		}
		//this is only if it could not find any good moves
		return defaultMove(currentBoard, team, pieces, currentConfig);
 	}

	/**
	*Calculates the most advantageous capture the piece in the specified position can make - assumes errors handled before method call
	*@param pos - the position of the piece for which you want to calculate the best move
	*@param board - the current game board
	*@param team - the team of the AI player
	*@param opMoves - a 2D boolean array of all possible valid moves the opponent can make with all their pieces combined
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
		//gets valid moves for this piece and assigns a value to it from the Piece class
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
		if (opMoves[pos[0]][pos[1]]) value = pieceValue; //increases the move value if the piece being considered is in danger, making this move more likely to be chosen

		for (int row = 0; row < 8; row++){
			for (int col = 0; col < 8; col++){
				if (!validMoves[row][col]) continue;	//if it's not a valid move, skip it

				char pTeam = board[row][col].charAt(0);
				if (pTeam == '0') continue;		//skips if empty square (not yet being considered) or if position is already occupied by friendly piece

				String pType = board[row][col].substring(2);
				int pValue = value;  			//this is where we add the value for the move if the piece is in danger in its current position

				if (pType.equals("Pa")) pValue = Piece.PAWN_VALUE;
				else if (pType.equals("Bi")) pValue = Piece.BISHOP_VALUE;
				else if (pType.equals("Kn")) pValue = Piece.KNIGHT_VALUE;
				else if (pType.equals("Ro")) pValue = Piece.ROOK_VALUE;
				else if (pType.equals("Qu")) pValue = Piece.QUEEN_VALUE;
				else if (pType.equals("Ki")) pValue = Piece.KING_VALUE;
				
				board[row][col] = "0";
				if (getAllOpponentValidMoves(board, team)[row][col]) pValue -= pieceValue; // checks if the piece will be in danger if it decides to capture this opponent piece and adjusts the move value
				
				if (pValue > best){
					best = pValue;
					move[0] = row;
					move[1] = col;
				}
				board[row][col] = pTeam + "_" + pType;		//puts the board back to original state for future calculations in the method
			}
		}
		return new int[] {best, move[0], move[1]};
	}

	/**
	*Calculates the most advantageous move into an empty square the piece in the specified position can make
	*@param pos - the position of the piece for which you want to calculate the best move
	*@param board - the current game board
	*@param team - the team of the AI player
	*@param opMoves - a 2D boolean array of all possible valid moves the opponent can make with all their pieces combined
	*@return an array with value of the returned move and the end position of the move, in the form {value, row, col},
	*returns position as -1,-1 if no good valid moves
  	*/

	private int[] getBestSpaceMove(int[] pos, String[][] board, char team, boolean[][] opMoves){

		int pValue = 0;		//will assign piece values based on which is most advantageous to have in the middle of the board
		boolean[][] validMoves;
		int[] move;
		String type = board[pos[0]][pos[1]].substring(2);
		//gets valid moves for this piece and assigns a value based on conditions explained above
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
			return new int[] {0,-1,-1};	//default just in case something went wrong calling the method
		}

		move = new int[] {-1,-1};
		if (opMoves[pos[0]][pos[1]]) pValue += 10; //this makes this piece the priority to move if it is about to be captured

		for (int row = 2; row < 6; row++){
			for (int col = 2; col < 6; col++){
				//will aim for the center of the board, and get the closest move it can to the very center
				if (!validMoves[row][col]) continue;

				if (opMoves[row][col]) continue;//skips if this move will put the piece in danger
				//
				if ((row < 5 && row > 2)&&(col < 5 && col > 2)){
					move = new int[] {row,col};
					continue;
				}
				//skips if already has a good move to the very center
				if ((move[0] < 5 && move[1] > 2)&&(move[0] < 5 && move[1] > 2)){
					continue;
				}
				//gradually increasing area
				if ((row < 5 && row > 2)||(col < 5 && col > 2)){
					move = new int[] {row,col};
					continue;
				}
				//skips if it already has a better move
				if ((move[0] < 5 && move[0] > 2)||(move[1] < 5 && move[1] > 2)){
					continue;
				}
				//defaults to a move in the 4x4 middle area if none of the above conditions met
				move = new int[] {row,col};
			}
		}

		if (move[0] == -1) pValue = -1;//if no good valid moves it will set the move value to -1

		return new int[] {pValue, move[0], move[1]};
	}

	/**
	*Tries to find a valid move that will either not move a piece into danger or will move a piece out of danger
	*@param pos - the position of the piece for which you want to calculate the move
	*@param board - the current game board
	*@param team - the team of the AI player
	*@param opMoves - a 2D boolean array of all possible valid moves the opponent can make with all their pieces combined
	*@return an array with value of the returned move and the end position of the move, in the form {value, row, col},
	*returns position as -1,-1 if no good valid moves
  	*/

	private int[] getSpaceMove(int[] pos, String[][] board, char team, boolean[][] opMoves){	//creating another method because cretain peices are less useless on the sides than others, so different values and different move selection logic

		int pValue = 0;		
		boolean[][] validMoves;
		int[] move;
		int inCheckBonus = 3;		//the value added to the move value if the considered piece is in check
		String type = board[pos[0]][pos[1]].substring(2);
		//gets valid moves for this piece and assigns a value based on lesser valued pieces (since the outsides of the board are less advantageous so we don't want to put our best pieces there)
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
			inCheckBonus = 5;//queen will have higher priority if it is in danger
		}
		else if (type.equals("Ki")){
			validMoves = Piece.calculateKingMoves(board, pos, team);
			pValue = 1;		
		}
		else{
			return new int[] {0,-1,-1};//in case something went wrong in method call
		}

		move = new int[] {-1,-1};
		if (opMoves[pos[0]][pos[1]]) pValue += inCheckBonus;//increases the value of the move if the piece is in danger

		for (int row = 2; row < 6; row++){
			for (int col = 2; col < 6; col++){
			
				if (!validMoves[row][col]) continue;//skips if this is not a valid move

				if (opMoves[row][col]) continue;//skips if this move will put the piece in danger
				
				move[0] = row;
				move[1] = col;
			}
		}

		if (move[0] == -1) pValue = -1;//if no good valid moves it will set the move value to -1

		return new int[] {pValue, move[0], move[1]};
	}

	/**
	 * This method should only be called in the worst case scenario where there are no good moves and it will try to sacrifice a lesser piece first
	 * @param board - the current game board
	 * @param team - the team of the AI player
	 * @param pieces - an ArrayList containing the positions of all the computer player's pieces
	 * @param config - the current GameConfiguration object
	 * @return returns a move that will hopefully be the least detrimental to the AI's chances for winning
	 */
	 
	private Move defaultMove(String[][] board, char team, ArrayList<int[]> pieces, GameConfiguration config){

		int[] pos = new int[] {-1,-1};
		boolean[][] validMoves;
		//first will try to sacrifice a pawn
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
		//then will try to sacrifice a bishop
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
		//then will try to sacrifice a knight
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
		//failing all that it will make a random move, akin to a human player getting frustrated and just making any move
		return super.getRandomMove(config);
	}

	/**
	 * Determines all possible moves the opponent can make and returns in the form of a 2D boolean array
	 * @param board - the current game board
	 * @param team - the AI player's team
	 * @return returns all possible moves the opponent can make
	 */

	private boolean[][] getAllOpponentValidMoves(String[][] board, char team){
		boolean[][] validMoves = new boolean[8][8];
		//scans the board for opponent players, then calculates their valid moves individually and combines the arrays
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

	/**
	 * Combines validMove arrays, by returning an array where any move that is true in one or both of the arrays will be true in returned
	 * @param b1 - the first array to combine
	 * @param b2 - the second array to combine
	 * @return returns the combined array
	 */

	private boolean[][] combine(boolean[][] b1, boolean[][] b2){
		boolean[][] combined = new boolean[8][8];
		for (int row = 0; row < 8; row++){
			for (int col = 0; col < 8; col++){
				combined[row][col] = b1[row][col] || b2[row][col];//by using the || operator it will be true if either or both are true
			}
		}
		return combined;
	}

 	public static void main(String[] args) {

	}
}
