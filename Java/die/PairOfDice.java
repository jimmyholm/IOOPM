import java.util.Scanner;

public class PairOfDice {
  private Die die1;
  private Die die2;
  
  public PairOfDice () {
    die1 = new Die ();
    die2 = new Die ();
  }
  
  public PairOfDice (int Sides){
    die1 = new Die (Sides);
    die2 = new Die (Sides);
  }
  
  public int Roll (){
    return (die1.roll() + die2.roll());
  }
  
  public int GetDie1 (){
    return die1.get();
  }
  
  public int GetDie2 (){
    return die2.get();
  }
  
  public String toString (){
    return "PairOfDice (" + die1.get() +"," + die2.get() + ")";
  }

  public static void main (String [] args){
    Scanner sc = new Scanner(System.in);
    System.out.print("Number of sides ");
    PairOfDice pd = new PairOfDice(sc.nextInt());
    System.out.println("Alea iacta est " + pd.Roll() +pd);
  }
}
