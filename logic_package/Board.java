package logic_package;

import java.util.Arrays;

/**
* 2019-03-06
* Author: Dany
* Class creates the game board in a 2D array and sets intial piece positions
* Contains data regarding the game board and drawing this board
*/
public class Board {
    private String[][] boardPositions = new String[8][8];

    //Constructor
    public Board() {
        for (int i = 0; i < 8; i++) {
            for (int x = 0; x < 8; x++) {
                boardPositions[i][x] = "0";
            }
        }
    }

    /**
    * Getter method for the boardPositions array
    * @return boardPositions, the positions for the board in String[][] format
    */
    public String[][] getBoardPosition() {
        return this.boardPositions;
    }

    /**
    * Getter for the 
    * @param i, int: first index
    * @param x, int: second index
    * @return boardPositions[i][x], the positions for the board at index i and index x
    */
    public String getBoardPositionPieceInfo(int i, int x) {
        return this.boardPositions[i][x];
    }

    /**
    * Sets an index of the boardPositions varible to a particular value
    * @param i, int: first index
    * @param x, int: second index
    * @param value, String: value to change to
    */
    public void setBoardPositions(int i, int x, String value) {
        this.boardPositions[i][x] = value;
    }

    /**
    * Sets the defualt positions of all the pieces on the boardPositions array
    */
    public void defaultPositions() {
		for (int i = 0; i < 8; i++) {
			for (int x = 0; x < 8; x++) {
				setBoardPositions(i, x, "0");
			}
		}
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

    /**
    * Draws boardPositions to the screen according to the value each index contains
    */
	
    public void draw() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!boardPositions[i][j].equals("0")) {
                    System.out.print("|" + boardPositions[i][j]);
                }
                else {
                    System.out.print("|____");
                }
                if (j == 7) {
                    //System.out.println("| " + Integer.toString(8 - i));
		    System.out.println("| " + Integer.toString(i));
                }
            }
			if (i == 7) {
				//System.out.println("   A    B    C    D    E    F    G    H");
				System.out.println("   0    1    2    3    4    5    6    7");
			}
        }
    }
}
