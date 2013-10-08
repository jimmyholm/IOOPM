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

    public int RollPairOfDies (){
        return (die1.roll() + die2.roll());
    }

    public int GetDie1 (){
retur