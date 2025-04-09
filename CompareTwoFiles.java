import java.io.*;
import java.util.*;

public class CompareTwoFiles {
    public static void main(String[] args) {

        // Input file paths
        String file1Path = "File1.txt";
        String file2Path = "File2.txt";
        // Output file paths
        String nonUniqueOutputPath = "NonUnique_Strings.txt";
        String uniqueOutputPath = "Unique_Strings.txt";

        try {
            // Read the strings from both files
            Set<String> file1Strings = readFileToSet(file1Path);
            Set<String> file2Strings = readFileToSet(file2Path);

            // Create a set with common strings (i.e. non-unique strings)
            Set<String> commonStrings = new HashSet<>(file1Strings);
            commonStrings.retainAll(file2Strings);

            // Create a set with unique strings (i.e. strings that appear only in one file)
            Set<String> uniqueStrings = new HashSet<>();
            uniqueStrings.addAll(file1Strings);
            uniqueStrings.addAll(file2Strings);
            uniqueStrings.removeAll(commonStrings);

            // Write the non-unique (common) strings to a file
            writeSetToFile(nonUniqueOutputPath,
                    "Non-Unique Strings (found in both files):\n",
                    commonStrings);

            // Write the unique strings (only found in one file) to another file
            writeSetToFile(uniqueOutputPath,
                    "Unique Strings (only in one file):\n",
                    uniqueStrings);

            System.out.println("Non-unique strings have been written to: " + nonUniqueOutputPath);
            System.out.println("Unique strings have been written to: " + uniqueOutputPath);

        } catch (FileNotFoundException e) {
            System.out.println("One or both of the input files were not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("An error occurred during file processing: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    // Reads all lines from a file into a set (ignoring blank lines)
    private static Set<String> readFileToSet(String filePath) throws IOException {
        Set<String> strings = new HashSet<>();
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {  // Skip empty lines
                strings.add(line);
            }
        }
        scanner.close();
        return strings;
    }

    // Writes a header and the contents of a set to the specified file
    private static void writeSetToFile(String filePath, String header, Set<String> data) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write(header);
        if (data.isEmpty()) {
            writer.write("None\n");
        } else {
            for (String s : data) {
                writer.write(s + "\n");
            }
        }
        writer.close();
    }
}
