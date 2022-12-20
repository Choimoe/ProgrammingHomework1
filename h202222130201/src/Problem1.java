import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class FormatOutput {
    public final int ROW = 3, COL = 4;
    OutputUnit[] output;

    public boolean isSpec(int year) {
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }

    // w=y+[y/4]+[c/4]-2c+[26(m+1）/10]+d-1
    public int getDay(int year, int month) {
        int day = 1;
        if (month == 1) { month = 13; year = year - 1; }
        if (month == 2) { month = 14; year = year - 1; }
        return ((day + (26 * (month + 1) / 10) + (year % 100) + ((year % 100) / 4) + ((year / 100) / 4) + 5 * (year / 100)) % 7 + 6) % 7;
    }

    public FormatOutput(int year) {
        output = new OutputUnit[12];
        for (int i = 1; i <= 12; i++) {
            OutputUnit obj = new OutputUnit(i, i == 4 && isSpec(year) ? 1 : 0);
            obj.setDay(getDay(year, i));
            output[i - 1] = obj;
        }
    }

    public void formatPrint() {
        for (int i = 0; i < ROW; i++) {
            String[] lines = new String[8];
            for (int k = 0; k < 8; k++) {
                lines[k] = "";
            }
            for (int j = 0; j < COL; j++) {
                for (int k = 0; k < 8; k++) {
                    lines[k] += "| " + output[COL * i + j].line[k];
                }
            }
            for (int k = 0; k < 8; k++) {
                System.out.println(lines[k] + " |");
            }
        }
    }
}

class OutputUnit {
    final List<String> MONTH_LIST = Arrays.asList(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    );
    List<Integer> MONTH_DAYS = Arrays.asList(
            31,    28,    31,    30,    31,    30,
            31,    31,    30,    31,    30,    31
    );
    String[] line;
    int month;

    public OutputUnit(int month, int add) {
        line = new String[8];
        this.month = month;
        line[0] = "--- --- --- " + MONTH_LIST.get(month - 1) + " --- --- --- ";
        line[1] = "Sun Mon Tue Wed Thu Fri Sat ";
        if (add == 1) {
            MONTH_DAYS.set(1, 29);
        }
    }

    public void fillLine(int index, int firstDay) {
        int lim = MONTH_DAYS.get(month - 1);
        line[index] = "";
        for (int i = 0; i < 7; i++) {
            int cur = firstDay + i;

            if (cur <= 0 || cur > lim) {
                line[index] += "    ";
                continue;
            }

            int len = String.valueOf(cur).length();

            line[index] += cur;
            for (int j = 0; j < 4 - len; j++) {
                line[index] += " ";
            }
        }
    }

    public void setDay(int firstDay) {
        for (int i = 0; i < 8; i++) {
            int index = i + 2;
            if (index > 7) return;
            fillLine(index, 1 - firstDay + 7 * i);
        }
    }
}

public class Problem1 {
    public static void main(String[] args) {
        int year = read("inputdata/ex1.txt");

        FormatOutput op = new FormatOutput(year);
        op.formatPrint();
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