import java.util.Scanner;

class Game{

  private HumanPlayer human1;
  private HumanPlayer human2;  // May go unused in a particular instance, where a human plays against a computer.
  private ComputerPlayer ai;
  private GameConfiguration config = new GameConfiguration();

  public void setup(){
    Scanner format = new Scanner(System.in);
    System.out.println("Would you like to play a [H]uman or [C]omputer?");
    String c = format.next();

    if (c == "H"){
      human1 = new HumanPlayer("White");
      human2 = new HumanPlayer("Black");
    }
    if (c == "C"){
      human1 = new HumanPlayer("White"); // Default's the human to the slightly more advantageous side, may change in the future.
      ai = new ComputerPlayer("Black", lastMovePlaceholder); // Unsure of the purpose of the instance variable of Move type upon initialisation.
    }

    Scanner m = new Scanner(System.in); // Scanner for getting input regarding move selection.
    int fR, fC, tR, tC, turnCounter;  // Always used, the first four are for move selections.
  }


  public void whitePlay(){
    System.out.println("What row is the piece you want to move in?");
    fR = m.nextInt();
    System.out.println("What column is the piece you want to move in?");
    fC = m.nextInt();
    System.out.println("What row would you like to move this piece to?");
    tR = m.nextInt();
    System.out.println("What column would you like to move this piece to?");
    tC = m.nextInt();

    if (config.isValidMove(new Move("White",fC,fR,tR,tC)))
      config.update("White",fC,fR,tR,tC);
    else{ 
      System.out.println("You have made an invalid move selection.");
      whitePlay(); // Recursive call never made if move is valid, avoids infinite loop.
      }
  }


  public void blackPlay(){
    System.out.println("What row is the piece you want to move in?");
    fR = m.nextInt();
    System.out.println("What column is the piece you want to move in?");
    fC = m.nextInt();
    System.out.println("What row would you like to move this piece to?");
    tR = m.nextInt();
    System.out.println("What column would you like to move this piece to?");
    tC = m.nextInt();

    if (config.isValidMove(new Move("Black",fC,fR,tR,tC)))
      config.update("Black",fC,fR,tR,tC);
    else{
      System.out.println("You have made an invalid move selection.");
      blackPlay(); // See above comment.
      }
  }


  public void play(){
    while (!hasWon("White") || !hasWon("Black")){
      if (turnCounter % 2 == 0)
        whitePlay();
      if ((turnCounter % 2 == 1) && (c == "H"))
       blackPlay();
      if ((turnCounter % 2 == 1) && (c == "C"))
       config.update(ai.getMove()); // Here I assume the ComputerPlayer cannot make invalid moves.
      // A method to check for draws/stalemates must be added to the GameConfiguration class, and incited here.
      }
  }
  public static void main(String[] args) {
    play(); // This is the class incited to run the project as a whole.
    }
}
