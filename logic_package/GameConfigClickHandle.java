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

public class GameConfigClickHandle implements EventHandler<MouseEvent> {
	private char pressedButton;
	private Stage primaryStage;
	private Scene scene;
	private GameConfiguration config;
	private Draw drawCopy;

	public GameConfigClickHandle(Stage primaryStage, Scene scene, GameConfiguration config, Draw drawCopy, char button) {
		pressedButton = button;
		this.primaryStage = primaryStage;
		this.scene = scene;
		this.config = config;
		this.drawCopy = drawCopy;
	}

	@Override
	public void handle(MouseEvent event) {
		if (pressedButton == 'g') {
			config.getBoard().defaultPositions();
			primaryStage.setScene(scene);
			config.resetPlayerTimes();
			config.takeTime();
			config.setWhiteTurn(true);
		}

		//MAIN MENU clickhandle
		 else if (pressedButton == 'b'){
			primaryStage.setScene(scene);
		}

		else if (pressedButton == 't') {
			primaryStage.close();
			TextGame textVersion = new TextGame();
			textVersion.playText();
		}

		//leaderboard clickhandle
		else if (pressedButton == 'h'){
			primaryStage.setScene(scene);
		}

		// Save to leaderboard click handle
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

		else if (pressedButton == 'n'){
			primaryStage.setScene(scene);
		}

		else if (pressedButton == 'l') {
			try {
				config.load();
				primaryStage.setScene(scene);
				drawCopy.clear();
				drawCopy.draw();
			}
			catch (IOException error) {
				System.out.println(error);
			}
		}
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
	}
}
