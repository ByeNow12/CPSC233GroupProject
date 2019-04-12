package logic_package;

import java.util.ArrayList;
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

	/**
	 * Constructor for game configuration. Creates new board object, sets board to default positions,
	 * creates new leaderboard object and sets white team's turn as true
	 */
	public GameConfiguration() {
		board = new Board();
		board.defaultPositions();
		leaderboard = new Leaderboard("newgame");
		whiteTurn = true;
	}

	/**
	* Getter method for whiteTurn instance variable
	* returns true if it is white's turn and false otherwise
	* @return whiteTurn, boolean
	*/
	public boolean getWhiteTurn() {
		return whiteTurn;
	}

	/**
	* Setter method for the whiteTurn instance variable
	* @param value, boolean
	*/
	public void setWhiteTurn(boolean value) {
		whiteTurn = value;
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

	/**
	 * "Promotes" pawn to a queen type piece when it reaches the other side of the board
	 * Checks the board for a pawn at the opposite side of the board
	 */
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

		if (pieceType.equals("Ro")) {
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
		if (team == 'w') {
			team = 'b';
		}
		else {
			team = 'w';
		}
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
	public boolean isCheckMate(char team) {
		boolean isCheckMateBool = false;
		boolean turn = getWhiteTurn();
		int[] kingLoc = {10, 10};
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
			// In case this method is called before the game ends.
			return false;
		}
		if (isCheck(team)) {
			isCheckMateBool = true;
			ArrayList<Move> allMoves = getAllValidMoves(team);
			for (int i = 0; i < allMoves.size(); i++) {
				Move move = allMoves.get(i);
				String currPiece = board.getBoardPosition()[move.getFrom()[0]][move.getFrom()[1]];
				String nextPiece = board.getBoardPosition()[move.getTo()[0]][move.getTo()[1]];
				update(move);
				if (!isCheck(team) || hasWon(team)) {
					isCheckMateBool = false;
				}
				board.setBoardPositions(move.getFrom()[0], move.getFrom()[1], currPiece);
				board.setBoardPositions(move.getTo()[0], move.getTo()[1], nextPiece);
			}
		}
		setWhiteTurn(turn);
		return isCheckMateBool;
	}

	/**
	 * saves current game as savegame.txt, formats into "<colour>_<piecetype>" and writes 0 if the square is empty
	 * @throws IOException
	 */
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
		if (whiteTurn) {
			bw.write("true");
		}
		else {
			bw.write("false");
		}
		bw.close();
	}

	/**
	 * loads saved game from savegame.txt and sets board positions according to what the pieces are
	 */
	public boolean load() throws IOException{
		File file = new File("savegame.txt");
		if (file.createNewFile()){
			file.delete();
			return false;
		}

		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		for (int i = 0; i < 9; i++){
			String line = br.readLine();
			if ((line.split(" ")).length <= 1) {
				setWhiteTurn(Boolean.valueOf(line.split(" ")[0]));
				return true;
			}
			for (int x = 0; x < 8; x++) {
				board.setBoardPositions(i, x, line.split(" ")[x]);
			}
		}

		return true;
	}

	/**
	 * takes the current time in milliseconds
	 */
	public void takeTime(){
		this.turnStartTime = System.currentTimeMillis();
	}

	/**
	 * resets the time to 0 for both players, so that when a second game is played, the time does not compound
	 */
	public void resetPlayerTimes(){
		totalBlackTime = 0;
		totalWhiteTime = 0;
	}

	/**
	 * returns the total time that white team player spent making moves before winning game
	 * @return totalWhiteTime as a long
	 */
	public long getTotalWhiteTime(){
		return totalWhiteTime;
	}

	/**
	 * returns the total time that black team player spent making moves before winning game
	 * @return totalBlackTime as a long
	 */
	public long getTotalBlackTime(){
		return totalBlackTime;
	}

	/**
	 * Player's name can be entered
	 * @return enterPlayerName as a String
	 */
	public TextField getEnterPlayerName() {
		return enterPlayerName;
	}

	/**
	 * gets label of the player's name and score
	 * @return namesAndScores as a label
	 */
	public Label getNamesAndScores(){
		return namesAndScores;
	}

	/**
	 * Updates names and scores of leaderboard, calls method from leaderboard that converts the text file to readable string
	 */
	public void updateNamesAndScores (){
		this.namesAndScores.setText(leaderboard.toReadableString());
	}

	/**
	 * Gets leaderboard object
	 * @return leaderboard
	 */
	public Leaderboard getLeaderboard(){
		return leaderboard;
	}

	/**
	 * gets the total time it took for winner to finish game
	 * @return winninTime as long, in miliseconds
	 */
	public long getWinningTime() {
		return winningTime;
	}

	/**
	 * sets winning time to the winning time in the argument
	 * @param winningTime, the time in miliseconds as a long
	 */
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
