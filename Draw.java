import javafx.application.Application;

/**
* Draw class
*/
public class Draw {
	private StackPane wrappingPane;
	
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
			//error.printStackTrace();
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
			System.out.println("Blue File not Found");
		}
	}
	
	/**
	* Highlights the moves that can be made
	*/
	public void highlightMoves(int[] pos, char team, String pieceType){
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
			error.printStackTrace();
		}
	}
}
