import java.util.Arrays;

public class Board {
    private String[][] boardPositions = new String[8][8];

    public Board() {
        for (int i = 0; i < 8; i++) {
            for (int x = 0; x < 8; x++) {
                boardPositions[i][x] = "0";
            }
        }
    }

    public String[][] getBoardPosition() {
        return this.boardPositions;
    }

    public void setBoardPositions(int i, int x, String value) {
        this.boardPositions[i][x] = value;
    }

    public void defaultPositions() {
        String[] pieces = {"w_Ro", "w_Kn", "w_Bi", "b_Ro", "b_Kn", "b_Bi"};
        for (int i = 0; i < 8; i++) {
            boardPositions[1][i] = "w_Pa";
        }
        for (int i = 0; i < 8; i++) {
            boardPositions[6][i] = "b_Pa";
        }
        for (int i = 0; i < 3; i++) {
            boardPositions[0][i] = pieces[i];
            boardPositions[7][i] = pieces[i + 3];
            boardPositions[0][7 - i] = pieces[i];
            boardPositions[7][7 - i] = pieces[i + 3];
        }
        boardPositions[0][4] = "w_Ki";
        boardPositions[0][3] = "w_Qu";
        boardPositions[7][4] = "b_Ki";
        boardPositions[7][3] = "b_Qu";
    }

    public void draw() {
        for (int i = 0; i < 8; i++) {
            for (int x = 0; x < 8; x++) {
                if (boardPositions[i][x] != "0") {
                    System.out.print("|" + boardPositions[i][x]);
                }
                else {
                    System.out.print("|____");
                }
                if (x == 7) {
                    System.out.println("| " + Integer.toString(8 - i));
                }
            }
			if (i == 7) {
				System.out.println("   A    B    C    D    E    F    G    H");
			}
        }
    }
}