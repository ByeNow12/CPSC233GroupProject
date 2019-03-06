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
* 2019-03-05
* Author: Shavonne
* GUI class that displays the chess board and pieces of the game 
* Responds to mouse events to move chess pieces 
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
		
	}
	
	/**
	* updates the state of the game and GUI display based on user input
	*/
	public void update() {
		
	}
	
	/**
	* Sets up the graphics to seen on screen with javafx
	* Accepts Png images and displays them 
	* @param primaryStage: a Stage object that is used to set the scene
	*/
	public void start(Stage primaryStage) throws FileNotFoundException {
		//Image instance created, passing FileInputStream as parameter to the Image to load the image 
		Image boardImage = new Image(new FileInputStream("C:\\Users\\Shavonne\\Desktop\\Chessboard.png")); //parameter is the image file path
		//file path to image depends on where you save board image
		
		//ImageView instance created, passing Image instance as parameter
		ImageView boardImageView = new ImageView(boardImage);
		
		//BorderPane to center the chess board image
		BorderPane pane = new BorderPane();
		pane.setCenter(boardImageView);
		
		Scene scene = new Scene(pane, 450, 500); //window size is 450 pixels by 500 pixels
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
