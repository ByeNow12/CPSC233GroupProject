import javafx.application.Application;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image; 
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

/**
* 2019-03-06
* Author: Riley, Shavonne
* GUI class that displays the chess board and pieces of the game 
* Updates the board display after each move 
*/
public class GUIGame extends Application {
	
	private ComputerPlayer ai;
	private GameConfiguration config;
	private boolean gameOver;
	private Button[][] boardButtons;
	private FlowPane flow;
	
	/**
	* initializes the positions of the chess pieces on the board before the game starts
	*/
	public void initialize() throws FileNotFoundException {
		config.getBoard().defaultPositions();
		//do more stuff too probably
		draw();
	}
	
	/**
	* updates the state of the game and GUI display based on user input
	*/
	public void update() throws FileNotFoundException {
		draw();//at the end
	}
	
	public void draw() throws FileNotFoundException{
		String[][] board = config.getBoard().getBoardPosition();
		
		//clear board
		flow.getChildren().clear();

		//place pieces on board
		for (int r = 0; r < 8; r++){
			HBox row = new HBox();
			for (int c = 0; c < 8; c++){

				String inputString;
				if (board[r][c].charAt(0) == 'w'){
					inputString = "w_";
				}
				else{
					inputString = "b_";   //this is the only thing that has to be changed to put in the outline ones instead
				}

				String pieceType = board[r][c].substring(2);
				if (pieceType.equals("Ro")) {
					inputString += "rook";
				}
				else if (pieceType.equals("Kn")) {
					inputString += "knight";
				}
				else if (pieceType.equals("Bi")) {
					inputString += "bishop";
				}
				else if (pieceType.equals("Qu")) {
					inputString += "queen";
				}
				else if (pieceType.equals("Ki")) {
					inputString += "king";
				}
				else if (pieceType.equals("Pa")) {
					inputString += "pawn";
				}
				else{
					Rectangle rect = new Rectangle(0,0,50,50);    //makes an empty transparent rectangle, this may be a problem area
					rect.opacityProperty().set(0.0);
					row.getChildren().add(rect);
					continue;
				}
				Image pieceImage = new Image(new FileInputStream(inputString));
				ImageView pieceImageView = new ImageView(pieceImage);
				row.getChildren().add(pieceImageView);
			}
			flow.getChildren().add(row);
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
		Image boardImage = new Image(new FileInputStream("Chessboard_brown.png")); //parameter is the image file path
		//file path to image depends on where you save board image
		
		
		//ImageView instance created, passing Image instance as parameter
		//ImageView boardImageView = new ImageView(boardImage);

		BackgroundImage bi= new BackgroundImage(boardImage,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Background background = new Background(bi);
		
		//BorderPane to center the chess board image
		//BorderPane pane = new BorderPane();              
		//pane.setCenter(boardImageView);
		flow = new FlowPane();
		flow.setBackground(background);  
		
		Scene scene = new Scene(flow, 450, 500); //window size is 450 by 500 pixels
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
