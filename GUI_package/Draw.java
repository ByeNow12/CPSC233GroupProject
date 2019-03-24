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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;


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
		wrap.setMaxSize(400, 400);
		wrap.getChildren().add(boardImageView);
		wrap.getChildren().add(eventPane);
		wrap.getChildren().add(piecePane);
		pane.setCenter(wrap);

		config.getBoard().defaultPositions();

		Scene gameScene = new Scene(pane, 450, 500); //window size is 450 by 500 pixels

		// START MENU GUI
		BorderPane startPane = new BorderPane();

		// Labels on start menu - VBox to hold welcome message and sub message labels
		VBox topPane = new VBox(5);
		topPane.setPrefWidth(100); //setting pref width and height so that text inside VBox is vertically centred
		topPane.setPrefHeight(200);
		topPane.setAlignment(Pos.CENTER); //aligns VBox to centre so that labels are centered
		Label welcomeMessage = new Label("Welcome to Chess!");
		Label subMessage = new Label("Select from the options below");
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
		//playGUI.setOnAction(e -> primaryStage.setScene(gameScene));

		//Adds the VBox for labels and buttons to the BorderPane
		startPane.setTop(topPane);
		startPane.setCenter(centrePane);

		Scene startMenu = new Scene(startPane, 450, 500);

		//tie the mouse event to the wrapping pane. The team must be specified
		wrap.setOnMouseClicked(new ClickHandle(this, config, 'w'));
		primaryStage.setTitle("Chess Game"); //set title to stage
		primaryStage.setScene(startMenu);
		primaryStage.show();
		draw();
	}


}
