import java.util.Random;

public class e1 {
    public static void main(String[] args) {
        Random random = new Random();

        int sum = 0;
        int max = -1, secMax = -1;
        int min = 101, secMin = 101;
        double average;

        for (int i = 0; i < 10; i++) {
            int number = random.nextInt(100) + 1;

            System.out.print(number);
            if (i < 9) System.out.print(", ");

            sum += number;

            if (number > max) {
                secMax = max; max = number;
            } else if (number > secMax) secMax = number;

            if (number < min) {
                secMin = min; min = number;
            } else if (number < secMin) secMin = number;
        }
        average = sum / 10.0;

        System.out.println();
        System.out.println("总和: " + sum);
        System.out.println("平均数: " + average);
        System.out.println("最大值: " + max);
        System.out.println("最小值: " + min);
        System.out.println("第二大的数: " + secMax);
        System.out.println("第二小的数: " + secMin);
    }
}
