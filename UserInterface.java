import java.util.Scanner;

public class UserInterface {
	private Scanner input = new Scanner(System.in);
	private String userName;
	private char userColor;
	
	public UserInterface() {
		userName = "Default name";
		userColor = 'w';
	}
	
	public UserInterface(String name) {
		userName = name;
		userColor = 'w';
	}
	
	public UserInterface(String name, char color) {
		userName = name;
		userColor = color;
	}
	
	public void setUserName() {
		System.out.print("Please enter your name: ");
		userName = input.nextLine();
		System.out.println("Thanks " + userName + ". Your name has been recorded as belonging to the user.");
	}
	
	public void setUserColor() {
		System.out.println("Please choose the color you will play as.\n\n'w' = white\n'b' = black");
		String userInput = input.nextLine();
		while ((!userInput.equals("w") && !userInput.equals("b"))) {
			System.out.println("Invalid Input");
			userInput = input.nextLine();
		}
		userColor = userInput.charAt(0);
	}
	
	public String getUserName() {
		return userName;
	}
	
	public char getUserColor() {
		return userColor;
	}
	
	public void welcomeMsg(boolean show_board) {
		System.out.println("Welcome to T09-G03's First Chess Demo!");
		if (show_board) {
			System.out.println("This is what the Board looks like now.\n");
			Board b = new Board();
			b.defaultPositions();
			b.draw();
		}
		System.out.println("\nGood Luck!");
	}
	
	public String moveInput() {
		char[] accep_lett = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
		String userInput = input.nextLine();
		boolean accep_lett_check = false;
		boolean accep_num_check = false;
		
		if (userInput.length() == 2) {
			if (Character.isLetter(userInput.charAt(0))) {
				for (char lett : accep_lett) {				
					if(lett == (userInput.charAt(0)) || Character.toUpperCase(lett) == userInput.charAt(0)) {
						accep_lett_check = true;
					}
				}
			}
			if (Character.isDigit(userInput.charAt(1))) {
				if ((Character.getNumericValue(userInput.charAt(1)) >= 1) && Character.getNumericValue(userInput.charAt(1)) <= 8) {
					accep_num_check = true;
				}
			}
		}
		if (!accep_lett_check || !accep_num_check) {
			System.out.println("Invalid Input. The following are the requirements for move inputs.");
			System.out.println("\nThe input must be only two characters long.");
			System.out.println("The first character must be a letter between A and H. Lower case letters are also accepted.");
			System.out.println("The second character must be a number between 1 and 8.");
			return moveInput();
		}
		else {
			return userInput;
		}
	}
}