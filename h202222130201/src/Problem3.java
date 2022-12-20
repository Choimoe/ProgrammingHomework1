import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import static java.util.Arrays.sort;

public class Problem3 {
    public static int[] splitInput (String input) {
        StringBuilder splitRegex = new StringBuilder();
        int oriSize = input.length();
        for (int i = 0; i < oriSize; i++) {
            if (input.charAt(i) <= '9' && input.charAt(i) >= '0') continue;
            while(input.charAt(i) > '9' || input.charAt(i) < '0') {
                splitRegex.append(input.charAt(i));
                i++;
            }
            break;
        }

        String[] split = input.split(splitRegex.toString());

        int size = split.length;
        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = Integer.parseInt(split[i]);
        }

        return result;
    }

    public static int[] sortNumbers (int[] numbers) {
        int[] result = numbers.clone();

        sort(result);

        return result;
    }

    public static int getAverage(int[] numbers) {
        int result = 0;

        for (int x : numbers) {
            result += x;
        }

        return result / numbers.length;
    }

    public static String toString (int[] numbers) {
        StringBuilder result = new StringBuilder();

        for (int x : numbers) {
            result.append(x);
            result.append(",");
        }

        result.deleteCharAt(result.length() - 1);

        return result.toString();
    }

    public static class PostorderSequence {
        int[] sequence, result;
        int size, count;

        void postorder (int root) {
            if (root > size) return;
            postorder(root << 1);
            postorder(root << 1 | 1);
            result[++count - 1] = sequence[root - 1];
        }

        public PostorderSequence(int[] sequence) {
            this.sequence = sequence;
            size = sequence.length;

            result = new int[size];
            count = 0;
        }

        public int[] output() {
            return result;
        }
    }

    public static boolean process(BufferedReader reader) throws IOException {
        String inputSource = reader.readLine();
        if (inputSource == null) return false;

        int[] numbers = splitInput(inputSource);
        int average = getAverage(numbers);

        int[] sortedNumbers = sortNumbers(numbers);
        String outputSortedNumbers = toString(sortedNumbers);
        PostorderSequence postorder = new PostorderSequence(numbers);
        postorder.postorder(1);
        int[] postorderNumbers = postorder.output();
        String outputPostorder = toString(postorderNumbers);

        System.out.println(average + "#" + outputSortedNumbers + "#" + outputPostorder);

        return true;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("inputdata/ex3.txt"));
        while(process(reader));
    }
}
