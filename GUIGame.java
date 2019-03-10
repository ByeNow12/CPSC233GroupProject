import javafx.application.Application;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image; 
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;

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
	
	/**
	* initializes the positions of the chess pieces on the board before the game starts
	*/
	public void initialize() {
		config.getBoard().defaultPositions();
		//do more stuff too probably
		draw();
	}
	
	/**
	* updates the state of the game and GUI display based on user input
	*/
	public void update() {
			
	}
	
	public void draw(){
		String[][] board = config.getBoard().getBoardPosition();
		
		//clear board
		//put board back (the checkerboard) if necessary
		//place pieces on board
		for (int r = 0; r < 8; r++){
			for (int c = 0; c < 8; c++){

				String inputString = "D:\\CPSC 233\\Project\\CPSC233GroupProject\\graphical_assets\\";
				if (board[r][c].charAt(0) == 'w'){
					inputString += "white_pieces\\w_";
				}
				else{
					inputString += "black_pieces\\b_";   //this is the only thing that has to be changed to put in the outline ones instead
				}

				String pieceType = board[r][c].substring(2);
				if (pieceType.equals("Ro")) {
					inputString += "rook";
					//draw rook at position (r*50, c*50)
				}
				else if (pieceType.equals("Kn")) {
					inputString += "knight";
					//draw knight at position (r*50, c*50)
				}
				else if (pieceType.equals("Bi")) {
					inputString += "bishop";
					//draw bishop at position (r*50, c*50)
				}
				else if (pieceType.equals("Qu")) {
					inputString += "queen";
					//draw queen at position (r*50, c*50)
				}
				else if (pieceType.equals("Ki")) {
					inputString += "king";
					//draw king at position (r*50, c*50)
				}
				else if (pieceType.equals("Pa")) {
					inputString += "pawn";
					//draw pawn at position (r*50, c*50)
				}
			}
		}
		//write something below/above the board
	}

	/**
	* Sets up the graphics to seen on screen with javafx
	* Accepts Png images and displays them 
	* @param primaryStage: a Stage object that is used to set the scene
	*/
	public void start(Stage primaryStage) throws FileNotFoundException {
		//Image instance created, passing FileInputStream as parameter to the Image to load the image 
		Image boardImage = new Image(new FileInputStream("C:\\Users\\Shavonne\\Desktop\\Chessboard_brown.png")); //parameter is the image file path
		//file path to image depends on where you save board image
		
		//ImageView instance created, passing Image instance as parameter
		ImageView boardImageView = new ImageView(boardImage);
		
		//BorderPane to center the chess board image
		BorderPane pane = new BorderPane();
		pane.setCenter(boardImageView);
		
		Scene scene = new Scene(pane, 450, 500); //window size is 450 by 500 pixels
		primaryStage.setTitle("Chess Game"); //set title to stage
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	* java main method. It starts javafx and calls the start method
	*/
	public static void main(String[] args) {
		launch(args);
	}
}
