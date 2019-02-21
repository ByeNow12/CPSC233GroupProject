public class Main {
	public static void main(String[] args) {
		boolean main_game_loop = true;
		UserInterface player1 = new UserInterface();
		UserInterface player2 = new UserInterface();
		UserInterface player3 = new UserInterface();
		Board b = new Board();
		b.defaultPositions();
		player1.welcomeMsg(true);
		player1.setUserName();
		player1.setUserColor();
		player1.setTeam();
		if (player1.getTeam() == 1) {
			if (player1.getUserColor() == ('w')) {
				player2.setColor('b');
			}
			else {
				player2.setColor('w');
			}
			System.out.println("The remaining color has been assigned to Player 2\nPlayer 2 please enter your name.");
			player2.setUserName();
		}
		else {
			player3.setName("Computer");
			if (player1.getUserColor() == ('w')) {
				player3.setColor('b');
			}
			else {
				player3.setColor('w');
			}
		}
		System.out.println(player1.getUserName() + " will go first.");
		while (main_game_loop) {
			b.draw();
			player1.move(player1.selectSpot(player1.getTeam(), player1.getUserName()));
			b.draw();
			if (player1.getTeam() == 1) {
				player2.move(player2.selectSpot(player2.getTeam(), player2.getUserName()));
			}
			else {
				player3.move(player3.selectSpot(player3.getTeam(), player3.getUserName()));
			}
			b.draw();
			main_game_loop = false;
		}
	}
}