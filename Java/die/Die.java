import java.util.Scanner;

public class Die {
    private int numberOfSides;
    private int value;

    public Die() { 
        numberOfSides = 6;
        value = -1; 
    }
    
    public Die(int _numberOfSides) { 
        if (_numberOfSides >= 2){
            numberOfSides = _numberOfSides;
        }
        else 
            numberOfSides = 6;
        value = -1;
    }

    public int roll() {
        return value =  (int) (Math.random()*numberOfSides) + 1;
    }
    
    public boolean equals (Die otherDie){
        if (value == otherDie.value)
            return true;
        return false;
    }
    
    public int get() { 
        return value; 
    }
    
    public String toString (){
        return "die(" + value + ")";
    }

    public static void main(String [] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Number of sides: ");
        Die d = new Die(sc.nextInt());
        System.out.println("Alea iacta est: " + d.roll());
        System.out.println(d);
    } 
}
