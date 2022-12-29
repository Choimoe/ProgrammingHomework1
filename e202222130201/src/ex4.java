import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ex4 {
    public static void main(String[] args) {
        List<String> lines = readFile("login-view.fxml");

        analysisFXMLData(lines);

        analysisFXMLDataToJavaFile(lines, "LoginController.java");
    }

    // 获取所有要定义的类型与名称
    private static void analysisFXMLDataToJavaFile(List<String> lines, String outputFile) {
        StringBuilder sb = new StringBuilder();
        int index = 0, tabHeight = 0;
        boolean getMain = true, firstLine = true;
        Map<String, Integer> map = new HashMap<>();

        Pattern r = Pattern.compile("fx:id=\"(\\w+)\"");
        Pattern getMainClass = Pattern.compile("fx:controller=\"(.+)\"");

        while(index < lines.size()) {
            StringBuilder line = new StringBuilder(lines.get(index));
            if (line.toString().trim().startsWith("<!")) {
                index++;
                continue;
            }

            if (line.toString().trim().startsWith("<")) {
                int startIndex = line.toString().indexOf('<');
                int endIndex = line.toString().indexOf('>');

                while(endIndex < 0) {
                    line.append(lines.get(++index));
                    endIndex = line.toString().indexOf('>');
                }

                String tag = line.substring(startIndex + 1, endIndex);
                String belong = tag.split(" ")[0];
                String name;

                if (belong.startsWith("?")) {
                    if (belong.equals("?import")) {
                        sb.append(tag, 1, tag.length() - 1).append(";").append("\r\n");
                    }
                    index++;
                    continue;
                }

                if (belong.equals("children") || belong.equals("/children") || belong.startsWith("/")) {
                    index++;
                    continue;
                }

                if (firstLine) {
                    sb.append("\n");
                    firstLine = false;
                }

                if (getMain) {
                    Matcher className = getMainClass.matcher(tag);
                    if (className.find()) {
                        String mainClassNameSource = className.group(1);
                        int classIndex = mainClassNameSource.lastIndexOf('.');
                        String packageName = mainClassNameSource.substring(0, classIndex);
                        String mainClassName = mainClassNameSource.substring(classIndex + 1);
                        packageName = "package " + packageName + ";\n\n";
                        sb.insert(0, packageName);
                        sb.append("public class ").append(mainClassName).append(" {\n");
                        tabHeight++;
                        getMain = false;
                    }
                }

                Matcher m = r.matcher(tag);
                if (m.find()) name = m.group(1);
                else name = belong.toLowerCase();
                if (map.containsKey(name)) {
                    map.put(name, map.get(name) + 1);
                    name += map.get(name);
                } else map.put(name, 1);
                sb.append("    ".repeat(tabHeight)).append("@FXML\n").append("    ".repeat(tabHeight)).append("private ").append(belong).append(" ").append(name).append(";").append("\n");
            }
            index++;
        }

        sb.append("\n");
        sb.append("    @FXML\n").append("    public void initialize() {\n").append("    }\n").append("\n").append("    @FXML\n").append("    public void onLoginButtonClick() {\n").append("    }").append("\n}\n");
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> readFile(String fileName) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private static void analysisFXMLData(List<String> lines) {
        int containerCount = 0, controlCount = 0;

        int index = 0;
        boolean newPush = false;
        Stack<String> stack = new Stack<>();
        stack.push("root");
        System.out.println("root");
        while(index < lines.size()) {
            StringBuilder line = new StringBuilder(lines.get(index));
            if (line.toString().trim().startsWith("<?") || line.toString().trim().startsWith("<!")) {
                index++;
                continue;
            }
            if (line.toString().trim().startsWith("<")) {
                int startIndex = line.toString().indexOf('<');
                int endIndex = line.toString().indexOf('>');

                while(endIndex < 0) {
                    line.append(lines.get(++index));
                    endIndex = line.toString().indexOf('>');
                }

                String tag = line.substring(startIndex + 1, endIndex);
                String belong = tag.split(" ")[0];

                if (belong.equals("children") || belong.equals("/children")) {
                    index++;
                    continue;
                }

                if (belong.startsWith("/")) {
                    if (newPush) controlCount++;
                    else containerCount++;

                    stack.pop();

                    newPush = false;
                    index++;
                    continue;
                }

                stack.push(belong);

                String prefix = "|   ".repeat(stack.size() - 2);
                System.out.println(prefix + "|---" + belong);

                newPush = true;
                if (tag.endsWith("/")) {
                    controlCount++;
                    stack.pop();
                    newPush = false;
                }
            }
            index++;
        }

        System.out.println("container count: " + containerCount);
        System.out.println("controller count: " + controlCount);
    }
}
