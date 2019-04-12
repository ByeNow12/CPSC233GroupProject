package logic_package;

import java.util.Arrays;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;
import java.util.concurrent.TimeUnit;

/**
* 2019-03-06
* Author: Riley, Carmen
* Class that stores and saves the leaderboard, where players can enter their score if they beat the highest score before them
* Methods allow for interaction with the users, validate input and return the data entered by the user as a Move object
*/
public class Leaderboard {
	private File rankings;
	private String name;
	private long score;
	private String[][] leaderboard;  //stores scores as a string, easier to not make mistakes and to pass through getLeaderboard
	
    /**
     * Constructor for leaderboard that takes name as a parameter and refreshes leaderboard
     * @param name, String that takes the name of the user who beats the high score
     */
	public Leaderboard(String name){
		refreshLeaderboard(name);
	}

    /**
     * Refreshes leaerboard, calls the same argument that takes the name in order to refresh after a game is played
     */
	public void refreshLeaderboard(){
		refreshLeaderboard(name);
	}

    /**
     * Refreshes leaderboard and looks for rankings.txt. If ranking does not exist, creates new rankings file
     * @param name, String that takes the name of the player who beat the highest score
     */
	public void refreshLeaderboard(String name) {
		try {
			rankings = new File("rankings.txt");
			rankings.createNewFile();

			this.name = name;
			FileReader fileReader = new FileReader(rankings);
			BufferedReader br = new BufferedReader(fileReader);
			leaderboard = new String[0][0];
			String line = br.readLine();
			while (line != null) {
				String[] ranking = line.split(" ");
				leaderboard = Arrays.copyOf(leaderboard, leaderboard.length + 1);
				leaderboard[leaderboard.length - 1] = ranking;
				line = br.readLine();
			}
			br.close();
		}
		catch (Exception e) {
			System.out.println("Problem with leaderboard");
		}
	}

    /**
     * setter for player's name
     * @param name, String that takes name of player
     */
	public void setName(String name){
		this.name = name;
	}

    /**
     * sets score for player who entered their name
     * @param score, long of the score in miliseconds
     */
	public void setScore(long score){
		this.score = score;
	}

    /**
     * saves the leaderboard as rankings.txt file. Formats leader board to "<name> <score>" and separated by lines
     * @throws IOException
     */
	public void save() throws IOException{
		//leaderboard = Arrays.copyOf(leaderboard, leaderboard.length + 1);
		//leaderboard[leaderboard.length - 1] = new String[] {name, "" + score};
		FileWriter fw = new FileWriter(rankings);
		BufferedWriter bw = new BufferedWriter(fw);

		for (String[] ranking: leaderboard){
			if (score <= Long.parseLong(ranking[1])){
				String s = name + " " + score;
				bw.write(s, 0, s.length());
				score = Long.MAX_VALUE;
				bw.newLine();
			}
			String s = ranking[0] + " " + ranking[1];
			bw.write(s, 0, s.length());
			bw.newLine();
		}
		
		if (leaderboard.length == 0){
			String s = name + " " + score;
			bw.write(s, 0, s.length());
			score = Long.MAX_VALUE;
			bw.newLine();
		}
		bw.close();
	}

    /**
     * converts the time from the String array into a readable format of hours:minutes:seconds
     * @param timeMillis, integer of the time in miliseconds
     * @return readableTime, the time as a string to display on leaderboard menu
     */
	public String getReadableTime(int timeMillis){
		String readableTime = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(timeMillis),
				TimeUnit.MILLISECONDS.toMinutes(timeMillis) % TimeUnit.HOURS.toMinutes(1),
				TimeUnit.MILLISECONDS.toSeconds(timeMillis) % TimeUnit.MINUTES.toSeconds(1));
		return readableTime;
	}

    /**
     * Formats the string on the leaderboard menu as "<Name> - <time taken to complete game>"
     * @return readableString in String format to display
     */
	public String toReadableString(){
		String readableString = "";
		for (int i = 0; i < leaderboard.length; i++){
			String timeToWin = getReadableTime(Integer.parseInt(leaderboard[i][1]));
			readableString += leaderboard[i][0] + " - " + timeToWin+"\n";
		}
		return readableString;
	}

    /**
     * for testing purposes
     * @param args
     */
	public static void main(String args[]) {
		try{
			Leaderboard l = new Leaderboard("Joe");
			l.setScore(System.currentTimeMillis());
			l.save();
		}
		catch(Exception e){
			System.out.println("it went wrong");
			e.printStackTrace();
		}
	}
}
	
	
