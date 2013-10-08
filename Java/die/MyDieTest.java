import java.util.Scanner;

public class MyDieTest {
    public static void main(String [] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Number of sides: ");
        Die d = new Die(sc.nextInt());
        int valueOfTen = 0;
        for (int i = 0; i < 10; i++){
            valueOfTen += d.roll();
        }
        System.out.println("Alea iacta est: " + valueOfTen);
    }
}