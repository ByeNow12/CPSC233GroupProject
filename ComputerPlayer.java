import java.util.Random;

/**
* 2019-03-06
* Author: Shavonne
* Class that allows the computer player to make a move and contains data related to the AI
* Methods allow for computer to make a random move as long as it's valid and returns that move 
* The AI method to be implemented will allow for the computer to do computation to determine a non random move
*/
public class ComputerPlayer {
	private String token;
	private Move lastMove; //computer considers the last move made by human player to make its move, used when AI developed
	//lastMove will be left alone for now
        private GameConfiguration config; //TODO This needs to be changed so that our gameconfig is passed into a constructor

  	//Constructors
	public ComputerPlayer(String aToken) { //default
    		this.token = aToken;
 	 }
  
	public ComputerPlayer(ComputerPlayer toCopy) { //copy constructor
		this.token = toCopy.token;
    		this.lastMove = toCopy.lastMove;
  	}
  	
 	/** Getter method for getting the move for the computer player
  	* @param currentConfig, the variable that checks the state of the game
  	* @return a random valid move until we implement the AI
 	*/
  	public Move getMove(GameConfiguration currentConfig) {
		return getRandomMove(currentConfig); //obtain move based on values from the setter
 	}
  
  	/** Setter method to make a random valid move for the computer player
 	* @param fromRowNum, the row number of the piece the computer wants to move from
	* @param fromColNum, the column number of the piece the computer wants to move from
	* @param toRowNum, the row number the computer wants to move to 
	* @param toColNum, the column number the computer wants to move to 
  	*/
  	public void setMove(int fromRowNum, int fromColNum, int toRowNum, int toColNum) {
		//while the row/column numbers in range of 0-8, Move class called to make a move
		while ((toRowNum >= 0 && toColNum >= 0) && (toRowNum < 8 && toColNum < 8)) {
			Move compMove = new Move(token, fromRowNum, fromColNum, toRowNum, toColNum); //call Move class to make a move based on the row/column number 
			if (config.isValidMove(compMove) == true) { //check if move is valid
				compMove.getTo(); //if valid, move piece to desired row and column number 
			}
		}
    	}

	/** Method that gets a random move for the computer player
	* Checks if a move is valid, then we return that move
	* @param config, a parameter from the state of the game
	* @return a random valid move from the Move class
	*/
	public Move getRandomMove(GameConfiguration config){ //literally just brute forces until a valid move comes out
		Random r = new Random();
		int[] nums = new int[4];
		Move move;
	
		do {   //do-while loops are convenient
			for (int i = 0; i < 4; i++){
				nums[i] = r.nextInt(8); //random int (0,8]
			}
			move = new Move("Black", nums[0], nums[1], nums[2], nums[3]);
		} while (!config.isValidMove(move) || config.getBoard().getBoardPosition()[nums[0]][nums[1]].charAt(0) == 'w'); //also makes sure it's not about too move a white piece

		return move;
	}
		
	
 	public static void main(String[] args) {
		ComputerPlayer p2 = new ComputerPlayer("black");
		GameConfiguration config = new GameConfiguration();
	}
}

