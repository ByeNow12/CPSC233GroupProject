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
		wrap.setOnMouseClicked(new ClickHandle(config, 'w', wrap, eventPane));
		primaryStage.setTitle("Chess Game"); //set title to stage
		primaryStage.setScene(scene);
		primaryStage.show();
		ClickHandle initDraw = new ClickHandle(config, 'w', wrap, eventPane);
		initDraw.draw(config.getBoard().getBoardPosition());
	}
	
	/**
	* java main method. It starts javafx and calls the start method
	*/
	public static void main(String[] args) {
		launch(args);
	}
}