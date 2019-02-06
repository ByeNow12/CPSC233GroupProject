import java.util.Arrays;

public class Board {
    private String[][] boardPositions = new String[8][8];

    public Board() {
    }

    public String[][] getBoardPosition() {
        return this.boardPositions;
    }

    public void defaultPositions() {
        for (int i = 0; i < 8; i++) {
            boardPositions[1][i] = "w_Pa";
        }
        for (int i = 0; i < 8; i++) {
            boardPositions[7][i] = "b_Pa";
        }

    }

    public static void main(String[] args) {
        Board b = new Board();
        b.defaultPositions();
        System.out.println(Arrays.deepToString(b.getBoardPosition()));
    }

}
