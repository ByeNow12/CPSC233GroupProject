public class Piece {
    /** Method for calculating possible moves for rook piece based on what moves the pieces are allowed to make according to standard chess rules
    * Creates a 8 by 8 board with valid moves marked as true and invalid moves marked as false
    * @param String[][] board, an arraylist for the board
    * @param int[] pos, array that contains index for position of the rook piece
    * @param char team, a character for the team 
    * @return an array that shows true for valid moves of rook and false everywhere else
    */ 
    public static boolean[][] calculateRookMoves(String[][] board, int[] pos, char team){
        int x = pos[0];
        int y = pos[1];
        boolean[][] moves = new boolean[8][8];
        for (int i = x-1; i >= 0; i--){
            if (team == board[i][y].charAt(0)) break;
            if (board[i][y].equals("0")){
                moves[i][y] = true;
            }
	    else{
                moves[i][y] = true;
                break;
            }
        }
        for (int i = x+1; i < 8; i++){
            if (team == board[i][y].charAt(0)) break;
            if (board[i][y].equals("0")){
                moves[i][y] = true;
            }
            else{
                moves[i][y] = true;
                break;
            }
        }
        for (int i = y-1; i >= 0; i--){
            if (team == board[x][i].charAt(0)) break;
            if (board[x][i].equals("0")){
                moves[x][i] = true;
            }
            else{
                moves[i][y] = true;
                break;
            }
        }
        for (int i = y+1; i < 8; i++){
            if (team == board[x][i].charAt(0)) break;
            if (board[x][i].equals("0")){
                moves[x][i] = true;
            }
            else{
                moves[i][y] = true;
                break;
            }
        }
        return moves;
    }
    
    /** Method for calculating possible moves for pawn piece based on what moves the pieces are allowed to make according to standard chess rules
    * Creates a 8 by 8 board with valid moves marked as true and invalid moves marked as false
	* Updated so that the first move of a pawn is able to move forward one or two spaces
	* @version 2.0
    * @param String[][] board, an arraylist for the board
    * @param int[] pos, array that contains index for position of the pawn piece
    * @param char team, a character for the team 
    * @return an array that shows true for valid moves of pawn and false everywhere else
    */
    public static boolean[][] calculatePawnMoves(String[][] board, int[] pos, char team){
		int r = pos[0];
		int c = pos[1];
		boolean[][] moves = new boolean[8][8];
		if (r == 1 || r == 6){ // if the pawn is in row 1 or 6 (will be the first move the pawn makes), can move one or two spaces forward
			if (team == 'w' && r < 7){ //assuming white is on the top of the board
				if (c < 7){
					for (int i=1; i<3; i++){
						if ('b' == board[r+i][c+1].charAt(0)) {
							moves[r+i][c+1] = true;
						}
					}
                }
                if (c > 1){
                    for (int i=1; i<3; i++){
                        if ('b' == board[r+i][c-1].charAt(0)) {
							moves[r+i][c-1] = true;
						}
                    }
                }
                for (int i=1; i<3; i++){
                    if ('0' == board[r+i][c].charAt(0)) {
						moves[r+i][c] = true;
					}
                }
            }
            else if (r > 1){
                if (c < 7){
                    for (int i=1; i<3; i++){
                        if ('w' == board[r-i][c+1].charAt(0)) {
							moves[r-i][c+1] = true;
						}
                    }
            }
                if (c > 1){
                    for (int i=1; i<3; i++){
                        if ('w' == board[r-i][c-1].charAt(0)) {
							moves[r-i][c-1] = true;
						}
                    }
            }
            for (int i=1; i<3; i++){
                if ('0' == board[r-i][c].charAt(0)) {
					moves[r-i][c] = true;
				}
            }
            }
        }

        else {
            if (team == 'w' && r < 7){ //assuming white is on the top of the board
                if (c < 7){
                    if ('b' == board[r+1][c+1].charAt(0)) moves[r+1][c+1] = true;
        	    }
        	    if (c > 1){
                    if ('b' == board[r+1][c-1].charAt(0)) moves[r+1][c-1] = true;
        	    }
        	    if ('0' == board[r+1][c].charAt(0)) moves[r+1][c] = true;
            }
            else if (r > 1){
                if (c < 7){
                    if ('w' == board[r-1][c+1].charAt(0)) moves[r-1][c+1] = true;
    	    }
                if (c > 1){
                    if ('w' == board[r-1][c-1].charAt(0)) moves[r-1][c-1] = true;
    	    }
    	    if ('0' == board[r-1][c].charAt(0)) moves[r-1][c] = true;
            }
        }
  
		return moves;
    }

    /** Method for calculating possible moves for knight piece based on what moves the pieces are allowed to make according to standard chess rules
    * Creates a 8 by 8 board with valid moves marked as true and invalid moves marked as false
    * @param String[][] board, an arraylist for the board
    * @param int[] pos, array that contains index for position of the knight piece
    * @param char team, a character for the team 
    * @return an array that shows true for valid moves of knight and false everywhere else
    */
    public static boolean[][] calculateKnightMoves(String[][] board, int[] pos, char team){
        int x = pos[0];
        int y = pos[1];
        boolean[][] moves = new boolean[8][8];
        moves = safeCheck(board, new int[]{x+2, y+1}, team, moves);
        moves = safeCheck(board, new int[]{x+1, y+2}, team, moves);
        moves = safeCheck(board, new int[]{x-1, y+2}, team, moves);
        moves = safeCheck(board, new int[]{x-2, y+1}, team, moves);
        moves = safeCheck(board, new int[]{x-2, y-1}, team, moves);
        moves = safeCheck(board, new int[]{x-1, y-2}, team, moves);
        moves = safeCheck(board, new int[]{x+1, y-2}, team, moves);
        moves = safeCheck(board, new int[]{x+2, y-1}, team, moves);
	return moves;
    }

    /** Method for calculating possible moves for bishop piece based on what moves the pieces are allowed to make according to standard chess rules
    * Creates a 8 by 8 board with valid moves marked as true and invalid moves marked as false
    * @param String[][] board, an arraylist for the board
    * @param int[] pos, array that contains index for position of the bishop piece
    * @param char team, a character for the team 
    * @return an array that shows true for valid moves of bishop and false everywhere else
    */
    public static boolean[][] calculateBishopMoves(String[][] board, int[] pos, char team){
	int x = pos[0];
        int y = pos[1];
	boolean[][] moves = new boolean[8][8];
	for (int i = x+1, j = y+1; i < 8 && j < 8; i++, j++){
		if (team == board[i][j].charAt(0)) break;
		moves[i][j] = true;
		if (!(board[i][j].equals("0"))){
			break;
		}
	}
	for (int i = x+1, j = y-1; i < 8 && j > -1; i++, j--){
		if (team == board[i][j].charAt(0)) break;
		moves[i][j] = true;
		if (!(board[i][j].equals("0"))){
			break;
		}
	}
	for (int i = x-1, j = y-1; i > -1 && j > -1; i--, j--){
		if (team == board[i][j].charAt(0)) break;
		moves[i][j] = true;
		if (!(board[i][j].equals("0"))){
			break;
		}
	}
	for (int i = x-1, j = y+1; i > -1 && j < 8; i--, j++){
		if (team == board[i][j].charAt(0)) break;
		moves[i][j] = true;
		if (!(board[i][j].equals("0"))){
			break;
		}
	}
	return moves;
    }

    /** Method for calculating possible moves for king piece based on what moves the pieces are allowed to make according to standard chess rules
    * Creates a 8 by 8 board with valid moves marked as true and invalid moves marked as false
    * @param String[][] board, an arraylist for the board
    * @param int[] pos, array that contains index for position of the king piece
    * @param char team, a character for the team 
    * @return an array that shows true for valid moves of king and false everywhere else
    */
    public static boolean[][] calculateKingMoves(String[][] board, int[] pos, char team){
	int x = pos[0];
        int y = pos[1];
	boolean[][] moves = new boolean[8][8];
	moves = safeCheck(board, new int[]{x+1, y+1}, team, moves);
	moves = safeCheck(board, new int[]{x+1, y}, team, moves);
	moves = safeCheck(board, new int[]{x+1, y-1}, team, moves);
	moves = safeCheck(board, new int[]{x, y-1}, team, moves);
	moves = safeCheck(board, new int[]{x-1, y-1}, team, moves);
	moves = safeCheck(board, new int[]{x-1, y}, team, moves);
	moves = safeCheck(board, new int[]{x-1, y+1}, team, moves);
        moves = safeCheck(board, new int[]{x, y+1}, team, moves);
	return moves;
    }

    /** method for calculating possible moves for queen piece based on what moves the pieces are allowed to make according to standard chess rules
    * Creates a 8 by 8 board with valid moves marked as true and invalid moves marked as false
    * @param String[][] board, an arraylist for the board
    * @param int[] pos, array that contains index for position of the queen piece
    * @param char team, a character for the team 
    * @return an array that shows true for valid moves of queen and false everywhere else
    */
    public static boolean[][] calculateQueenMoves(String[][] board, int[] pos, char team){
	boolean[][] moves = new boolean[8][8];
	boolean[][] rMoves = calculateRookMoves(board, pos, team);
	boolean[][] bMoves = calculateBishopMoves(board, pos, team);
	for (int i = 0; i < 8; i++){
		for (int j = 0; j < 8; j++){
			moves[i][j] = rMoves[i][j] || bMoves[i][j];
		}
	}
	return moves;
    }

    /** Method for ensuring that the pieces move within the 8 by 8 board  
    * @param String[][] board, an arraylist for the board
    * @param int[] pos, array that contains index for position of the pieces
    * @param char team, a character for the team 
    * @param boolean[][] moves, an array that shows if the moves are true
    * @return moves, an array that shows true if the pieces move within the board range
    */
    private static boolean[][] safeCheck(String[][] board, int[] pos, char team, boolean[][] moves){
	int x = pos[0];
        int y = pos[1];
	if (x > -1 && x < 8 && y > -1 && y < 8 && !(team == board[x][y].charAt(0))){
		moves[x][y] = true;
	}
	return moves;
    }

}
