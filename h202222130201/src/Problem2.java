import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Math.sqrt;

public class Problem2 {
    public static String binaryTransform(Integer x) {
        StringBuilder result = new StringBuilder();
        while (x > 0) {
            result.append(x & 1);
            x /= 2;
        }
        result.reverse();
        return result.toString();
    }

    public static Object[] getPrimeFactors(Integer x) {
        ArrayList<Integer> list = new ArrayList<>();

        int sqrtX = (int)Math.sqrt(x) + 1;

        for (int i = 2; i <= sqrtX; i++) {
            while (x % i == 0) {
                x = x / i;
                list.add(i);
            }
        }

        if (x != 0) list.add(x);

        return list.toArray();
    }

    public static String primeFactors(Object[] factors) {
        StringBuilder result = new StringBuilder();
        for (Object x: factors) {
            result.append(x);
            result.append(',');
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    public static boolean process(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        if (line == null) return false;

        int x = Integer.parseInt(line);

        String toBinary = binaryTransform(x);
        String factors = primeFactors(getPrimeFactors(x));

        System.out.println(toBinary + "#" + factors);

        return true;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("inputdata/ex2.txt"));
        /* ex2.txt:
            1234
            10034
            1100104
         */
        while(process(reader));
    }
}
