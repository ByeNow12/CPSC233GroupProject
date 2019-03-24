package logic_package;

/**
* 2019-03-20
* Author: Riley
* AI class that evaluates the game condition and makes non-random moves to play against the player
*/
public class AI extends ComputerPlayer {

	String[][] currentBoard;
	String[][][] hBoards;

	public AI(String aToken){
		super(aToken);
	}
 	/** 
	* Getter method for getting the move for the computer player
  	* @param
  	* @return 
 	*/
 	@Override
  	public Move getMove(GameConfiguration currentConfig) {
		return null;
 	}
  
 	public static void main(String[] args) {

	}
}
