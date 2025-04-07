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
                    case "Insert":
                        try {
                            int key = Integer.parseInt(line.substring(line.indexOf(":") + 1));

                            String result = tree.insert(key) ? "True" : "False";

                            String lastRotationType = tree.getLastRotationType();

                            writer.println(result + " " + lastRotationType);
                        }
                        catch (NumberFormatException e) {
                            writer.println("Error in Line: " + line);
                        }
                        catch (IllegalArgumentException e) {
                            writer.println("Error in insert: IllegalArgumentException raised");
                        }
                        break;

                    case "Delete":
                        try {
                            int key = Integer.parseInt(line.substring(line.indexOf(":") + 1));
                            String result = tree.delete(key) ? "True" : "False";
                            writer.println(result);
                        }
                        catch (NumberFormatException e) {
                            writer.println("Error in Line: " + line);
                        }
                        catch (Exception e) {
                            writer.println("Error in delete: IllegalArgumentException raised");
                        }
                        break;

                    case "Contains":
                        try {
                            int key = Integer.parseInt(line.substring(line.indexOf(":") + 1));
                            String result = tree.contains(key) ? "True" : "False";
                            writer.println(result);
                        }
                        catch (NumberFormatException e) {
                            writer.println("Error in Line: " + line);
                        }
                        catch (Exception e) {
                            writer.println("Error in contains: IllegalArgumentException raised");
                        }
                        break;

                    case "FindMin":
                        writer.println(tree.findMin());
                        break;

                    case "FindMax":
                        writer.println(tree.findMax());
                        break;

                    case "PrintTree":
                        // Java's println implicitly calls obj.toString()
                        // so writing tree.toString() would be redundant.
                        writer.println(tree);
                        break;

                    case "Height":
                        writer.println(tree.height());
                        break;

                    case "Size":
                        writer.println(tree.size());
                        break;

                    default:
                        writer.println("Error in Line: " + line);
                        break;
                }

            }

            scanner.close();
            writer.close();
        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
