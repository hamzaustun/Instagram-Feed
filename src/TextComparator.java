import java.io.*;
import java.util.*;

public class TextComparator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input text files or direct text
        String file1Path = "output.txt";
        String file2Path = "otype4_small.txt";

        try {
            // Read the files
            List<String> text1 = readFile(file1Path);
            List<String> text2 = readFile(file2Path);

            // Compare the texts
            compareTexts(text1, text2);
        } catch (IOException e) {
            System.out.println("Error reading files: " + e.getMessage());
        }
    }

    // Method to read a file into a list of strings
    private static List<String> readFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    // Method to compare two texts line by line
    private static void compareTexts(List<String> text1, List<String> text2) {
        int maxLines = Math.max(text1.size(), text2.size());
        boolean differencesFound = false;

        System.out.println("\nComparison Results:");
        System.out.println("====================");

        for (int i = 0; i < maxLines; i++) {
            String line1 = i < text1.size() ? text1.get(i) : "<No line>";
            String line2 = i < text2.size() ? text2.get(i) : "<No line>";

            if (!line1.equals(line2)) {
                differencesFound = true;
                System.out.printf("Line %d:\n", i + 1);
                System.out.println("Text 1: " + line1);
                System.out.println("Text 2: " + line2);
                System.out.println();
            }
        }

        if (!differencesFound) {
            System.out.println("The texts are identical.");
        }
    }
}

