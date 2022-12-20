import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static java.lang.Math.abs;

public class Problem5 {
    static void serialize(Object obj) throws IOException {
        ObjectOutputStream objectOutputStream =
                new ObjectOutputStream(new FileOutputStream("data\\manage5.dat"));
        objectOutputStream.writeObject(obj);
        objectOutputStream.close();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Manage manage = new Manage();

        manage.printStudentList();

        serialize(manage);
    }
}

abstract class DataInput {
    public abstract void readFromFile(String filePath) throws IOException;

    public void readFromPath(String path, String prefix) throws IOException {
        File filePoint = new File(path);
        File[] list = filePoint.listFiles();

        prefix = path + "\\" + prefix;
        if (list == null) return;
        for (File file : list) {
            if (file.isDirectory()) continue;
            if (!file.getPath().startsWith(prefix)) continue;

            readFromFile(file.getPath());
        }
    }
}

class Courses extends DataInput implements Serializable {
    HashMap<String, String> courseList = new HashMap<>();
    HashMap<String, Integer> courseScore = new HashMap<>();
    int courseCounter;

    public void outputCourseList() {
        Set<String> keySet = courseList.keySet();
        for (String key : keySet) {
            String value = courseList.get(key);
            System.out.println(key + " : " + value);
        }
    }

    public int getCourseCount() {
        return courseCounter;
    }

    public int getCourseScore(String courseID) {
        if (courseScore.get(courseID) == null) {
            return -1;
        }
        return courseScore.get(courseID);
    }

    public Courses (String path, String prefix) throws IOException {
        super();
        super.readFromPath(path, prefix);
    }

    void mergeCourses (Courses coursesFrom) {
        coursesFrom.courseList.putAll(courseList);
        courseList = coursesFrom.courseList;
        coursesFrom.courseScore.putAll(courseScore);
        courseScore = coursesFrom.courseScore;
    }

    @Override
    public void readFromFile(String filePath) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            lines.forEach(ele -> {
                String[] temp = ele.split(" ");
                if (!courseList.containsKey(temp[0])) courseCounter++;
                courseList.put(temp[0], temp[1]);
                courseScore.put(temp[0], Integer.parseInt(temp[2]));
            });
        }
    }
}

class Student implements Comparable<Student>, Serializable  {
    final double compareEps = 1E-8;

    String stuNum, stuName;
    double creditTotal, creditScoreTotal;
    // creditScoreTotal / creditTotal

    public Student() {
        creditTotal = -1;
        creditScoreTotal = 1;
    }

    public Student(String stuNum, String stuName) {
        this.stuNum = stuNum;
        this.stuName = stuName;
        creditTotal = 0;
        creditScoreTotal = 0;
    }

    public void addCredit(double creditScoreTotal, double creditTotal) {
        this.creditTotal += creditTotal;
        this.creditScoreTotal += creditScoreTotal;
    }

    public double getCreditPercent() {
        return creditScoreTotal / creditTotal / 100;
    }

    public double getGPA() {
        return getCreditPercent() * 4;
    }

    @Override
    public int compareTo(Student o) {
        if (abs(getGPA() - o.getGPA()) < compareEps) return 0;
        return getGPA() - o.getGPA() < 0 ? 1 : -1;
    }
}

class Students extends DataInput implements Serializable {
    List<Student> students;
    Set<String> studentNameSet = new HashSet<>();
    int studentsCounter = 0;

    public Students (String path, String prefix) throws IOException {
        super();
        students = new ArrayList<>();
        super.readFromPath(path, prefix);
    }

    public Students() {
        students = new ArrayList<>();
    }

    public void sortStudentByGPA() {
        Collections.sort(students);
    }

    public void addStudent(String studentID, double creditScoreTotal, double creditTotal) {
        for (Student student : students) {
            if (student.stuNum.equals(studentID)) {
//                System.out.println("add : " + creditScoreTotal + " / " + creditTotal + " = " + creditScoreTotal / creditTotal);
                student.addCredit(creditScoreTotal, creditTotal);
            }
        }
    }

    @Override
    public void readFromFile(String filePath) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            lines.forEach(ele -> {
                String[] temp = ele.split(" ");
                if (!studentNameSet.contains(temp[0])) {
                    studentsCounter++;
                    Student student = new Student(temp[0], temp[1]);
                    students.add(student);
                    studentNameSet.add(temp[0]);
                }
            });
        }
    }

    void mergeStudent (Students studentsNew) {
        for (Student student : studentsNew.students) {
            if (studentNameSet.contains(student.stuNum)) continue;
            studentsCounter++;
            students.add(student);
            studentNameSet.add(student.stuNum);
        }
    }

    public void printStudentList() {
//        System.out.println("[DEBUG - printStudentList] ListNumbers: " + students.size());
        for (Student student : students) {
            System.out.println(student.stuNum + " " + student.stuName + " " + student.getGPA());
        }
    }
}

class Manage extends DataInput implements Serializable {
    Courses courses;
    Students students;
    int numberOfStudents;

    public void inputCourses (String path, String prefix) throws IOException {
        courses = new Courses(path, prefix);
    }

    public void inputStudent (String path, String prefix) throws IOException {
        students = new Students(path, prefix);
    }

    public int getCourseCount () {
        return courses.getCourseCount();
    }

    public void addStudentScore (String studentID, String courseID, double score) {
        int courseScore = courses.getCourseScore(courseID);
        if(courseScore == -1) return;
        students.addStudent(studentID, courseScore * score, courseScore);
    }

    @Override
    public void readFromFile(String filePath) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            lines.forEach(ele -> {
                if (ele.length() < 3) return;
                String[] temp = ele.split(" ");
                addStudentScore(temp[0], temp[1], Integer.parseInt(temp[2]));
            });
        }
    }

    public void printStudentList() {
        students.printStudentList();
    }

    public Object deserialize() throws IOException, ClassNotFoundException {
        File file = new File("data\\manage5.dat");
        if (!file.exists()) return null;

        ObjectInputStream objectInputStream =
                new ObjectInputStream(new FileInputStream("data\\manage5.dat"));
        Object obj = objectInputStream.readObject();
        objectInputStream.close();

        return obj;
    }

    public Manage() throws IOException, ClassNotFoundException {
        super();
        Manage obj = (Manage)deserialize();
        File directory = new File("");
        String path = directory.getCanonicalPath() + "\\inputdata\\ex5";

        inputCourses(path, "C");
        inputStudent(path, "S");
        super.readFromPath(path, "A");

        if (obj != null) {
            courses.mergeCourses(obj.courses);
            students.mergeStudent(obj.students);
        }

        students.sortStudentByGPA();
    }
}
