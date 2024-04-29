import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
/**
 * Class representing a forest, which consists of multiple trees.
 */
public class Forest implements Serializable {
    private String name;
    private List<Tree> trees = new ArrayList<>();
    /**
     * Constructor for creating a new Forest.
     * @param name The name of the forest.
     */
    public Forest(String name) {
        this.name = name;
    }
    /**
     * Adds a tree to the forest.
     * @param tree The tree to add.
     */
    public void addTree(Tree tree) {
        trees.add(tree);
    }
    /**
     * Prompts the user to select a tree to cut down, then removes it from the forest.
     */
    public void cutTree(int index){
        if (index >= 0 && index < trees.size()){
            trees.remove(index);

        }else {
            System.out.println("Tree number: " + index + " does not exist");
        }
    }
    /**
     * Grows all trees in the forest by their respective growth rates.
     */
    public void grow() {
        for (Tree tree : trees) {
            tree.grow();
        }
    }
    /**
     * Reaps (replaces) trees that exceed a certain height threshold.
     */
    public void reap(double heightThreshold) {
        int i;

        for (i = 0; i < trees.size(); i++){
            if (trees.get(i).getHeight() > heightThreshold){
                System.out.printf("%-22s %s \n", "Reaping the tall tree", trees.get(i));
                cutTree(i);
                trees.add(i, Final_Project.createTree(new Random()));
                System.out.printf("%-22s %s \n", "Replaced with new tree", trees.get(i));
            }
        }
    }

    /**
     * Prints the details of all trees in the forest.
     */
    public void printForest() {
        System.out.println("Forest name: " + name);
        for (int i = 0; i < trees.size(); i++) {
            System.out.println("     " + i + " " + trees.get(i));
        }
        System.out.println("There are " + trees.size() + ", with an average height of " + calculateAverageHeight());
    }
    /**
     * Calculates the average height of the trees in the forest.
     * @return The average height of the trees.
     */
    private double calculateAverageHeight() {
        double totalHeight = 0;
        for (Tree tree : trees) {
            totalHeight += tree.getHeight();
        }
        double average = totalHeight / trees.size();
        return Double.parseDouble(String.format("%.2f", average));
    }

}
