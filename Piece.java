import java.lang.Math;

public class Piece{
    
    //in calling this method one should already have determined the peice is a rook and it will return an array showing true for valid moves and false everyhere else
    // also need to add functionality to check teams, in current state it will take a piece regardless of whether it is on the same team or not
    public static Boolean[][] calculateRookMoves(String[][] board, int[] pos, String team){ 
        int x = pos[0];
        int y = pos[1];
        Boolean[][] moves = new Boolean[8][8];
        for (int i = x-1; i >= 0; i--){
            if (board[i][y].equals("0")){
                moves[i][y] = True;
            }
	    else{
                moves[i][y] = True;
                break;
            }
        }
        for (int i = x+1; i < 8; i++){
            if (board[i][y].equals("0")){
                moves[i][y] = True;
            }
            else{
                moves[i][y] = True;
                break;
            }
        }
        for (int i = y-1; i >= 0; i--){
            if (board[x][i].equals("0")){
                moves[x][i] = True;
            }
            else{
                moves[i][y] = True;
                break;
            }
        }
        for (int i = y+1; i < 8; i++){
            if (board[x][i].equals("0")){
                moves[x][i] = True;
            }
            else{
                moves[i][y] = True;
                break;
            }
        }
        return moves;
    }
    
    public static Boolean[][] calculatePawnMoves(String[][] board, int[] pos, String team){
        int x = pos[0];
        int y = pos[1];
        Boolean[][] moves = new Boolean[8][8];
        if (team.equals("white")){ //assuming white is on the top of the board
            moves = safeCheck(board, {x-1, y+1}, team, moves);
            moves = safeCheck(board, {x+1, y+1}, team, moves);
        }
        else{
            moves = safeCheck(board, {x-1, y-1}, team, moves);
            moves = safeCheck(board, {x+1, y-1}, team, moves);
        }
	return moves;
    }
    
    public static Boolean[][] calculateKnightMoves(String[][] board, int[] pos, String team){
        int x = pos[0];
        int y = pos[1];
        Boolean[][] moves = new Boolean[8][8];
        moves = safeCheck(board, {x+2, y+1}, team, moves);
        moves = safeCheck(board, {x+1, y+2}, team, moves);
        moves = safeCheck(board, {x-1, y+2}, team, moves);
        moves = safeCheck(board, {x-2, y+1}, team, moves);
        moves = safeCheck(board, {x-2, y-1}, team, moves);
        moves = safeCheck(board, {x-1, y-2}, team, moves);
        moves = safeCheck(board, {x+1, y-2}, team, moves);
        moves = safeCheck(board, {x+2, y-1}, team, moves);
	return moves;
    }

    public static Boolean[][] calculateBishopMoves(String[][] board, int[] pos, String team){
	int x = pos[0];
        int y = pos[1];
	Boolean[][] moves = new Boolean[8][8];
	for (int i = x+1, int j = y+1; i < 8 && j < 8; i++, j++){
		//if team == team break
		moves[i][j] = True;
		if (not board[i][j].equals("0")){
			break;
		}
	}
	for (int i = x+1, int j = y-1; i < 8 && j > -1; i++, j--){
		//if team == team break
		moves[i][j] = True;
		if (not board[i][j].equals("0")){
			break;
		}
	}
	for (int i = x-1, int j = y-1; i > -1 && j > -1; i--, j--){
		//if team == team break
		moves[i][j] = True;
		if (not board[i][j].equals("0")){
			break;
		}
	}
	for (int i = x-1, int j = y+1; i > -1 && j < 8; i--, j++){
		//if team == team break
		moves[i][j] = True;
		if (not board[i][j].equals("0")){
			break;
		}
	}
	return moves;
    }
	
    public static Boolean[][] calculateKingMoves(String[][] board, int[] pos, String team){
	int x = pos[0];
        int y = pos[1];
	Boolean[][] moves = new Boolean[8][8];
	moves = safeCheck(board, {x+1, y+1}, team, moves);
	moves = safeCheck(board, {x+1, y}, team, moves);
	moves = safeCheck(board, {x+1, y-1}, team, moves);
	moves = safeCheck(board, {x, y-1}, team, moves);
	moves = safeCheck(board, {x-1, y-1}, team, moves);
	moves = safeCheck(board, {x-1, y}, team, moves);
	moves = safeCheck(board, {x-1, y+1}, team, moves);
        moves = safeCheck(board, {x, y+1}, team, moves);
	return moves;
    }
	
    public static Boolean[][] calculateQueenMoves(String[][] board, int[] pos, String team){
	Boolean[][] moves = new Boolean[8][8];
	rMoves = calculateRookMoves(board, pos, team);
	bMoves = calculateBishopMoves(board, pos, team);
	for (int i = 0; i < 8; i++){
		for (int j = 0; j < 8; j++){
			moves[i][j] = rMoves[i][j] || bMoves[i][j];
		}
	}
	return moves;
    }
    private static Boolean[][] safeCheck(String[][] board, int[] pos, String team, Boolean[][] moves){
	int x = pos[0];
        int y = pos[1];
	if (x > -1 && x < 8 && y > -1 && x < 8){
		//if team == team break
		moves[x][y] = True;
	}
	return moves;
    }
	
}
