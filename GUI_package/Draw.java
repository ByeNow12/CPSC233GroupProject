package GUI_package;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
* Draw class that does the drawing of the board, pieces, labels during the game and start/end menus 
*/
public class Draw extends Application {
	private StackPane wrappingPane;
	private Pane eventPane;
	private StackPane wrap;
	private GameConfiguration config;
	private Pane piecePane = new Pane();
	private Stage primaryStageCopy;
	private Scene gameSceneCopy;
	private Scene startMenuCopy;
	private Scene subMenuCopy;
	private Scene endMenuCopy;
	private Scene leaderboardCopy;
	private Scene enterNameCopy;
	private String endText = "You Won!";
	private Label errorLabel;
	private Label checkLabel;

	//global button so that you can go back to start menu from leaderboard and submenu later
	Button goBack = new Button("Back to main menu");
	Button goBackFromLeaderboard = new Button("Back to main menu");



	public Draw(GameConfiguration config){
		this.config = config;
	}
	
	public void setEndText(String t) {
		endText = t;
	}
	
	public void setErrorText(String txt) {
		errorLabel.setText(txt);
	}
	
	public void setCheckText(String txt) {
		checkLabel.setText(txt);
	}

	public void draw(){
		String[][] board = config.getBoard().getBoardPosition();
		//place pieces on board
		for (int r = 0; r < 8; r++){
			for (int c = 0; c < 8; c++){

				String inputString = "";
				String pieceType = "";
				if (board[r][c].charAt(0) == 'w'){
					inputString = "graphics_package/w_";
					pieceType = board[r][c].substring(2);
				}
				else if (board[r][c].charAt(0) == 'b'){
					inputString = "graphics_package/b_";   //this is the only thing that has to be changed to put in the outline ones instead
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
	 * Highlights the square selected by player.
	 * @param int[] pos, x and y coordinate of the square.
	 */
	public void highlightSelectedSquare(int[] pos) {
		try {
			Image selectSquare = new Image(new FileInputStream("graphics_package/blue.png"));
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
	 * Highlights the moves that can be made when player clicks on a piece
	 * @param int[] pos, x and y coordinate of the square
	 * @param char team, to identify the player as white or black team
	 * @param String piecType, to classify piece as one out for the 6 possible pieces in the game
	 *
	 */
	public void highlightMoves(int[] pos, char team, String pieceType){
		String[][] boardPositions = config.getBoard().getBoardPosition();
		try {
			Image possibleSquare = new Image(new FileInputStream("graphics_package/green.png"));
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
	 * @param char team, the team  that is playing
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
	
	/**
	* Start of Draw class
	*/
	public void start(Stage primaryStage) throws FileNotFoundException {
		//GAME GUI

		//Image instance created, passing FileInputStream as parameter to the Image to load the image 
		Image boardImage = new Image(new FileInputStream("graphics_package/Chessboard.png")); //parameter is the image file path
		//file path to image depends on where you save board image

		//ImageView instance created, passing Image instance as parameter
		ImageView boardImageView = new ImageView(boardImage);

		//BorderPane to center the chess board image
		BorderPane pane = new BorderPane();
		
		Button saveGameButton = new Button("Save and Quit");
		saveGameButton.setOnMouseClicked(new GameConfigClickHandle(primaryStage, endMenuCopy, config, this, 's'));
		
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
		
		// Error message label and isCheck label added
		HBox bottomContainer = new HBox();
		errorLabel = new Label("");
		errorLabel.setTextFill(Color.RED);
		errorLabel.setFont(new Font("Arial", 20));
		checkLabel = new Label("");
		checkLabel.setTextFill(Color.GREEN);
		checkLabel.setFont(new Font("Arial", 20));
		bottomContainer.getChildren().add(saveGameButton);
		bottomContainer.getChildren().add(checkLabel);
		bottomContainer.getChildren().add(errorLabel);
		bottomContainer.setAlignment(Pos.CENTER);
		bottomContainer.setSpacing(15);
		
		pane.setBottom(bottomContainer);

		config.getBoard().defaultPositions();
		primaryStageCopy = primaryStage;
		gameSceneCopy = gameScene;


		//creates new instances of start menu, sub menu and end menu
		Scene subMenu = buildSubMenuScene();
		subMenuCopy = subMenu;

		Scene leaderboard = buildLeaderboardScene();
		leaderboardCopy = leaderboard;

		Scene startMenu = buildStartMenuScene();
		startMenuCopy = startMenu;
		// BUTTON TO GO BACK - need to re-wire button in subMenu with the newly created startMenu
		goBack.setOnMouseClicked(new GameConfigClickHandle(primaryStageCopy, startMenuCopy, config, this, 'b'));
		goBackFromLeaderboard.setOnMouseClicked(new GameConfigClickHandle(primaryStageCopy, startMenuCopy, config, this, 'b'));


		Scene endMenu = buildEndMenuScene();
		endMenuCopy = endMenu;

		Scene enterName = buildEnterNameScene();
		enterNameCopy = enterName;


		//tie the mouse event to the wrapping pane. The team must be specified
		wrap.setOnMouseClicked(new ClickHandle(this, config, primaryStage, endMenuCopy, 'w'));
		primaryStage.setTitle("Chess Game"); //set title to stage

		//For testing purposes: start Menu = (startMenu), game = (gameScene), end menu (endMenu)
		primaryStage.setScene(startMenu);
		primaryStage.show();
		draw();
	}

	/**
	 * Creates start menu, which is the first scene that players will see upon opening game
	 * Includes welcome message and allows user to choose from 3 buttons - to play the GUI version of the game, to play the text version,
	 * or to view the scoreboard
	 * @return Scene for the start menu
	 */
	public Scene buildStartMenuScene() {
		// START MENU GUI
		BorderPane startPane = new BorderPane();

		// Labels on start menu - VBox to hold welcome message and sub message labels
		VBox topPane = new VBox(15);
		topPane.setPrefWidth(100); //setting pref width and height so that text inside VBox is vertically centred
		topPane.setPrefHeight(200);
		topPane.setAlignment(Pos.CENTER); //aligns VBox to centre so that labels are centered
		Label welcomeMessage = new Label("Welcome to Chess!");
		Label authors = new Label("Created by: Carmen, Dany, Riley, Shavonne, Tom");
		Label subMessage = new Label("Select from the options below:");
		// increase font size of labels
		welcomeMessage.setStyle("-fx-font-size: 40px;");
		authors.setStyle("-fx-font-size: 16px;");
		subMessage.setStyle("-fx-font-size: 20px;");
		//adds labels to VBox
		topPane.getChildren().add(welcomeMessage);
		topPane.getChildren().add(authors);
		topPane.getChildren().add(subMessage);

		//Buttons on Start Menu - VBox to hold 3 buttons
		VBox centrePane = new VBox(10);
		centrePane.setPrefWidth(100); //setting pref width and height so that text inside VBox is vertically centred
		centrePane.setPrefHeight(300);
		centrePane.setAlignment(Pos.CENTER); //aligns VBox to centre so that labels are centered
		//Start menu buttons
		Button playGUI = new Button("Play Chess (GUI Version)");
		
		playGUI.setOnMouseClicked(new GameConfigClickHandle(primaryStageCopy, subMenuCopy, config, this, 'g'));
		
		Button playText = new Button("Play Chess (Text Version)");
		
		playText.setOnMouseClicked(new GameConfigClickHandle(primaryStageCopy, gameSceneCopy, config, this, 't'));
		
		Button viewScoreboard = new Button("View Leaderboard");
		viewScoreboard.setOnMouseClicked(new GameConfigClickHandle(primaryStageCopy, leaderboardCopy, config, this, 'h'));

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
	public Scene buildSubMenuScene() {
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
		
		newGameHuman.setOnMouseClicked(new GameConfigClickHandle(primaryStageCopy, gameSceneCopy, config, this, 'g'));
		
		Button newGameComputer = new Button("New Game (VS computer)");
		
		// AI not implemented yet
		
		Button loadGame = new Button("Load Game from Save");
		
		loadGame.setOnMouseClicked(new GameConfigClickHandle(primaryStageCopy, gameSceneCopy, config, this, 'l'));




		//increase font size of buttons
		newGameHuman.setStyle("-fx-font-size: 16px;");
		newGameComputer.setStyle("-fx-font-size: 16px;");
		loadGame.setStyle("-fx-font-size: 16px;");
		goBack.setStyle("-fx-font-size: 16px;");

		//Adds labels to VBox
		subCentre.getChildren().add(newGameHuman);
		subCentre.getChildren().add(newGameComputer);
		subCentre.getChildren().add(loadGame);
		// this is a global variable, so we can set the button at a later point in the code
		subCentre.getChildren().add(goBack);

		subPane.setTop(subTop);
		subPane.setCenter(subCentre);

		return new Scene(subPane, 450,500);
	}

	/** Creates end menu for game. Includes message of game win/lose and gives options for player to play again
	* player can choose from playing again (returns to start menu), saving their score or to view the scoreboard
	* @return Scene for end menu
	*/
	public Scene buildEndMenuScene() {
		// END MENU GUI
		BorderPane endPane = new BorderPane();

		// Labels on end menu - VBox to hold win/lost message and sub message labels
		VBox endTop = new VBox(5);
		endTop.setPrefWidth(100); //setting pref width and height so that text inside VBox is vertically centred
		endTop.setPrefHeight(200);
		endTop.setAlignment(Pos.CENTER); //aligns VBox to centre so that labels are centered
		Label winStatus = new Label(endText); //NEEDS TO BE DYNAMIC
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
		playAgainBtn.setOnMouseClicked(new GameConfigClickHandle(primaryStageCopy, gameSceneCopy, config, this, 'g'));
		
		Button saveScore = new Button("Save Score to Scoreboard");
		saveScore.setOnMouseClicked(new GameConfigClickHandle(primaryStageCopy, enterNameCopy, config, this, 'n'));
		Button playAgainScoreboard = new Button("View Leaderboard");
		playAgainScoreboard.setOnMouseClicked(new GameConfigClickHandle(primaryStageCopy, leaderboardCopy, config, this, 'h'));
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

	public Scene buildEnterNameScene(){
		BorderPane startPane = new BorderPane();

		// VBox to hold welcome message and sub message labels
		VBox topPane = new VBox(5);
		topPane.setPrefWidth(100); //setting pref width and height so that text inside VBox is vertically centred
		topPane.setPrefHeight(400);
		topPane.setPadding(new Insets(10, 50, 50, 50));
		topPane.setAlignment(Pos.CENTER); //aligns VBox to centre so that labels are centered
		Label leaderboardMessage = new Label("Enter your name to scoreboard here:");
		TextField enterName =  config.getEnterPlayerName();
		// increase font size of labels
		leaderboardMessage.setStyle("-fx-font-size: 20px;");
		enterName.setStyle("-fx-font-size: 12px;");
		//adds labels to VBox
		topPane.getChildren().add(leaderboardMessage);
		topPane.getChildren().add(enterName);

		//Buttons - VBox to hold 2 buttons
		VBox centrePane = new VBox(10);
		centrePane.setPrefWidth(100); //setting pref width and height so that text inside VBox is vertically centred
		centrePane.setPrefHeight(50);
		centrePane.setAlignment(Pos.CENTER); //aligns VBox to centre so that labels are centered
		//Start menu buttons
		Button saveName = new Button("Save name to leaderboard");
		saveName.setOnMouseClicked(new GameConfigClickHandle(primaryStageCopy, leaderboardCopy, config, this, 'z'));
		Button startMenu = new Button("Go back to main menu");

		startMenu.setOnMouseClicked(new GameConfigClickHandle(primaryStageCopy, startMenuCopy, config, this, 'b'));

		//increase font size of buttons
		startMenu.setStyle("-fx-font-size: 12px;");

		//Adds labels to VBox
		centrePane.getChildren().add(saveName);
		centrePane.getChildren().add(startMenu);

		//Adds the VBox for labels and buttons to the BorderPane
		startPane.setTop(topPane);
		startPane.setCenter(centrePane);

		return new Scene(startPane, 450, 500);
	}

	public Scene buildLeaderboardScene() {
		// LEADERBOARD
		BorderPane startPane = new BorderPane();

		// Labels on leaderboard - VBox to hold welcome message and sub message labels
		VBox topPane = new VBox(5);
		topPane.setPrefWidth(100); //setting pref width and height so that text inside VBox is vertically centred
		topPane.setPrefHeight(400);
		topPane.setAlignment(Pos.CENTER); //aligns VBox to centre so that labels are centered
		Label leaderboardMessage = new Label("Leaderboard - High Scores");
		Label namesAndScores = config.getNamesAndScores();
		config.updateNamesAndScores();
		// increase font size of labels
		leaderboardMessage.setStyle("-fx-font-size: 20px;");
		namesAndScores.setStyle("-fx-font-size: 16px;");
		//adds labels to VBox
		topPane.getChildren().add(leaderboardMessage);
		topPane.getChildren().add(namesAndScores);

		//Buttons on leaderboard - VBox to hold button
		VBox centrePane = new VBox(10);
		centrePane.setPrefWidth(100); //setting pref width and height so that text inside VBox is vertically centred
		centrePane.setPrefHeight(50);
		centrePane.setAlignment(Pos.CENTER); //aligns VBox to centre so that labels are centered
		goBackFromLeaderboard.setStyle("-fx-font-size: 12px;");
		//Adds labels to VBox
		centrePane.getChildren().add(goBackFromLeaderboard); //this is a global variable, style was set in buildSubMenuScene

		//Adds the VBox for labels and buttons to the BorderPane
		startPane.setTop(topPane);
		startPane.setCenter(centrePane);

		return new Scene(startPane, 450, 500);
	}
	
	/**
	* Displays a label that the player made an invalid move if they made one
	* @return Scene for the invalid move label
	*/
	public Scene buildInvalidMoveLabel() {
		//Label to be displayed when player makes invalid move
		BorderPane gameLabelPane1 = new BorderPane();
		Label invalidMove = new Label("You made an invalid move, please make another move");
		gameLabelPane1.getChildren().add(invalidMove);
		gameLabelPane1.setTop(invalidMove);
		
		return new Scene(gameLabelPane1, 450, 500);
	}
	
	/**
	* Displays a label that indicates when team white is making a move
	* @return Scene for the white turn label
	*/
	public Scene buildWhiteTurnLabel() {
		//Label to be displayed when it's white team's turn
		BorderPane gameLabelPane2 = new BorderPane();
		Label whiteTurn = new Label("Team white will make a move now");
		gameLabelPane2.getChildren().add(whiteTurn);
		gameLabelPane2.setTop(whiteTurn);
		
		return new Scene(gameLabelPane2, 450, 500);
	}
	
	/**
	* Displays a label that indicates when team black is making a move
	* @return Scene for the black turn label
	*/
	public Scene buildBlackTurnLabel() {
		//Label to be displayed when it's black team's turn
		BorderPane gameLabelPane3 = new BorderPane();
		Label blackTurn = new Label("Team black will make a move now");
		gameLabelPane3.getChildren().add(blackTurn);
		gameLabelPane3.setTop(blackTurn);
		
		return new Scene(gameLabelPane3, 450, 500);
	}
}
