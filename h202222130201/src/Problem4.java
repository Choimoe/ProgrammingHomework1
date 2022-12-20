import java.io.File;

public class Problem4 {
    public static class DirectoryTree {
        private String path;
        private int fileDepth;

        private final String prefix = "   ";

        String outputFilename (String filename, int depth, boolean isDirectory) {
            StringBuilder result = new StringBuilder();
            result.append(prefix.repeat(depth));

            if (isDirectory) {
                result.append("<d>");
            } else {
                result.append("<f>");
            }

            result.append(filename);

            return result.toString();
        }

        void printFilename (String formatString) {
            System.out.println(formatString);
        }

        private void searchDir (String nowDir) {
            File filePoint = new File(nowDir);
            File[] list = filePoint.listFiles();

            for (File file : list) {
                boolean isDirectory = file.isDirectory();
                printFilename(outputFilename(file.getName(), fileDepth, isDirectory));
                if (isDirectory) {
                    fileDepth++;
                    searchDir(file.getPath());
                    fileDepth--;
                }
            }
        }

        private void searchDir () {
            String[] dirNameList = path.split("\\\\");
            int size = dirNameList.length;
            printFilename(outputFilename(dirNameList[size - 1], 0, true));
            searchDir(path);
        }

        public DirectoryTree(String path) {
            fileDepth = 1;
            this.path = path;
        }
    }

    public static void main (String[] args) {
        String path = ".";

        DirectoryTree file = new DirectoryTree(path);

        file.searchDir();
    }
}
