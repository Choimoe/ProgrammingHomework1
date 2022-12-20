import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class Problem6 {
    static void serialize(Object obj) throws IOException {
        ObjectOutputStream objectOutputStream =
                new ObjectOutputStream(new FileOutputStream("data\\manage6.dat"));
        objectOutputStream.writeObject(obj);
        objectOutputStream.close();
    }

    public static void main(String[] args) throws Exception {
        Management op = new Management();
        op.inputFromFile("\\InputData\\ex6\\data6.txt");

        op.sortOrdered1();

        System.out.println("============================================================");
        System.out.println("Sorting by Age and Number results:");
        System.out.println("============================================================");
        op.print();

        op.sortOrdered2();

        System.out.println("============================================================");
        System.out.println("Sorting by getCompareValue results:");
        System.out.println("============================================================");
        op.print();

        serialize(op);
    }
}

abstract class Person implements Serializable {
    int age;
    String number, name;

    public String getAgeID() {
        return age + number;
    }

    public abstract int getCompareValue();
}

class StudentScore extends Person implements Serializable {
    final int ENG_COURSE_SCORE = 6, MATH_COURSE_SCORE = 5, PROGRAM_COURSE_SCORE = 4;
    int englishScore, mathScore, programScore;
    // 6 5 4

    @Override
    public int getCompareValue() {
        return (englishScore * ENG_COURSE_SCORE
                + mathScore * MATH_COURSE_SCORE
                + programScore * PROGRAM_COURSE_SCORE)
                / (ENG_COURSE_SCORE + MATH_COURSE_SCORE + PROGRAM_COURSE_SCORE);
    }

    @Override
    public String toString() {
        return "number = " + number
                + ", name = " + name
                + ", age = " + age
                + ", english = " + englishScore
                + ", math = " + mathScore
                + ", program = " + programScore
                + ", GPA = " + getCompareValue();
    }
}

class Staff extends Person implements Serializable {
    int salary;

    @Override
    public int getCompareValue() {
        return salary;
    }

    @Override
    public String toString() {
        return "number = " + number
                + ", name = " + name
                + ", age = " + age
                + ", salary = " + salary;
    }
}

class Management implements Serializable {
    final int SIZE = 1024;
    Object[] objects;
    int stuNum, staffNum;

    public int size() {
        return stuNum + staffNum;
    }

    public static String[][] splitInput (String input) {
        String[] split = input.split(",");

        int size = split.length;
        String[][] result = new String[size][2];

        for (int i = 0; i < size; i++) {
            result[i] = split[i].split(":");
        }

        return result;
    }

    public Staff newStaff(String[][] input) {
        Staff staff = new Staff();

        for (String[] item : input) {
            switch (item[0]) {
                case "number" -> staff.number = item[1];
                case "name" -> staff.name = item[1];
                case "age" -> staff.age = Integer.parseInt(item[1]);
                case "salarys" -> staff.salary = Integer.parseInt(item[1]);
            }
        }

        return staff;
    }

    public StudentScore newStudent(String[][] input) {
        StudentScore student = new StudentScore();

        for (String[] item : input) {
            switch (item[0]) {
                case "number" -> student.number = item[1];
                case "name" -> student.name = item[1];
                case "age" -> student.age = Integer.parseInt(item[1]);
                case "english" -> student.englishScore = Integer.parseInt(item[1]);
                case "math" -> student.mathScore = Integer.parseInt(item[1]);
                case "program" -> student.programScore = Integer.parseInt(item[1]);
            }
        }

        return student;
    }

    public void inputFromFile(String dataPath) throws IOException {
        File directory = new File("");
        String filePath = directory.getCanonicalPath() + dataPath;
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            lines.map(Management::splitInput).forEach(map -> {
                if (map.length <= 3) return;
                else if (map.length == 4) {
                    staffNum++;
                    Object obj = newStaff(map);
                    if (obj != null) objects[size()] = obj;
                } else {
                    stuNum++;
                    Object obj = newStudent(map);
                    if (obj != null) objects[size()] = obj;
                }
            });
        }
    }

    public void sortOrdered1() {
        // age first
        // number second

        Arrays.sort(objects, (x, y) -> {
            if (x == null) return -1;
            if (y == null) return 1;

            String xVal, yVal;

            if (x instanceof StudentScore) xVal = ((StudentScore) x).getAgeID();
            else xVal = ((Staff) x).getAgeID();

            if (y instanceof StudentScore) yVal = ((StudentScore) y).getAgeID();
            else yVal = ((Staff) y).getAgeID();

            return xVal.compareTo(yVal);
        });
    }

    public void sortOrdered2() {
        Arrays.sort(objects, (x, y) -> {
            if (x == null) return -1;
            if (y == null) return 1;
            if (x.getClass() == y.getClass()) {
                if (x instanceof StudentScore) {
                    return ((StudentScore) x).getCompareValue() - ((StudentScore) y).getCompareValue();
                } else {
                    return ((Staff) x).getCompareValue() - ((Staff) y).getCompareValue();
                }
            }
            else return x instanceof StudentScore ? 1 : -1;
        });
    }

    public void print() {
        for (Object o : objects) {
            if (o == null) continue;
            System.out.println(o.toString());
        }
    }

    public Management() {
        objects = new Object[SIZE];
    }
}
