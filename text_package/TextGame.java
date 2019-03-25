package text_package;

import logic_package.*; //import the logic package to use GameConfiguration, ComputerPlayers class
import GUI_package.*;

import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
* 2019-03-06
* Author: Tom
* TextGame class allows users to play the game in the text based version
*/
public class TextGame{

  private HumanPlayer human1;
  private HumanPlayer human2;  // May go unused in a particular instance, where a human plays against a computer.
  private ComputerPlayer ai;
  private GameConfiguration config;
  private Scanner m = new Scanner(System.in); // Scanner for getting input regarding move selection.
  private int fR, fC, tR, tC, turnCounter;  // Always used, the first four are for move selections.
  private String c;

  /** 
  * Establishes the configuration of the game, prompts the user to decide whether they want to play against a human or computer
  */
  public void setup() throws IOException {
    config = new GameConfiguration();
    Scanner format = new Scanner(System.in);
    System.out.println("Would you like to play a [H]uman or [C]omputer?");
    c = format.next();

    if (c.equals("H")){
      human1 = new HumanPlayer("White");
      human2 = new HumanPlayer("Black");
    }
    if (c.equals("C")){
      human1 = new HumanPlayer("White"); // Default's the human to the slightly more advantageous side, may change in the future.
      ai = new ComputerPlayer("Black"); // Unsure of the purpose of the instance variable of Move type upon initialisation.
    }
  }
  
  /** 
  * Contains the prompt for what move White will make, recurses if an invalid move is made
  */
  public void whitePlay(){
    System.out.println("For the player on the white team, what row is the piece you want to move in?");
    fR = m.nextInt();
    System.out.println("For the player on the white team, what column is the piece you want to move in?");
    fC = m.nextInt();
    System.out.println("For the player on the white team, what row would you like to move this piece to?");
    tR = m.nextInt();
    System.out.println("For the player on the white team, what column would you like to move this piece to?");
    tC = m.nextInt();

    Move move = new Move("White",fR,fC,tR,tC);

    if (config.isValidMove(move))
      config.update(move);
    else{
      System.out.println("You have made an invalid move selection.");
      whitePlay(); // Recursive call never made if move is valid, avoids infinite loop.
      }
  }
  
  /** 
  * Contains the prompt for what move Black will make, recurses if an invalid move is made
  */
  public void blackPlay(){
    System.out.println("For the player on the black team, what row is the piece you want to move in?");
    fR = m.nextInt();
    System.out.println("For the player on the black team, what column is the piece you want to move in?");
    fC = m.nextInt();
    System.out.println("For the player on the black team, what row would you like to move this piece to?");
    tR = m.nextInt();
    System.out.println("For the player on the black team, what column would you like to move this piece to?");
    tC = m.nextInt();

    Move move = new Move("Black",fR,fC,tR,tC);

    if (config.isValidMove(move))
      config.update(move);
    else{
      System.out.println("You have made an invalid move selection.");
      blackPlay(); // See above comment.
      }
  }

  /** 
  * The main game loop, runs whilst the game isn't won, and alternates between players
  */
  public void play() {
    int turnCounter = 0;
    while (!config.hasWon('w') && !config.hasWon('b')) {
      config.getBoard().draw();
      if (turnCounter % 2 == 0)
        whitePlay();
      if ((turnCounter % 2 == 1) && (c.equals("H")))
       blackPlay();
      if ((turnCounter % 2 == 1) && (c.equals("C"))) {
       config.update(ai.getMove(config)); // Here I assume the ComputerPlayer cannot make invalid moves.
       System.out.println("The computer has made a move. It is your turn to make a move."); //tells you computer made a move
      }
      // A method to check for draws/stalemates must be added to the GameConfiguration class, and incited here.
      turnCounter++;
      }
  }
  
  public void playGUI() throws FileNotFoundException {
    }
  
  /** 
  * Sets up and runs the game utilising the previously defined methods
  */
  public static void main(String[] args) throws IOException {
    String t;
    Scanner type = new Scanner(System.in);
    System.out.println("Would you like to play a [T]ext-based implementation or a [G]raphical implemetation");
    t = type.next();
    
    if (t.equals("T")) {
      TextGame g = new TextGame();
      g.setup();
      g.play(); // This is the method incited to run the project as a whole.
    }
    if (t.equals("G")) {
      TextGame g = new TextGame();
      g.setup();
      g.playGUI(); // This is the method incited to run the project as a whole.
    }
    }
}
