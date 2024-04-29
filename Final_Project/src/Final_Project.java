import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;
import java.io.*;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/**
 * Main class for the forestry simulation program.
 */
public class Final_Project implements Serializable{
    public static final int MAX_HEIGHT_AND_GROWTH=20;
    public static final int MIN_HEIGHT_AND_GROWTH=10;
    public static final int MAX_YEAR=2026;
    public static final int MIN_YEAR=2000;

    /**
     * Main method to run the forestry simulation.
     * @param args Command line arguments (not used).
     * @throws FileNotFoundException if the initial forest file is not found.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        Forest currentForest;
        String currentForestName= "Montane";
        System.out.println("Welcome to the Forestry Simulation");
        System.out.println("----------------------------------");
        System.out.println("Initializing from Montane");

        currentForest = loadForestFromCSV(args[0]);

        String command;
        do {
            System.out.println("(P)rint, (A)dd, (C)ut, (G)row, (R)eap, (S)ave, (L)oad, (N)ext, e(X)it:");
            command = scanner.next().toUpperCase();
            switch (command) {
                case "P":
                    currentForest.printForest();
                    break;
                case "A":
                    Tree newTree = createTree(random);
                    currentForest.addTree(newTree);
                    break;
                case "C":
                    do {
                        System.out.print("Tree number to cut down: ");
                        try{
                            currentForest.cutTree(scanner.nextInt());
                            System.out.println();
                            break;
                        } catch (InputMismatchException e){
                            System.out.print("That is not an integer");
                            scanner.next();
                            System.out.println();
                        }

                    } while (true);
                    break;
                case "G":
                    currentForest.grow();
                    break;
                case "R":
                    do {
                        System.out.print("Height to reap from : ");
                        try {
                            currentForest.reap(scanner.nextInt());
                            System.out.println();
                            break;
                        }catch (InputMismatchException e){
                            System.out.print("That is not an integer");
                            scanner.next();
                            System.out.println();
                        }

                    }while(true);
                    break;
                case "S":
                    saveForest(currentForest, currentForestName);
                    break;
                case "L":
                    System.out.println("Enter forest name:");
                    String loadFileName = scanner.next();
                    currentForest = loadForest(loadFileName, currentForest);
                    break;
                case "N":
                    for (int i = 1; i < args.length; i++) {
                        System.out.println("Initializing from " + args[i]);
                        System.out.println("-------------------------");
                        currentForest = loadForestFromCSV(args[i]);
                        currentForestName = args[i];
                    }
                    break;
                case "X":
                    System.out.println("Exiting the Forestry Simulation");
                    break;
                default:
                    System.out.println("Invalid menu option, try again");
                    break;
            }
        } while (!command.equals("X"));
        scanner.close();
    }
    /**
     * Saves a forest object to a db file.
     * @param forest The forest to save.
     * @param fileName The file name to save the forest under.
     */
    public static void saveForest(Forest forest, String fileName) {
        ObjectOutputStream toStream= null;
        try {
            toStream = new ObjectOutputStream(new FileOutputStream(fileName + ".db"));
            toStream.writeObject(forest);
        } catch (IOException e) {
            System.out.println("Error saving");
        }finally{
            if(toStream != null){
                try{
                    toStream.close();
                }catch(IOException e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    /**
     * Loads a forest from a CSV file.
     * @param CSVFile The name of the forest file without extension.
     * @return A Forest object populated with trees.
     */
    public static Forest loadForestFromCSV(String CSVFile) {
        String oneLine;
        String[] treeVariables;
        Tree.TreeSpecies treeType;
        Forest localForest;
        int year;
        double growthRate;
        double height;
        localForest = new Forest(CSVFile);
        try{
            BufferedReader fromBufferedReader = new BufferedReader(new FileReader(CSVFile + ".csv"));
            oneLine = fromBufferedReader.readLine();
            while (oneLine != null) {
                treeVariables = oneLine.split(",");
                treeType = Tree.TreeSpecies.valueOf(treeVariables[0].toUpperCase());
                year = parseInt(treeVariables[1]);
                growthRate = parseDouble(treeVariables[2]);
                height = parseDouble(treeVariables[3]);
                localForest.addTree(new Tree(treeType, year, growthRate, height));
                oneLine = fromBufferedReader.readLine();
            }
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            return(null);
        } catch (IOException e) {
            System.out.println("Error opening/reading " + e.getMessage());
            return(null);
        }
        return localForest;
    }

    /**
     * Loads a forest object from a db file.
     * @param fileName The file name of the forest to load.
     * @return The loaded forest.
     */
    public static Forest loadForest(String fileName, Forest currentForest){
        ObjectInputStream fromStream= null;
        Forest readForest;
        try{
            fromStream = new ObjectInputStream(new FileInputStream(fileName + ".db"));
            readForest = (Forest)fromStream.readObject();
        } catch (IOException e){
            System.out.println("Error opening/reading " + fileName + ".db");
            System.out.println("Old forest retained");
            return(currentForest);
        } catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
            System.out.println("Old forest retained");
            return(currentForest);
        } finally {
            if(fromStream != null){
                try{
                    fromStream.close();
                }catch (IOException e){
                    System.out.println("Error closing " + e.getMessage());
                }
            }
        }
        return (readForest);
    }
    /**
     * Creates a new tree with random attributes.
     * @param random The random generator to use.
     * @return A new Tree object.
     */
    public static Tree createTree(Random random) {
        int year = MIN_YEAR + random.nextInt(MAX_YEAR - MIN_YEAR + 1);
        double height = MIN_HEIGHT_AND_GROWTH + random.nextDouble() * (MAX_HEIGHT_AND_GROWTH - MIN_HEIGHT_AND_GROWTH);
        double growthRate = MIN_HEIGHT_AND_GROWTH  + random.nextDouble() * ((MAX_HEIGHT_AND_GROWTH - MIN_HEIGHT_AND_GROWTH) / 100.0);
        Tree.TreeSpecies species = Tree.TreeSpecies.values()[random.nextInt(Tree.TreeSpecies.values().length)];
        return new Tree(species, year, height, growthRate);
    }
}
