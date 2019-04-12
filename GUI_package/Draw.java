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
* Draw class that does the drawing of the board, pieces, labels during the game and start/end and submenus
*/
public class Draw extends Application {
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
	private String timeTakenText = "";
	private Label teamLabel;
	private Label errorLabel;
	private Label checkLabel;

	//global button so that you can go back to start menu from leaderboard and submenu later
	private Button goBack = new Button("Back to main menu");
	private Button goBackFromLeaderboard = new Button("Back to main menu");

	/**
	* Constructor that sets up the state of the game
	* @param config, the parameter that contains data about the game state
	*/
	public Draw(GameConfiguration config){
		this.config = config;
	}

	/**
	* Setter for the labels that display which team is making a move
	*/
	public void setTeamText(){
		if (config.getWhiteTurn()) { //if it is team white's turn, label displays message
			teamLabel.setText("White team's move");
		}
		else { //if it is team black's turn, label displays message
			teamLabel.setText("Black team's move");
		}
	}

	/**
	* Setter for the label at the end of the game indicating which team won
	* @param t, the text displayed in the label
	*/
	public void setEndText(String t) {
		endText = t;
	}

	public void setTimeTakenText(String timeTaken) {
		timeTakenText = timeTaken;
	}

	/**
	* Setter for the label that indicates a player made an invalid move
	* Displayed when called in ClickHandle class if an invalid move is made
	* @param txt, the text displayed in the label
	*/
	public void setErrorText(String txt) {
		errorLabel.setText(txt);
	}
	
	/**
	* Setter for the label that indicates which team's king is in check
	* Displayed when called in ClickHandle class when a team's king is in check
	* @param txt, the text displayed in the label
	*/
	public void setCheckText(String txt) {
		checkLabel.setText(txt);
	}

	/**
	* Draws the pieces on the board in the default positions
	*/
	public void draw(){
		String[][] board = config.getBoard().getBoardPosition();
		//place pieces on board
		for (int r = 0; r < 8; r++){
			for (int c = 0; c < 8; c++){

				String inputString = "";
				String pieceType = "";
				if (board[r][c].charAt(0) == 'w'){
					inputString = "graphics_package/w_"; //package that the graphics are located in, w_ to retrieve white pieces
					pieceType = board[r][c].substring(2);
				}
				else if (board[r][c].charAt(0) == 'b'){
					inputString = "graphics_package/b_";   //package that the graphics are located in, b_ to retrieve white pieces
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

	/**
	* Clears the board when pieces are captured
	*/
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
	* Start of Draw class to display the graphics of the game
	* @param primaryStage, the Stage
	*/
	public void start(Stage primaryStage) throws FileNotFoundException {
		//GAME GUI

		//Image instance created, passing FileInputStream as parameter to the Image to load the image 
		Image boardImage = new Image(new FileInputStream("graphics_package/Chessboard.png")); //parameter is the image file path

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

		//vbox to split the bottom section into 2 HBoxes

		VBox bottomSection = new VBox();

		//Label for which team is playing
		HBox teamPlayingContainer = new HBox();
		teamLabel = new Label("");
		teamLabel.setFont(new Font("Arial", 20));
		teamPlayingContainer.getChildren().add(teamLabel);
		teamPlayingContainer.setAlignment(Pos.CENTER);

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
		
		bottomSection.getChildren().add(teamPlayingContainer); // stack team playing text on top of save button,  error message and in check message
		bottomSection.getChildren().add(bottomContainer);
		bottomSection.setSpacing(10);
		bottomSection.setPadding(new Insets(5, 0, 10, 0));
		pane.setBottom(bottomSection);

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
	* Includes welcome message and allows user to choose from 3 buttons:
	* to play the GUI version of the game, to play the text version, or to view the leaderboard
	* @return Scene for the start menu
	*/
	public Scene buildStartMenuScene() {
		// START MENU GUI
		BorderPane startPane = new BorderPane();

		//adds background image
		Image bgImage = new Image("graphics_package/chessboard_stockPhoto.jpg"); //Photo by Charlie Solorzano on Unsplash.com
		ImageView imageView = new ImageView(bgImage);
		startPane.getChildren().addAll(imageView);

		// Labels on start menu - VBox to hold welcome message and sub message labels
		VBox topPane = new VBox(15);
		topPane.setAlignment(Pos.TOP_CENTER); //aligns VBox to centre so that labels are centered
		topPane.setPadding(new Insets(50,0,30,0));
		Label welcomeMessage = new Label("Welcome to Chess!");
		Label authors = new Label("Created by: Carmen, Dany, Riley, Shavonne, Tom");
		Label subMessage = new Label("Select from the options below:");
		// increase font size of labels
		welcomeMessage.setStyle("-fx-font-size: 40px;");
		authors.setStyle("-fx-font-size: 16px;");
		subMessage.setStyle("-fx-font-size: 20px;");
		welcomeMessage.setTextFill(Color.WHITE);
		authors.setTextFill(Color.WHITE);
		subMessage.setTextFill(Color.WHITE);
		//adds labels to VBox
		topPane.getChildren().add(welcomeMessage);
		topPane.getChildren().add(authors);
		topPane.getChildren().add(subMessage);

		//Buttons on Start Menu - VBox to hold 3 buttons
		VBox centrePane = new VBox(15);
		centrePane.setAlignment(Pos.TOP_CENTER); //aligns VBox to centre so that labels are centered
		centrePane.setPadding(new Insets(20,0,20,0));
		//Start menu buttons
		Button playGUI = new Button("Play Chess (GUI Version)");
		
		playGUI.setOnMouseClicked(new GameConfigClickHandle(primaryStageCopy, subMenuCopy, config, this, 'g'));
		
		Button playText = new Button("Play Chess (Text Version)");
		
		playText.setOnMouseClicked(new GameConfigClickHandle(primaryStageCopy, gameSceneCopy, config, this, 't'));
		
		Button viewLeaderboard = new Button("View Leaderboard");
		viewLeaderboard.setOnMouseClicked(new GameConfigClickHandle(primaryStageCopy, leaderboardCopy, config, this, 'h'));

		//increase font size of buttons
		playGUI.setStyle("-fx-font-size: 16px;");
		playText.setStyle("-fx-font-size: 16px;");
		viewLeaderboard.setStyle("-fx-font-size: 16px;");
		//Adds labels to VBox
		centrePane.getChildren().add(playGUI);
		centrePane.getChildren().add(playText);
		centrePane.getChildren().add(viewLeaderboard);

		//Adds the VBox for labels and buttons to the BorderPane
		startPane.setTop(topPane);
		startPane.setCenter(centrePane);
		return new Scene(startPane, 450, 500);
	}

	/**
	* Builds the sub menu, which is presented upon clicking on the GUI version of the game in the start menu
	* users are presented with options to play a new game against a human or computer, or to load their game from a previous save
	* @return Scene for the sub menu
	*/
	public Scene buildSubMenuScene() {
		//SUB MENU GUI
		BorderPane subPane = new BorderPane();
		//Vbox for labels on top
		VBox subTop = new VBox();
		subTop.setAlignment(Pos.TOP_CENTER); //aligns VBox to centre so that labels are centered
		subTop.setPadding(new Insets(100,0,50,0));
		Label chooseBelow = new Label("Choose from an option below:");
		// increase font size of labels
		chooseBelow.setStyle("-fx-font-size: 20px;");
		//adds labels to VBox
		subTop.getChildren().add(chooseBelow);

		//VBox with buttons
		VBox subCentre = new VBox(15);
		subCentre.setAlignment(Pos.TOP_CENTER); //aligns VBox to centre so that labels are centered
		subCentre.setPadding(new Insets(20,0,20,0));
		//buttons on sub menu
		Button newGameHuman = new Button("New Game (VS human player)");
		
		newGameHuman.setOnMouseClicked(new GameConfigClickHandle(primaryStageCopy, gameSceneCopy, config, this, 'g'));
		
		Button newGameComputer = new Button("New Game (VS computer)"); //create buttons for the submenu options
		newGameComputer.setOnMouseClicked(new GameConfigClickHandle(primaryStageCopy, gameSceneCopy, config, this, 'c'));
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

	/** Creates Scene for the end menu. Includes message of game win/lose and gives options for player to play again
	* Player can choose to play again (returns to start menu), saving their score or to view the leaderboard
	* @return Scene for end menu
	*/
	public Scene buildEndMenuScene() {
		// END MENU GUI
		BorderPane endPane = new BorderPane();

		// Labels on end menu - VBox to hold win/lost message and sub message labels
		VBox endTop = new VBox(5);
		endTop.setAlignment(Pos.TOP_CENTER); //aligns VBox to centre so that labels are centered
		endTop.setPadding(new Insets(50,0,30,0));
		Label winStatus = new Label(endText); //Says which team won
		Label timeTaken = new Label(timeTakenText); //Says which team won
		Label playAgain = new Label("Play again?");
		// increase font size of labels
		winStatus.setStyle("-fx-font-size: 35px;");
		timeTaken.setStyle("-fx-font-size: 20px;");
		playAgain.setStyle("-fx-font-size: 20px;");
		winStatus.setAlignment(Pos.CENTER);
		timeTaken.setAlignment(Pos.CENTER);
		//adds labels to VBox
		endTop.getChildren().add(winStatus);
		endTop.getChildren().add(timeTaken);
		endTop.getChildren().add(playAgain);
		endTop.setAlignment(Pos.CENTER);

		//Buttons on End Menu - VBox to hold 3 buttons - Same as Start buttons
		VBox endCentre = new VBox(15);
		endCentre.setAlignment(Pos.TOP_CENTER); //aligns VBox to centre so that labels are centered
		endCentre.setPadding(new Insets(20,0,20,0));
		//end menu buttons
		
		Button playAgainBtn = new Button("Play Again!");
		playAgainBtn.setOnMouseClicked(new GameConfigClickHandle(primaryStageCopy, gameSceneCopy, config, this, 'g'));
		
		Button saveScore = new Button("Save Score to Leaderboard");
		saveScore.setOnMouseClicked(new GameConfigClickHandle(primaryStageCopy, enterNameCopy, config, this, 'n'));
		Button playAgainLeaderboard = new Button("View Leaderboard");
		playAgainLeaderboard.setOnMouseClicked(new GameConfigClickHandle(primaryStageCopy, leaderboardCopy, config, this, 'h'));
		//increases size of buttons
		playAgainBtn.setStyle("-fx-font-size: 16px;");
		saveScore.setStyle("-fx-font-size: 16px;");
		playAgainLeaderboard.setStyle("-fx-font-size: 16px;");
		//Adds labels to VBox
		endCentre.getChildren().add(playAgainBtn);
		endCentre.getChildren().add(saveScore);
		endCentre.getChildren().add(playAgainLeaderboard);

		//Adds the VBox for labels and buttons to the BorderPane
		endPane.setTop(endTop);
		endPane.setCenter(endCentre);

		return new Scene(endPane, 450, 500);
	}

	/**
	* Creates a Scene for entering a player's name to the scoreboard
	*/
	public Scene buildEnterNameScene() {
		BorderPane startPane = new BorderPane();

		// VBox to hold welcome message and sub message labels
		VBox topPane = new VBox(5);
		topPane.setPrefWidth(100); //setting pref width and height so that text inside VBox is vertically centred
		topPane.setPrefHeight(400);
		topPane.setPadding(new Insets(10, 50, 50, 50));
		topPane.setAlignment(Pos.CENTER); //aligns VBox to centre so that labels are centered
		Label leaderboardMessage = new Label("Enter your name to leaderboard here:");
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

	/**
	* Creates a Scene for creating the leaderboard to display the top player's high scores
	*/
	public Scene buildLeaderboardScene() {
		// LEADERBOARD
		BorderPane startPane = new BorderPane();

		// Labels on leaderboard - VBox to hold welcome message and sub message labels
		VBox topPane = new VBox(15);
		topPane.setPadding(new Insets(50, 0, 20, 0));
		topPane.setAlignment(Pos.CENTER); //aligns VBox to centre so that labels are centered
		Label leaderboardMessage = new Label("Leaderboard: High Scores");
		Label leaderboardDescription = new Label("Names and time taken to win");
		// increase font size of labels
		leaderboardMessage.setStyle("-fx-font-weight: bold;-fx-font-size: 25px;");
		leaderboardDescription.setStyle("-fx-font-size: 20px;");

		//adds labels to VBox
		topPane.getChildren().add(leaderboardMessage);
		topPane.getChildren().add(leaderboardDescription);

		//Leader board scores - VBox to hold names and scores
		VBox centrePane = new VBox();
		centrePane.setPrefWidth(100); //setting pref width and height so that text inside VBox is vertically centred
		centrePane.setPrefHeight(400);
		centrePane.setAlignment(Pos.TOP_CENTER);
		Label namesAndScores = config.getNamesAndScores();
		config.updateNamesAndScores();
		namesAndScores.setStyle("-fx-font-size: 16px;");
		namesAndScores.setAlignment(Pos.CENTER);
		centrePane.getChildren().add(namesAndScores);

		//Buttons on leaderboard - VBox to hold button
		HBox bottomPane = new HBox(10);
		bottomPane.setPrefWidth(100); //setting pref width and height so that text inside VBox is vertically centred
		bottomPane.setPrefHeight(50);
		bottomPane.setAlignment(Pos.CENTER); //aligns VBox to centre so that labels are centered
		goBackFromLeaderboard.setStyle("-fx-font-size: 12px;");
		//Adds labels to VBox
		bottomPane.getChildren().add(goBackFromLeaderboard); //this is a global variable, style was set in buildSubMenuScene

		//Adds the VBox for labels and buttons to the BorderPane
		startPane.setTop(topPane);
		startPane.setCenter(centrePane);
		startPane.setBottom(bottomPane);

		return new Scene(startPane, 450, 500);
	}

}
