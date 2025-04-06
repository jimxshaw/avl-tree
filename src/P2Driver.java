import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class P2Driver {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.print("Error Incorrect Arguments:" + Arrays.toString(args));
            System.exit(0);
        }

        try {
            File inputFile = new File(args[0]);
            File outputFile = new File(args[1]);

            Scanner scanner = new Scanner(inputFile);
            PrintWriter writer = new PrintWriter(outputFile);

            LazyAVLTree tree = new LazyAVLTree();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                // Find out the command.
                // E.g. Insert, Delete, Contains, etc.
                String command = line.contains(":") ? line.substring(0, line.indexOf(":")) : line;

                switch (command) {

                    default:
                        writer.println("Error with line: " + line);
                        break;
                }

            }
        }
        catch (Exception e) {
            System.err.println("Error occurred while processing the files: " + e.getMessage());
        }
    }
}
