
package logic_package;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
* 2019-03-20
* Authors: Riley, Dany, Tom, Carmen
* GameConfiguration class checks the current state of the game
* Methods check for piece positions, moves conditions and updates, and game conditions including when a player wins
*/
public class GameConfiguration {

	private Board board;
	private Leaderboard leaderboard;
	private boolean whiteTurn;
	private long totalWhiteTime = 0;
	private long totalBlackTime = 0;
	private long turnStartTime = 0;
	private long winningTime = 0;
	private TextField enterPlayerName = new TextField();
	private Label namesAndScores = new Label("names and scores here");
	
	//Constructor
	public GameConfiguration() {
		board = new Board();
		board.defaultPositions();
		leaderboard = new Leaderboard("newgame");
		whiteTurn = true;
	}

	/**
	* Getter method for board instance variable
	* @return board, the board
	*/
	public Board getBoard() {
		return board;
	}

	/**
	* Updates board positions in accordance with the passed move object
	* @param move: Move, the change in the board position to be done
	*/
	public void update(Move move){
		int[] lastMove = move.getFrom();
		int[] currentMove = move.getTo();
		String token = board.getBoardPosition()[lastMove[0]][lastMove[1]];
		board.setBoardPositions(lastMove[0], lastMove[1], "0");
		board.setBoardPositions(currentMove[0], currentMove[1], token);
		promotion();
		if (whiteTurn){
			totalWhiteTime += System.currentTimeMillis() - turnStartTime;
		} else{
			totalBlackTime += System.currentTimeMillis() - turnStartTime;
		}
		takeTime();
		whiteTurn = !whiteTurn;  	//this way it will cycle between white and black team's turns
	}
	
	public void promotion(){
		for (int i = 0; i <=7; i++){
		  if (board.getBoardPositionPieceInfo(0,i).equals("b_Pa")){
			  board.setBoardPositions(0,i,"b_Qu");
		}
		  if (board.getBoardPositionPieceInfo(7,i).equals("w_Pa")){
			  board.setBoardPositions(7,i,"w_Qu");
		}
		}
		
	}
	
	/**
	* Reverse the passed Move object
	* @param move: Move, the change in the board to be reversed.
	*/
	public void reverseMove(Move move) {
		int[] lastMove = move.getFrom();
		int[] currentMove = move.getTo();
		String token = board.getBoardPosition()[lastMove[0]][lastMove[1]];
		board.setBoardPositions(currentMove[0], currentMove[1], "0");
		board.setBoardPositions(lastMove[0], lastMove[1], token);
	}

	/**
	* Checks if the passed move object is a valid move and returns true if it is
	* @param move, the change in the board position to be done
	* @return boolean, whether or not the move is valid
	*/
	public boolean isValidMove(Move move){
		int[] lastMove = move.getFrom();
		int[] currentMove = move.getTo();
		String token = board.getBoardPosition()[lastMove[0]][lastMove[1]];
		char pieceColor = token.charAt(0);

		if (pieceColor == '0'){ return false;} //just for the random ai move method

		//determining if it is this player's turn
		if ((whiteTurn && pieceColor == 'b') || (!whiteTurn && pieceColor == 'w')) return false;

		String pieceType = token.substring(2);
		String[][] boardPositions = board.getBoardPosition();
		/*if (Character.toUpperCase(pieceColor) != move.getTeam().charAt(0)) {
						System.out.println("This is the problem");
			return false;
		}
		else */if (pieceType.equals("Ro")) {
			return Piece.calculateRookMoves(boardPositions, lastMove, pieceColor)[currentMove[0]][currentMove[1]];
		}
		else if (pieceType.equals("Kn")) {
			return Piece.calculateKnightMoves(boardPositions, lastMove, pieceColor)[currentMove[0]][currentMove[1]];
		}
		else if (pieceType.equals("Bi")) {
			return Piece.calculateBishopMoves(boardPositions, lastMove, pieceColor)[currentMove[0]][currentMove[1]];
		}
		else if (pieceType.equals("Qu")) {
			return Piece.calculateQueenMoves(boardPositions, lastMove, pieceColor)[currentMove[0]][currentMove[1]];
		}
		else if (pieceType.equals("Ki")) {
			return Piece.calculateKingMoves(boardPositions, lastMove, pieceColor)[currentMove[0]][currentMove[1]];
		}
		else if (pieceType.equals("Pa")) {
			return Piece.calculatePawnMoves(boardPositions, lastMove, pieceColor)[currentMove[0]][currentMove[1]];
		}
                return false; //just in case some fluke accident occurs
	}

	/**
	* Checks if the king in belonging to token is captured. Checks if the game has ended.
	* @param token, the team to be checked for winning
	* @return boolean, true or false whether the team has won 
	*/
	public boolean hasWon(char token){
		boolean wKingPresent = false;
		boolean bKingPresent = false;
		for (int i = 0; i < 8; i++) {
            for (int x = 0; x < 8; x++) {
				if (board.getBoardPosition()[x][i].equals("w_Ki")) {
					wKingPresent = true;
				}
				if (board.getBoardPosition()[x][i].equals("b_Ki")) {
					bKingPresent = true;
				}
			}
		}
                if (token == 'w') return !bKingPresent;
		return !wKingPresent;
	}

	/**
	* Returns all the valid moves of the team belonging to token
	* @param token, the team colour to find all valid moves for
	* @return ArrayList of Move objects
	*/
	public ArrayList<Move> getAllValidMoves(char token){
		ArrayList<Move> moveArrayList = new ArrayList<Move>();
		boolean[][] unconvertedMoves = new boolean[8][8];
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (board.getBoardPosition()[x][y].charAt(0) == token) {
					int[] pos = new int[2];
					pos[0] = x;
					pos[1] = y;
					if (board.getBoardPosition()[x][y].substring(2).equals("Ro")) {
						unconvertedMoves = Piece.calculateRookMoves(board.getBoardPosition(), pos, token);
					}
					else if (board.getBoardPosition()[x][y].substring(2).equals("Pa")) {
						unconvertedMoves = Piece.calculatePawnMoves(board.getBoardPosition(), pos, token);
					}
					else if (board.getBoardPosition()[x][y].substring(2).equals("Kn")) {
						unconvertedMoves = Piece.calculateKnightMoves(board.getBoardPosition(), pos, token);
					}
					else if (board.getBoardPosition()[x][y].substring(2).equals("Bi")) {
						unconvertedMoves = Piece.calculateBishopMoves(board.getBoardPosition(), pos, token);
					}
					else if (board.getBoardPosition()[x][y].substring(2).equals("Ki")) {
						unconvertedMoves = Piece.calculateKingMoves(board.getBoardPosition(), pos, token);
					}
					else if (board.getBoardPosition()[x][y].substring(2).equals("Qu")) {
						unconvertedMoves = Piece.calculateQueenMoves(board.getBoardPosition(), pos, token);
					}
					for (int i = 0; i < 8; i++) {
						for (int n = 0; n < 8; n++) {
							if (unconvertedMoves[i][n]) {
								if (token == 'w') {
									Move move = new Move("white", x, y, i, n);
									moveArrayList.add(move);
								}
								else {
									Move move = new Move("black", x, y, i, n);
									moveArrayList.add(move);
								}
							}
						}
					}
				}
			}
		}
		
		return moveArrayList;
	}
	
	/**
	* Checks whether the specified player is in check
	* @param char, the team colour that is checked
	* @return boolean, true if in check and false if not
	*/
	public boolean isCheck(char team) {
		ArrayList<Move> possibleMoves = getAllValidMoves(team);
		int[] enemyKingLoc = new int[2];
		enemyKingLoc[0] = 10;
		enemyKingLoc[1] = 10;
		boolean isCheckBoolean = false;
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if (board.getBoardPosition()[x][y].charAt(0) != team && board.getBoardPosition()[x][y].charAt(0) != '0') {
					if (board.getBoardPosition()[x][y].substring(2).equals("Ki")) {
						enemyKingLoc[0] = x;
						enemyKingLoc[1] = y;
					}
				}
			}
		}
		if (enemyKingLoc[0] == 10 || enemyKingLoc[1] == 10) {
			return false;
		}
		for (Move move : possibleMoves) {
			if (move.getTo()[0] == enemyKingLoc[0] && move.getTo()[1] == enemyKingLoc[1]) {
				isCheckBoolean = true;
			}
		}
		return isCheckBoolean;
	}
	
	/**
	* Checks whether the specified player is in check mate
	* @param char, the team color that is checked
	* @return boolean, true if in check mate and false if not
	*/
	/*public boolean isCheckMate(char team) {
		char enemyColor;
		boolean isCheckMateBool = true;
		int[] kingLoc = new int[2];
		kingLoc[0] = 10;
		kingLoc[1] = 10;
		if (team == 'w') {
			enemyColor = 'b';
		}
		else {
			enemyColor = 'w';
		}
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if (board.getBoardPosition()[x][y].charAt(0) == team) {
					if (board.getBoardPosition()[x][y].substring(2).equals("Ki")) {
						kingLoc[0] = x;
						kingLoc[1] = y;
					}
				}
			}
		}
		if (kingLoc[0] == 10 || kingLoc[1] == 10) {
			return false;
		}
		Move[] allMoves = getAllValidMoves(team);
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if (unconvertedKingMoves[x][y]) {
					String pieceId = team + "_Ki";
					String currentPiece = board.getBoardPosition()[x][y];
					board.setBoardPositions(kingLoc[0], kingLoc[1], "0");
					board.setBoardPositions(x, y, pieceId);
					board.draw();
					if (!isCheck(team)) {
						isCheckMateBool = false;
					}
					board.setBoardPositions(x, y, currentPiece);
					board.setBoardPositions(kingLoc[0], kingLoc[1], pieceId);
				}
			}
		}
		if (!isCheck(team)) {
			isCheckMateBool = false;
		}
		System.out.println(kingLoc[0]);
		System.out.println(kingLoc[1]);
		return isCheckMateBool;
	}*/

	public void save() throws IOException{	//only call at the end when you want to save and exit
		File file = new File("savegame.txt");
		file.createNewFile();
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		String[][] b = board.getBoardPosition();
		for (int r = 0; r < 8; r++){
			for (int c = 0; c < 8; c++){
				bw.write(b[r][c] + " ", 0, b[r][c].length() + 1);
			}
			bw.newLine();
		}
		bw.close();
	}
			
	public boolean load() throws IOException{
		File file = new File("savegame.txt");
		if (file.createNewFile()){
			file.delete();
			return false;
		}

		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		for (int i = 0; i < 8; i++){
			String line = br.readLine();
			for (int x = 0; x < 8; x++) {
				board.setBoardPositions(i, x, line.split(" ")[x]);
			}
		}

		return true;
	}

	public void takeTime(){
		this.turnStartTime = System.currentTimeMillis();
	}

	public void resetPlayerTimes(){
		totalBlackTime = 0;
		totalWhiteTime = 0;
	}

	public long getTotalWhiteTime(){
		return totalWhiteTime;
	}

	public long getTotalBlackTime(){
		return totalBlackTime;
	}

	public TextField getEnterPlayerName() {
		return enterPlayerName;
	}

	public Label getNamesAndScores(){
		return namesAndScores;
	}

	public void updateNamesAndScores (){
		this.namesAndScores.setText(leaderboard.toReadableString());
	}

	public Leaderboard getLeaderboard(){
		return leaderboard;
	}

	public long getWinningTime() {
		return winningTime;
	}

	public void setWinningTime(long winningTime) {
		this.winningTime = winningTime;
	}
		
	public static void main(String[] args) {
		GameConfiguration config = new GameConfiguration();
		config.getBoard().defaultPositions();
		System.out.println("Got here :)");
		try{
		config.save();}
		catch(Exception e){e.printStackTrace();}
		//config.getBoard().draw();
		//System.out.println(config.isCheck('b'));
	}
}