import java.lang.Math;

public class Piece{
    
    //in calling this method one should already have determined the peice is a rook and it will return an array showing true for valid moves and false everyhere else
    // also need to add functionality to check teams, in current state it will take a piece regardless of whether it is on the same team or not
    public static Boolean[][] calculateRookMoves(String[][] board, int[] pos){ 
        int x = pos[0];
        int y = pos[1];
        Boolean[][] moves = new Boolean[8][8];
        for (int i = x-1; i >= 0; i--){
            if (board[i][y].equals("blank")){
                moves[i][y] = true;
            }
	    else{
                moves[i][y] = true;
                break;
            }
        }
        for (int i = x+1; i < 8; i++){
            if (board[i][y].equals("blank")){
                moves[i][y] = true;
            }
            else{
                moves[i][y] = true;
                break;
            }
        }
        for (int i = y-1; i >= 0; i--){
            if (board[x][i].equals("blank")){
                moves[x][i] = true;
            }
            else{
                moves[i][y] = true;
                break;
            }
        }
        for (int i = y+1; i < 8; i++){
            if (board[x][i].equals("blank")){
                moves[x][i] = true;
            }
            else{
                moves[i][y] = true;
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
            moves[Math.max(x-1,0)][y+1] = true;
            moves[Math.min(x+1,7)][y+1] = true;
        }
        else{
            moves[Math.max(x-1,0)][y-1] = true;
            moves[Math.min(x+1,7)][y-1] = true;
        }
	return moves;
    }
    
    public static Boolean[][] calculateKnightMoves(String[][] board, int[] pos, String team){
        int x = pos[0];
        int y = pos[1];
        Boolean[][] moves = new Boolean[8][8];
        moves[x+2][y+1] = true;
        moves[x+1][y+2] = true;
        moves[x-1][y+2] = true;
        moves[x-2][y+1] = true;
        moves[x-2][y-1] = true;
        moves[x-1][y-2] = true;
        moves[x+1][y-2] = true;
        moves[x+2][y-1] = true;
	return moves;
    }
    //more methods
}
