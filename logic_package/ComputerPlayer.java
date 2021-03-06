package logic_package;

import java.util.Random;

/**
* 2019-03-06
* Author: Shavonne, Riley
* Class that allows the computer player to make a move
* Methods allow for computer to make a random move as long as it's valid and returns that move 
* The AI class extends from this class and will allow for the computer to do computation to determine a non random move 
*/
public class ComputerPlayer {
	private char team;

  	/**
	* Default constructor
	* @param team, a character to represent the team colour playing
	*/
	public ComputerPlayer(char team) { 
		this.team = team;
	}
  	
 	/** 
	* Getter method for getting the move for the computer player
  	* @param currentConfig, the variable that checks the state of the game
  	* @return a random valid move until we implement the AI
 	*/
  	public Move getMove(GameConfiguration currentConfig) {
		return getRandomMove(currentConfig); //obtain move based on values from the setter
 	}
  	
	/**
	* Getter for getting the team 
	* @return team, the team colour playing
	*/
	public char getTeam() {
		return team;
	}

	/** 
	* Gets a random move for the computer player and checks if a move is valid, then we return that move
	* @param config, a parameter from the state of the game
	* @return a random valid move from the Move class
	*/
	public Move getRandomMove(GameConfiguration config) { //literally just brute forces until a valid move comes out
		Random r = new Random();
		int[] nums = new int[4];
		Move move;
	
		do {   //do-while loops are convenient
			for (int i = 0; i < 4; i++) {
				nums[i] = r.nextInt(8); //random int (0,8]
			}
			move = new Move("Black", nums[0], nums[1], nums[2], nums[3]);
		} while (!config.isValidMove(move) || config.getBoard().getBoardPosition()[nums[0]][nums[1]].charAt(0) == 'w'); //also makes sure it's not about too move a white piece

		return move;
	}
	
	//Main method
 	public static void main(String[] args) {
		ComputerPlayer p2 = new ComputerPlayer('b');
		GameConfiguration config = new GameConfiguration();
	}
}
