public class Piece{
    
    //in calling this method oens hould already have determined the peice is a rook and it will return an array showing true for valid moves and false everyhere else
    
    public static bool[][] calculateRookMoves(String[][] board, int[] pos){ 
        x = pos[0];
        y = pos[1];
        bool[][] moves = bool[8][8];
        for (int i = x-1; i >= 0; i--;){
            if (board[i][y].equals("blank")){
                moves[i][y] = true;
            }
            else{
                moves[i][y] = true;
                break;
            }
        }
        for (int i = x+1; i < 8; i++;){
            if (board[i][y].equals("blank")){
                moves[i][y] = true;
            }
            else{
                moves[i][y] = true;
                break;
            }
        }
        for (int i = y-1; i >= 0; i--;){
            if (board[x][i].equals("blank")){
                moves[x][i] = true;
            }
            else{
                moves[i][y] = true;
                break;
            }
        }
        for (int i = y+1; i < 8; i++;){
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
    
    //more methods
}
