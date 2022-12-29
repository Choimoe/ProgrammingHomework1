public class ex2 {
    static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void arrange_print(int[] arr, int st) {
        if (st + 1 == arr.length) {
            for (int j : arr) System.out.print(j + " ");
            System.out.println();
            return;
        }
        for (int i = st; i < arr.length; i++) {
            swap(arr, st, i);

            arrange_print(arr, st + 1);

            swap(arr, st, i);
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 4};
        arrange_print(arr, 0);
    }
}