package logic_package;
import logic_package.GameConfiguration;
import logic_package.Board;
import text_package.*;
import GUI_package.Draw;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;


/**
* The event handling class for pressing buttons 
*/
public class GameConfigClickHandle implements EventHandler<MouseEvent> {
	private char pressedButton;
	private Stage primaryStage;
	private Scene scene;
	private GameConfiguration config;
	private Draw drawCopy;

	/**
	* Constructor that sets the parameters needed to handle button clicks
	* @param primaryStage, the Stage used to set the scene
	* @param scene, the scene to be set on the Stage
	* @param config, contains data about state of the game
	* @param drawCopy, a copy of an object of Draw class
	* @param button, a character that represents which button was clicked 
	*/
	public GameConfigClickHandle(Stage primaryStage, Scene scene, GameConfiguration config, Draw drawCopy, char button) {
		pressedButton = button;
		this.primaryStage = primaryStage;
		this.scene = scene;
		this.config = config;
		this.drawCopy = drawCopy;
	}

	/**
	* Handles the button clicks for each button in the game and menus
	* @param event, the mouse event to be handled
	*/
	@Override
	public void handle(MouseEvent event) {
		//Click handle for clicking the buttons for playing chess GUI version, for new game (vs. human or computer), and play again
		if (pressedButton == 'g') {
			if (config.getActiveAI()) {
				config.getBoard().defaultPositions();
				primaryStage.setScene(scene);
				config.resetPlayerTimes();
				config.takeTime();
				config.setWhiteTurn(true);
				drawCopy.setTeamText();
			}
			else {
				config.getBoard().defaultPositions();
				config.setWhiteTurn(true);
				drawCopy.setTeamText();
				primaryStage.setScene(scene);
			}
		}

		//MAIN MENU click handle
		 else if (pressedButton == 'b'){
			primaryStage.setScene(scene);
		}

		//Click handle for clicking the button for playing chess text version
		else if (pressedButton == 't') {
			primaryStage.close();
			TextGame textVersion = new TextGame();
			textVersion.playText();
		}

		//Click handle for clicking the button for viewing the leaderboard
		else if (pressedButton == 'h'){
			primaryStage.setScene(scene);
		}

		//Click handle for clicking the button Save to leaderboard click handle
		else if (pressedButton == 'z'){
			String name = config.getEnterPlayerName().getText();
			config.getLeaderboard().setName(name);
			config.getLeaderboard().setScore(config.getWinningTime());
			try {
				config.getLeaderboard().save();
				config.getLeaderboard().refreshLeaderboard();
				config.updateNamesAndScores();
			} catch (Exception e) {
				System.out.println("Problems saving score to file");
			}
			primaryStage.setScene(scene);
		}

		//Click handle for clicking the button for save score to scoreboard
		else if (pressedButton == 'n'){
			primaryStage.setScene(scene);
		}

		//Click handle for clicking the button for load game from save
		else if (pressedButton == 'l') {
			try {
				config.load();
				//Update turn text and isCheck text for loaded saves
				drawCopy.setTeamText();
				if (config.isCheck('w')) {
					drawCopy.setCheckText("White is in Check");
				}
				else if (config.isCheck('b')) {
					drawCopy.setCheckText("Black is in Check");
				}
				else {
					drawCopy.setCheckText("");
				}
				primaryStage.setScene(scene);
				drawCopy.clear();
				drawCopy.draw();
			}
			catch (IOException error) {
				System.out.println(error);
			}
		}
		
		//Click handle for clicking the button for save and quit
		else if (pressedButton == 's') {
			try {
				config.save();
				primaryStage.setScene(scene);
				System.exit(0);
			}
			catch (IOException error) {
				System.out.println(error);
			}
		}
		// What is done to play with computer.
		else if (pressedButton == 'c') {
			config.getBoard().defaultPositions();
			config.setWhiteTurn(true);
			drawCopy.setTeamText();
			primaryStage.setScene(scene);
			config.setActiveAI(true);
		}
	}
}
