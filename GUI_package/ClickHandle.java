package GUI_package;

//import needed classes from logic package
import logic_package.GameConfiguration;
import logic_package.Board;
import logic_package.Move;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * 2019-03-20
 * Author: Dany, Carmen
 * The event handling class. This class handle all mouse events
 */
public class ClickHandle implements EventHandler<MouseEvent> {
	private int[] pieceSelected = new int[] {10, 10};
	private GameConfiguration config;
	private char team;
	private Draw drawGame;
	private Stage primaryStage;
	private Scene scene;

	public ClickHandle(Draw drawGame, GameConfiguration config, Stage primaryStage, Scene scene, char team){
		this.drawGame = drawGame;
		this.config = config;
		this.team = team;
		this.primaryStage = primaryStage;
		this.scene = scene;
	}

	@Override
	public void handle(MouseEvent event){
		Board board = config.getBoard();
		int width = 400;
		int height = 400;
		double x = event.getX();
		double y = event.getY();
		int[] position = new int[2];

		for (int i = 0; i < width; i+=(width/ 8)) {
			if (x > i && x < i + (width / 8)) {
				position[1] = (i / (width / 8));
			}
		}

		for (int i = 0; i < height; i+=(height / 8)) {
			if (y > i && y < i + (height / 8)) {
				position[0] = (i / (height / 8));
			}
		}

		String pieceId = board.getBoardPosition()[position[0]][position[1]];
		String pieceType = "";
		if (!pieceId.equals("0")) {
			pieceType = pieceId.substring(2);
		}
		char team = pieceId.charAt(0);

		if (pieceSelected[0] == 10 && pieceSelected[1] == 10) {
			drawGame.draw();
			drawGame.highlightSelectedSquare(position);
			drawGame.highlightMoves(position, team, pieceType);
			pieceSelected[0] = position[0];
			pieceSelected[1] = position[1];
		}
		else {
			String color;
			if (team == 'w') {
				color = "White";
			}
			else {
				color = "Black";
			}
			Move move = new Move(color, pieceSelected[0], pieceSelected[1], position[0], position[1]);
			if (config.isValidMove(move)) {
				//update board. Display an error message of some kind. Perhaps a red square.
				board.setBoardPositions(position[0], position[1], board.getBoardPosition()[pieceSelected[0]][pieceSelected[1]]);
				board.setBoardPositions(pieceSelected[0], pieceSelected[1], "0");
				//board.draw();
				if (config.hasWon('w') || config.hasWon('b')) {
					if (config.hasWon('w')) {
						drawGame.setEndText("You Won!");
						scene = drawGame.buildEndMenuScene();
					}
					else {
						drawGame.setEndText("You Lost!");
						scene = drawGame.buildEndMenuScene();
					}
					config.getBoard().defaultPositions();
					primaryStage.setScene(scene);
					drawGame.draw();
				}
			}
			else {
				// Not a valid move.
				/*if (position[0] != pieceSelected[0] || position[1] != pieceSelected[1]) {
					if (board.getBoardPosition()[pieceSelected[0]][pieceSelected[1]] != "0") {
						board.setBoardPositions(position[0], position[1], board.getBoardPosition()[pieceSelected[0]][pieceSelected[1]]);
						board.setBoardPositions(pieceSelected[0], pieceSelected[1], "0");
						//board.draw();
						if (config.hasWon('w') || config.hasWon('b')) {
							if (config.hasWon('w')) {
								drawGame.setEndText("White Won!");
								scene = drawGame.buildEndMenuScene();
							}
							else {
								drawGame.setEndText("Black Won");
								scene = drawGame.buildEndMenuScene();
							}
							config.getBoard().defaultPositions();
							primaryStage.setScene(scene);
							drawGame.draw();
						}
					}
				}*/
			}
			pieceSelected[0] = 10;
			pieceSelected[1] = 10;
			drawGame.clear();
			drawGame.draw();
		}
	}
}
