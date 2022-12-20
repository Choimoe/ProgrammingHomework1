import java.io.*;
import java.util.Stack;

public class e3 {
    static Stack<Integer> numbers = new Stack<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("e3.txt"));

        while (true) {
            String line = reader.readLine();
            if (line.startsWith("#")) break;

            System.out.println(line + " = " + evaluate(line + " "));
        }
    }

    public static int evaluate(String expression) {
        int flag = 1, instantFlag = 0, num = 0;

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c >= '0' && c <= '9') num = num * 10 + c - '0';
            else {
                num = flag * num;
                if (instantFlag == 1) num = numbers.pop() * num;
                else if (instantFlag == 2) {
                    if (num == 0) {
                        System.out.println("Error: divide by zero");
                        throw new RuntimeException();
                    }
                    num = numbers.pop() / num;
                }

                numbers.push(num);
                num = 0;

                switch (c) {
                    case '+' -> {flag =  1; instantFlag = 0;}
                    case '-' -> {flag = -1; instantFlag = 0;}
                    case '*' -> {flag =  1; instantFlag = 1;}
                    case '/' -> {flag =  1; instantFlag = 2;}
                }
            }
        }

        int result = 0;
        while (!numbers.isEmpty()) result += numbers.pop();

        return result;
    }
}
