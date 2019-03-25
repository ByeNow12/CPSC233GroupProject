package GUI_package;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import logic_package.*;

import javafx.application.Application;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;

/**
* 2019-03-24
* Authors: Dany, Carmen, Shavonne
* Draw class that does the drawing of the board, pieces and start/end menus of the game
*/
public class Draw extends Application {
	private StackPane wrappingPane;
	private Pane eventPane;
	private StackPane wrap;
	private GameConfiguration config;
	private Pane piecePane = new Pane();

	public Draw(GameConfiguration config){
		this.config = config;
	}

	public void draw(){
		String[][] board = config.getBoard().getBoardPosition();
		//place pieces on board
		for (int r = 0; r < 8; r++){
			for (int c = 0; c < 8; c++){

				String inputString = "";
				String pieceType = "";
				if (board[r][c].charAt(0) == 'w'){
					inputString = "w_";
					pieceType = board[r][c].substring(2);
				}
				else if (board[r][c].charAt(0) == 'b'){
					inputString = "b_";   //this is the only thing that has to be changed to put in the outline ones instead
					pieceType = board[r][c].substring(2);
				}
				if (pieceType.equals("Ro")) {
					inputString += "rook.png";
					//draw rook at position (r*50, c*50)
					drawPiece(inputString, r, c);
				}
				else if (pieceType.equals("Kn")) {
					inputString += "knight.png";
					//draw knight at position (r*50, c*50)
					drawPiece(inputString, r, c);
				}
				else if (pieceType.equals("Bi")) {
					inputString += "bishop.png";
					//draw bishop at position (r*50, c*50)
					drawPiece(inputString, r, c);
				}
				else if (pieceType.equals("Qu")) {
					inputString += "queen.png";
					//draw queen at position (r*50, c*50)
					drawPiece(inputString, r, c);
				}
				else if (pieceType.equals("Ki")) {
					inputString += "king.png";
					//draw king at position (r*50, c*50)
					drawPiece(inputString, r, c);
				}
				else if (pieceType.equals("Pa")) {
					inputString += "pawn.png";
					//draw pawn at position (r*50, c*50)
					drawPiece(inputString, r, c);
				}
			}
		}
		//write something below/above the board
	}

	public void clear(){
		eventPane.getChildren().clear();
	}
	/**
	 * Draw the specified file onto screen on the specified coordinates
	 * @param String file, the name of the file to draw.
	 * @param int x, x coordinate.
	 * @param int y, y coordinate.
	 */
	public void drawPiece(String file, int x, int y) {
		x = x*50;
		y = y*50;
		try {
			Image pieceImage = new Image(new FileInputStream(file));
			ImageView pieceImageView = new ImageView(pieceImage);

			pieceImageView.setLayoutX(y);
			pieceImageView.setLayoutY(x);
			eventPane.getChildren().add(pieceImageView);
		}
		catch (FileNotFoundException error) {
			//error.printStackTrace();
		}
	}

	/**
	 * Highlights the specified square.
	 * @param int[] pos, x and y coordinate of the square.
	 */
	public void highlightSelectedSquare(int[] pos) {
		try {
			Image selectSquare = new Image(new FileInputStream("blue.png"));
			ImageView selectSquareView = new ImageView(selectSquare);

			int x = pos[1]*50;
			int y = pos[0]*50;

			selectSquareView.setLayoutX(x);
			selectSquareView.setLayoutY(y);
			eventPane.getChildren().add(selectSquareView);
		}
		catch (FileNotFoundException error) {
			System.out.println("Blue File not Found");
		}
	}

	/**
	 * Highlights the moves that can be made
	 */
	public void highlightMoves(int[] pos, char team, String pieceType){
		String[][] boardPositions = config.getBoard().getBoardPosition();
		try {
			Image possibleSquare = new Image(new FileInputStream("green.png"));
			int x;
			int y;
			boolean[][] possibleMoves = new boolean[8][8];
			if (pieceType.equals("Ro")) {
				possibleMoves = Piece.calculateRookMoves(boardPositions, pos, team);
			}
			else if (pieceType.equals("Kn")) {
				possibleMoves = Piece.calculateKnightMoves(boardPositions, pos, team);
			}
			else if (pieceType.equals("Bi")) {
				possibleMoves = Piece.calculateBishopMoves(boardPositions, pos, team);
			}
			else if (pieceType.equals("Qu")) {
				possibleMoves = Piece.calculateQueenMoves(boardPositions, pos, team);
			}
			else if (pieceType.equals("Ki")) {
				possibleMoves = Piece.calculateKingMoves(boardPositions, pos, team);
			}
			else if (pieceType.equals("Pa")) {
				possibleMoves = Piece.calculatePawnMoves(boardPositions, pos, team);
			}

			for (int i = 0; i < 8; i++) {
				for (int n = 0; n < 8; n++) {
					if (possibleMoves[i][n]) {
						ImageView possibleSquareView = new ImageView(possibleSquare);
						x = n*50;
						y = i*50;
						possibleSquareView.setLayoutX(x);
						possibleSquareView.setLayoutY(y);
						eventPane.getChildren().add(possibleSquareView);
					}
				}
			}
		}
		catch(FileNotFoundException error) {
			error.printStackTrace();
		}
	}

	/**
	 * changes text based on which team it is
	 **/
	public String bottomLabel (char team) {
		String currentTeam = "";
		if (team == 'w'){
			currentTeam = "White team";
		}
		else if (team == 'b' ){
			currentTeam = "Black team";
		}
		return ("Current turn: "+currentTeam);
	}

	public void start(Stage primaryStage) throws FileNotFoundException {
		//GAME GUI

		//Image instance created, passing FileInputStream as parameter to the Image to load the image 
		Image boardImage = new Image(new FileInputStream("Chessboard.png")); //parameter is the image file path
		//file path to image depends on where you save board image

		//ImageView instance created, passing Image instance as parameter
		ImageView boardImageView = new ImageView(boardImage);

		//BorderPane to center the chess board image
		BorderPane pane = new BorderPane();
		//wrap for the center of the border pane
		wrap = new StackPane();
		//pane that handle all event handling
		eventPane = new Pane();
		Scene gameScene = new Scene(pane, 450, 500); //window size is 450 by 500 pixels
		wrap.setMaxSize(400, 400);
		wrap.getChildren().add(boardImageView);
		wrap.getChildren().add(eventPane);
		wrap.getChildren().add(piecePane);
		pane.setCenter(wrap);

		/* Label in game - incomplete
		*/ 

		//Label whitePlaying = new Label(bottomLabel('w')); //label reads Current turn: White team
		//Label blackPlaying = new Label(bottomLabel('b')); //label reads Current turn: Black team
		
		//gamePane.getChildren().add(whitePlaying); //add labels to gamePane
		//gamePane.getChildren().add(blackPlaying);
		
		//gamePane.setBottom(whitePlaying); //set the labels displayed in gamePane to bottom of board
		//gamePane.setBottom(blackPlaying);
		config = new GameConfiguration();
		config.getBoard().defaultPositions();


		//creates new instances of start menu, sub menu and end menu
		Scene startMenu = buildStartMenuScene();
		Scene subMenu = buildSubMenuScene();
		Scene endMenu = buildEndMenuScene();

		//tie the mouse event to the wrapping pane. The team must be specified
		wrap.setOnMouseClicked(new ClickHandle(this, config, 'w'));
		primaryStage.setTitle("Chess Game"); //set title to stage

		//For testing purposes: start Menu = (startMenu), game = (gameScene), end menu (endMenu)
		primaryStage.setScene(gameScene);
		primaryStage.show();
		draw();
	}

	/**
	 * Creates start menu, which is the first scene that players will see upon opening game
	 * Includes welcome message and allows user to choose from 3 buttons - to play the GUI version of the game, to play the text version,
	 * or to view the scoreboard
	 * @return Scene for the start menu
	 */
	private Scene buildStartMenuScene() {
		// START MENU GUI
		BorderPane startPane = new BorderPane();

		// Labels on start menu - VBox to hold welcome message and sub message labels
		VBox topPane = new VBox(5);
		topPane.setPrefWidth(100); //setting pref width and height so that text inside VBox is vertically centred
		topPane.setPrefHeight(200);
		topPane.setAlignment(Pos.CENTER); //aligns VBox to centre so that labels are centered
		Label welcomeMessage = new Label("Welcome to Chess!");
		Label subMessage = new Label("Select from the options below:");
		// increase font size of labels
		welcomeMessage.setStyle("-fx-font-size: 40px;");
		subMessage.setStyle("-fx-font-size: 20px;");
		//adds labels to VBox
		topPane.getChildren().add(welcomeMessage);
		topPane.getChildren().add(subMessage);

		//Buttons on Start Menu - VBox to hold 3 buttons
		VBox centrePane = new VBox(10);
		centrePane.setPrefWidth(100); //setting pref width and height so that text inside VBox is vertically centred
		centrePane.setPrefHeight(300);
		centrePane.setAlignment(Pos.CENTER); //aligns VBox to centre so that labels are centered
		//Start menu buttons
		Button playGUI = new Button("Play Chess (GUI Version)");
		Button playText = new Button("Play Chess (Text Version)");
		Button viewScoreboard = new Button("View Scoreboard");
		//increase font size of buttons
		playGUI.setStyle("-fx-font-size: 16px;");
		playText.setStyle("-fx-font-size: 16px;");
		viewScoreboard.setStyle("-fx-font-size: 16px;");
		//Adds labels to VBox
		centrePane.getChildren().add(playGUI);
		centrePane.getChildren().add(playText);
		centrePane.getChildren().add(viewScoreboard);

		//Adds the VBox for labels and buttons to the BorderPane
		startPane.setTop(topPane);
		startPane.setCenter(centrePane);

		return new Scene(startPane, 450, 500);
	}

	/**
	 * builds the sub menu, which is presented upon clickin on the GUI version of the game in the start menu
	 * users are presented with options to play a new game against a human or computer, or to load their game from a previous save
	 * @return Scene for the sub menu
	 */
	private Scene buildSubMenuScene() {
		//SUB MENU GUI
		BorderPane subPane = new BorderPane();
		//Vbox for labels on top
		VBox subTop = new VBox();
		subTop.setPrefWidth(100); //setting pref width and height so that text inside VBox is vertically centred
		subTop.setPrefHeight(200);
		subTop.setAlignment(Pos.CENTER); //aligns VBox to centre so that labels are centered
		Label chooseBelow = new Label("Choose from an option below:");
		// increase font size of labels
		chooseBelow.setStyle("-fx-font-size: 20px;");
		//adds labels to VBox
		subTop.getChildren().add(chooseBelow);

		//VBox with buttons
		VBox subCentre = new VBox(10);
		subCentre.setPrefWidth(100); //setting pref width and height so that text inside VBox is vertically centred
		subCentre.setPrefHeight(300);
		subCentre.setAlignment(Pos.CENTER); //aligns VBox to centre so that labels are centered
		//buttons on sub menu
		Button newGameHuman = new Button("New Game (VS human player)");
		Button newGameComputer = new Button("New Game (VS computer)");
		Button loadGame = new Button("Load Game from Save");
		//increase font size of buttons
		newGameHuman.setStyle("-fx-font-size: 16px;");
		newGameComputer.setStyle("-fx-font-size: 16px;");
		loadGame.setStyle("-fx-font-size: 16px;");
		//Adds labels to VBox
		subCentre.getChildren().add(newGameHuman);
		subCentre.getChildren().add(newGameComputer);
		subCentre.getChildren().add(loadGame);

		subPane.setTop(subTop);
		subPane.setCenter(subCentre);

		return new Scene(subPane, 450,500);
	}

	/** Creates end menu for game. Includes message of game win/lose and gives options for player to play again
	 * player can choose from playing again (returns to start menu), saving their score or to view the scoreboard
	 * @return Scene for end menu
	 * */
	private Scene buildEndMenuScene() {
		// END MENU GUI
		BorderPane endPane = new BorderPane();

		// Labels on end menu - VBox to hold win/lost message and sub message labels
		VBox endTop = new VBox(5);
		endTop.setPrefWidth(100); //setting pref width and height so that text inside VBox is vertically centred
		endTop.setPrefHeight(200);
		endTop.setAlignment(Pos.CENTER); //aligns VBox to centre so that labels are centered
		Label winStatus = new Label("You won/lost!"); //NEEDS TO BE DYNAMIC
		Label playAgain = new Label("Play again?");
		// increase font size of labels
		winStatus.setStyle("-fx-font-size: 40px;");
		playAgain.setStyle("-fx-font-size: 20px;");
		//adds labels to VBox
		endTop.getChildren().add(winStatus);
		endTop.getChildren().add(playAgain);

		//Buttons on End Menu - VBox to hold 3 buttons - Same as Start buttons
		VBox endCentre = new VBox(10);
		endCentre.setPrefWidth(100); //setting pref width and height so that text inside VBox is vertically centred
		endCentre.setPrefHeight(300);
		endCentre.setAlignment(Pos.CENTER); //aligns VBox to centre so that labels are centered
		//end menu buttons
		Button playAgainBtn = new Button("Play Again!");
		Button saveScore = new Button("Save Score to Scoreboard");
		Button playAgainScoreboard = new Button("View Scoreboard");
		//increases size of buttons
		playAgainBtn.setStyle("-fx-font-size: 16px;");
		saveScore.setStyle("-fx-font-size: 16px;");
		playAgainScoreboard.setStyle("-fx-font-size: 16px;");
		//Adds labels to VBox
		endCentre.getChildren().add(playAgainBtn);
		endCentre.getChildren().add(saveScore);
		endCentre.getChildren().add(playAgainScoreboard);

		//Adds the VBox for labels and buttons to the BorderPane
		endPane.setTop(endTop);
		endPane.setCenter(endCentre);

		return new Scene(endPane, 450, 500);
	}
}
