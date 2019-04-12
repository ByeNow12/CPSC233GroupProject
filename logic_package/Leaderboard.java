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
* Class that stores and saves the leaderboard 
* Methods allow for interaction with the users, validate input and return the data entered by the user as a Move object
*/
public class Leaderboard {
	private File rankings;
	private String name;
	private long score;
	private String[][] leaderboard;  //stores scores as a string, easier to not make mistakes and to pass through getLeaderboard
	
	//need to make a method that constructs leaderboard from text file and has an empty slot for the current game
	//also one that saves leaderboard

	public Leaderboard(String name){
		refreshLeaderboard(name);
	}

	public void refreshLeaderboard(){
		refreshLeaderboard(name);
	}

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

	public void setName(String name){
		this.name = name;
	}
	
	public void setScore(long score){
		this.score = score;
	}
	
	public String getName() {
		return name;
	}
	public long getScore(){
		return score;
	}
	
	public String[][] getLeaderboard(){
		return leaderboard;
	}
	
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

	public String getReadableTime(int timeMillis){
		String readableTime = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(timeMillis),
				TimeUnit.MILLISECONDS.toMinutes(timeMillis) % TimeUnit.HOURS.toMinutes(1),
				TimeUnit.MILLISECONDS.toSeconds(timeMillis) % TimeUnit.MINUTES.toSeconds(1));
		return readableTime;
	}

	public String toReadableString(){
		String readableString = "";
		for (int i = 0; i < leaderboard.length; i++){
			String timeToWin = getReadableTime(Integer.parseInt(leaderboard[i][1]));
			readableString += leaderboard[i][0] + " - " + timeToWin+"\n";
		}
		return readableString;
	}

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
	
	
