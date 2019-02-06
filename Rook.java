public class Rook extends Piece{
    
    public Rook(String team){
        super(team);
    }
    
    public bool[][] calculateMoves(String[][] board, int[] pos){ //overwrites calculateMoves method from the superclass
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
}
