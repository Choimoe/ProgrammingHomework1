import java.util.Random;

public class ex1 {
    public static void main(String[] args) {
        Random random = new Random();

        int[] counts = new int[10];

        for (int i = 0; i < 10000; i++) {
            int n = random.nextInt(10);
            System.out.print(n + (i == 9999 ? "\n" : ", "));
            counts[n]++;
        }

        for (int i = 0; i < 10; i++) {
            System.out.println(i + ": " + counts[i]);
        }
    }
}