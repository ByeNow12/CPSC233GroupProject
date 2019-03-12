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

/**
* 2019-03-06
* Author: Shavonne
* GUI class that displays the chess board and pieces of the game 
* Updates the board display after each move 
*/
public class GUIGame extends Application {
	
	private ComputerPlayer ai;
	private GameConfiguration config;
	private boolean gameOver;
	private Button[][] boardButtons;
	private StackPane wrap;
	private Pane piecePane = new Pane();
	private Pane eventPane;
	
	/**
	* initializes the positions of the chess pieces on the board before the game starts
	*/
	public void initialize() {
		//config.getBoard().defaultPositions();
		//do more stuff too probably
		//draw();
	}
	
	/**
	* updates the state of the game and GUI display based on user input
	*/
	public void update() {
			
	}
	
	public GameConfiguration getConfig() {
		return config;
	}
	
	public StackPane getWrap() {
		return wrap;
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
				}
				else if (pieceType.equals("Kn")) {
					inputString += "knight.png";
					//draw knight at position (r*50, c*50)
				}
				else if (pieceType.equals("Bi")) {
					inputString += "bishop.png";
					//draw bishop at position (r*50, c*50)
				}
				else if (pieceType.equals("Qu")) {
					inputString += "queen.png";
					//draw queen at position (r*50, c*50)
				}
				else if (pieceType.equals("Ki")) {
					inputString += "king.png";
					//draw king at position (r*50, c*50)
				}
				else if (pieceType.equals("Pa")) {
					inputString += "pawn.png";
					//draw pawn at position (r*50, c*50)
				}
				drawPiece(inputString, r, c);
			}
		}
		//write something below/above the board
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
			System.out.println("File Not Found");
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
			System.out.println("File not Found");
		}
	}
	
	/**
	* Highlights the moves that can be made
	*/
	public void highlightMoves(int[] pos, char team, String pieceType) {
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
			System.out.println("File not Found");
		}
	}

	/**
	* Sets up the graphics to seen on screen with javafx
	* Accepts Png images and displays them 
	* @param primaryStage: a Stage object that is used to set the scene
	*/
	public void start(Stage primaryStage) throws FileNotFoundException {
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
		
		config = new GameConfiguration();
		config.getBoard().defaultPositions();
		
		Scene scene = new Scene(pane, 450, 500); //window size is 450 by 500 pixels
		//tie the mouse event to the wrapping pane. The team must be specified
		wrap.setOnMouseClicked(new ClickHandle(this, config, 'w'));
		primaryStage.setTitle("Chess Game"); //set title to stage
		primaryStage.setScene(scene);
		primaryStage.show();
		draw();
	}
	
	/**
	* java main method. It starts javafx and calls the start method
	*/
	public static void main(String[] args) {
		launch(args);
	}
}