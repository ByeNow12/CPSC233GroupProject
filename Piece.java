
public class Piece{

    //in calling this method one should already have determined the peice is a rook and it will return an array showing true for valid moves and false everyhere else
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
    
    public static boolean[][] calculatePawnMoves(String[][] board, int[] pos, char team){
        int r = pos[0];
        int c = pos[1];
        boolean[][] moves = new boolean[8][8];
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
	return moves;
    }
    
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
    private static boolean[][] safeCheck(String[][] board, int[] pos, char team, boolean[][] moves){
	int x = pos[0];
        int y = pos[1];
	if (x > -1 && x < 8 && y > -1 && y < 8 && !(team == board[x][y].charAt(0))){
		moves[x][y] = true;
	}
	return moves;
    }
	
}