import java.io.*;
import java.util.*;

public class CompareFile2AgainstFile1 {
    public static void main(String[] args) {

        // Input file paths
        String file1Path = "File1.txt";
        String file2Path = "File2.txt";
        // Output file paths for strings from file2.txt
        String duplicatesOutputPath = "Duplicates_Output.txt";
        String uniquesOutputPath = "Uniques_Output.txt";

        try {
            // Read all strings from file1.txt into a set
            Set<String> file1Strings = readFileToSet(file1Path);

            // Process file2.txt and segregate the strings based on their presence in file1.txt
            Set<String> duplicates = new HashSet<>();
            Set<String> uniques = new HashSet<>();

            File file2 = new File(file2Path);
            Scanner scanner = new Scanner(file2);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                // Check if file1.txt contains the string from file2.txt
                if (file1Strings.contains(line)) {
                    duplicates.add(line);
                } else {
                    uniques.add(line);
                }
            }
            scanner.close();

            // Write the duplicate strings (from file2.txt that are also in file1.txt)
            writeSetToFile(duplicatesOutputPath,
                    "Duplicates (strings from file2.txt that also appear in file1.txt):\n",
                    duplicates);

            // Write the unique strings (from file2.txt that do not appear in file1.txt)
            writeSetToFile(uniquesOutputPath,
                    "Unique Strings (only in file2.txt):\n",
                    uniques);

            System.out.println("Duplicates have been written to: " + duplicatesOutputPath);
            System.out.println("Unique strings have been written to: " + uniquesOutputPath);

        } catch (FileNotFoundException e) {
            System.out.println("One of the input files was not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("An error occurred during file processing: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    // Reads all lines from a given file into a set, skipping blank lines.
    private static Set<String> readFileToSet(String filePath) throws IOException {
        Set<String> strings = new HashSet<>();
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                strings.add(line);
            }
        }
        scanner.close();
        return strings;
    }

    // Writes a header and the contents of a set to a specified file.
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