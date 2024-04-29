import java.io.Serializable;
/**
 * Represents an individual tree within a forest.
 */
public class Tree implements Serializable {
    private TreeSpecies species;
    private int yearPlanted;
    private double height;
    private double growthRate;



    public enum TreeSpecies {
        BIRCH, MAPLE, FIR
    }

    /**
     * Constructor for creating a new tree.
     * @param species The species of the tree.
     * @param yearPlanted The year the tree was planted.
     * @param height The initial height of the tree.
     * @param growthRate The growth rate of the tree in percent per year.
     */

    public Tree(){

    }
    public Tree(TreeSpecies species, int yearPlanted, double height, double growthRate) {
        this.species = species;
        this.yearPlanted = yearPlanted;
        this.height = height;
        this.growthRate = growthRate;
    }
    /**
     * Simulates the growth of the tree over one year.
     */
    public void grow() {
        height += height * (growthRate/100);
    }
    /**
     * Gets the current height of the tree.
     * @return The height of the tree.
     */
    public double getHeight() {
        return height;
    }
    /**
     * Sets the height of the tree.
     * @param height The new height of the tree.
     */
    public void setHeight(double height) {
        this.height = height;
    }
    /**
     * Returns a string representation of the tree, including species, year planted, height, and growth rate.
     * @return A formatted string representing the tree.
     */
    @Override
    public String toString() {
        return String.format("%-6s %-4d %8.2f' %6.1f%%",
                species.toString(), yearPlanted, height, growthRate);
    }


}
