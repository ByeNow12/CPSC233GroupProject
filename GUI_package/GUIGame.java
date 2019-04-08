package GUI_package;//import packages
import javafx.application.Application;
import javafx.stage.Stage;
import logic_package.*;

import java.io.FileNotFoundException;

/**
* 2019-03-20
* Author: Shavonne, Carmen
* GUI class, the main class that displays the chess board and pieces of the game for the GUI version
* Updates the board display after each move 
*/
public class GUIGame extends Application{
	
	private ComputerPlayer ai;
	private GameConfiguration config;
	private Draw draw;

	/**
	 * Constructor for GUIGame, creates new GameConfiguration and Draw classes
	 * Passes config into Draw class
	 */
	public GUIGame (){
		config = new GameConfiguration();
		draw = new Draw(config);
	}
	
	/**
	* java main method. It starts javafx and calls the start method
	*/
	public static void main(String[] args) {
		launch(args); //Start of program. Call constructor and creates new GUIGame, which runs start method
	}

	// all programs will happen here
	public void start(Stage primaryStage) throws FileNotFoundException {
		draw.start(primaryStage);
	}
}
