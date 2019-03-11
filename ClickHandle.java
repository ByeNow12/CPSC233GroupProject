import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage; 
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ClickHandle implements EventHandler<MouseEvent> {
    private int[] pieceSelected = new int[] {10, 10};
    private GameConfiguration config;
    private char team;
	private StackPane wrap;
	private Pane eventPane;

    public ClickHandle(GameConfiguration config, char team, StackPane wrap, Pane eventPane) {
        this.config = config;
        //this.team = team;
		this.wrap = wrap;
		this.eventPane = eventPane;
    }
	
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
	
	public void draw(String[][] board){
		//clear board
		//put board back (the checkerboard) if necessary
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
	
	public void highlightMoves(int[] pos, char team, String pieceType, String[][] boardPositions) {
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
	
    @Override
    public void handle(MouseEvent event) {
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
			draw(board.getBoardPosition());
			highlightSelectedSquare(position);
			highlightMoves(position, team, pieceType, board.getBoardPosition());
			pieceSelected[0] = position[0];
			pieceSelected[1] = position[1];
		}
		else {
			String color;
			if (team == 'w') {
				color = "white";
			}
			else {
				color = "black";
			}
			Move move = new Move(color, position[0], position[1], pieceSelected[0], pieceSelected[1]);
			if (config.isValidMove(move)) {
				//update board. Display an error message of some kind. Perhaps a red square.
				board.setBoardPositions(position[0], position[1], board.getBoardPosition()[pieceSelected[0]][pieceSelected[1]]);
				board.setBoardPositions(pieceSelected[0], pieceSelected[1], "0");
				board.draw();
				if (config.hasWon('w') || config.hasWon('b')) {
					System.exit(0);
				}
			}
			else {
				// Not a valid move.
				if (position[0] != pieceSelected[0] || position[1] != pieceSelected[1]) {
					if (board.getBoardPosition()[pieceSelected[0]][pieceSelected[1]] != "0") {
						board.setBoardPositions(position[0], position[1], board.getBoardPosition()[pieceSelected[0]][pieceSelected[1]]);
						board.setBoardPositions(pieceSelected[0], pieceSelected[1], "0");
						board.draw();
						if (config.hasWon('w') || config.hasWon('b')) {
							System.exit(0);
						}
					}
				}
			}
			pieceSelected[0] = 10;
			pieceSelected[1] = 10;
			eventPane.getChildren().clear();
			draw(board.getBoardPosition());
		}
    }

    public static void main(String[] args) {
        GUIGame n = new GUIGame();
        n.main(args);
    } 

}