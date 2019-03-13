import java.util.Scanner;

/**
* 2019-03-06
* Author: Carmen
* Class that allows human players to make a move and contains data related to the player 
* Methods allow for interaction with the users, validate input and return the data entered by the user as a Move object
*/
public class HumanPlayer {
	private String name;
	private int score;
	private String[][] leaderboard;  //stores scores as a string, easier to not make mistakes and to pass through getLeaderboard
	
	//need to make a method that constructs leaderboard from text file and has an empty slot for the current game
	//also one that saves leaderboard
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setScore(int score){
		this.score = score;
	}
	
	public String getName(){
		return name;
	
	public int getScore(){
		return score;
	}
	
	public String[][] getLeaderboard(){
		return leaderboard;
	}

	public static void main(String[] args) {

	}
}
	
	
