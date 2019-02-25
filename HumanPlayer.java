import java.util.Scanner;

public class HumanPlayer {

	private String token;
	
	public HumanPlayer(String token){
		this.token = token;
	}
	
	/** Retrieves move from the user playing the game. Prompts the user to enter the number for the row and column that their piece is moving from, to the row and column that their piece is moving to.
	* If user enters invalid move, the method will loop from the beginning until a valid move is entered.
	* An invalid move is when the piece is moved in a way that violates rules of chess, or if the player tries to move the piece to the same location that it is already in.
	* @param gameConfiguration, the current status of the game 
	* @return currentMove, the move that the user has selected to move a piece
	*/
	public Move getMove(GameConfiguration gameConfiguration){
		Scanner userInput = new Scanner(System.in);
		boolean moveIsValid = false;
		Move currentMove = null;
		while(!moveIsValid){
		
			int fromRow;
			// prompts user for row to move piece from. reprompts if row is not valid
			System.out.print("Enter the row number of the piece you want to move: ");
			fromRow = userInput.nextInt();
			while (fromRow < 0 || fromRow > 7){
				System.out.print("Invalid row number. Enter the row number of the piece you want to move: ");
				fromRow = userInput.nextInt();
			}
			
			// prompts user for column to move piece from. reprompts if row is not valid
			System.out.print("Enter the column number of the piece you want to move: ");
			int fromCol = userInput.nextInt();
			while (fromCol < 0 || fromCol > 7){
				System.out.print("Invalid column number. Enter the column number of the piece you want to move: ");
				fromCol = userInput.nextInt();
			}

			
			// prompts user for row to move piece to. reprompts if row is not valid
			System.out.print("Enter the row number of where you want to move the piece: ");
			int toRow = userInput.nextInt();
			while (toRow < 0 || toRow > 7){
				System.out.print("Invalid row number. Enter the row number of where you want to move the piece: ");
				toRow = userInput.nextInt();
			}
			
			
			// prompts user for column to move piece to. reprompts if row is not valid
			System.out.print("Enter the column number of where you want to move the piece: ");
			int toCol = userInput.nextInt();
			while (toCol < 0 || toCol > 7){
				System.out.print("Invalid column number. Enter the column number of where you want to move the piece: ");
				toCol = userInput.nextInt();
			}

			//checks if to row/col is same as from row/col. if the same, reprompts user to start over. if they're different, breaks loop
			if ((fromRow==toRow) && (fromCol==toCol)){
				System.out.print("You must move a piece to a different position. Please start over. ");
				continue;
			}else{
				moveIsValid = true;
			}
	
			// checks if move is valid. if not valid, prompts user to start over. if valid, breaks loop
			if (gameConfiguration.isValidMove(currentMove)){
				moveIsValid = true;
			}else{
				System.out.println("Not a valid move. Please start over. ");
				moveIsValid = false;
				continue;
			}

		currentMove = new Move(token,fromRow,fromCol,toRow,toCol);		
		}
		
		
		return currentMove;
	}

		
	public static void main(String[] args) {
		HumanPlayer alex = new HumanPlayer("white");
		GameConfiguration blankConfig = new GameConfiguration();
		System.out.println(alex.getMove(blankConfig));
		
	}
}
	
	
