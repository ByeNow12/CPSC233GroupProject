public class Piece{

    private final String team;
    
    public Piece(String team){
        this.team = team;
    }
    
    /*
    @param board the game board
    @param pos -  the position of the piece in the format [x,y]
    @return returns each move in the format [x,y] within a 2D array
    **/
    
    public int[][] calculateMoves(int[][] board, int[] pos){
        //calculate valid moves
        // this function may even just be removed
    }
    
    public String getTeam(){
        return team;
    }
    
    //more methods
}
