import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.Math.abs;

public class e2 {
    static char[] charSet = {' ', '*'};

    static char rendering(int posX, int posY, int param) {
        return charSet[abs(posX) + abs(posY) == param ? 1 : 0];
    }

    public static void main(String[] args) {
        int len = read("e2.txt") - 1; // example: 5
        for (int posX = -len; posX <= len; posX++) {
            for (int posY = -len; posY <= len; posY++) {
                System.out.print(rendering(posX, posY, len));
            }
            System.out.println();
        }
    }

    public static int read(String fileName) {
        String line = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            line = reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading file: " + fileName);
            e.printStackTrace();
        }
        return Integer.parseInt(line);
    }
}
