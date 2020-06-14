import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.Random;

public class ElementaryCAPresenter {

    private final ElementaryCAView view;
    private int[] currentGeneration;
    private int[][] totalAutomaton;
    private int currentGenerationNumber;
    private final int[] ruleSet;
    private final boolean animationOn;
    private final boolean scrollOn;

    /**
     * Constructor to create ECA presenter
     * @param view ElementaryCAView object. Can't be null
     */
    public ElementaryCAPresenter(final ElementaryCAView view, final int[] ruleArr, final boolean simpleStart, final boolean[] hasAnimation) {
        if (view == null) {
            throw new IllegalArgumentException("View cannot be null");
        }
        this.view = view;
        ArrayUtils.reverse(ruleArr);
        this.ruleSet = ruleArr;
        this.currentGeneration = new int[view.getWidth()];
        this.totalAutomaton = new int[view.getHeight()][view.getWidth()];
        this.currentGenerationNumber = 1;
        final Random random = new Random();

        if (simpleStart == false) {
            for (int i = 0; i < currentGeneration.length; i++) {
                currentGeneration[i] = random.nextInt(2);
            }
        } else {
            Arrays.fill(currentGeneration, 0);
            currentGeneration[currentGeneration.length / 2] = 1;
        }

        if (hasAnimation[0] == true) {
            if (hasAnimation[1] == true) {
                this.animationOn = true;
                this.scrollOn = true;
            } else {
                this.animationOn = true;
                this.scrollOn = false;
            }
        } else {
            this.animationOn = false;
            this.scrollOn = false;
        }

        for (int[] row: totalAutomaton)
            Arrays.fill(row, 0);

        if (this.scrollOn == true) {
            totalAutomaton[totalAutomaton.length - 1] = currentGeneration.clone();
        } else {
            totalAutomaton[0] = currentGeneration.clone();
        }
    }

    /**
     * Starts progressing thru generations and updating the view
     */
    public void start() {
        while (this.currentGenerationNumber < view.getHeight()) {
            currentGeneration = progressGeneration(currentGeneration);
            totalAutomaton = progressAutomaton(totalAutomaton);
            if (this.animationOn == true) {
                view.drawGeneration(totalAutomaton);
            }
            if (this.scrollOn == false) {
                this.currentGenerationNumber += 1;
            } else {
                // seems to help with screen tearing
                StdDraw.pause(40);
            }
        }
        if (this.animationOn == false) {
            view.drawGeneration(totalAutomaton);
        }
        System.out.println("Finished!");
    }

    /**
     * Get next generation from current generation
     * @param generation    current generation array of ints
     * @return              next generation array of ints based on ruleset
     */
    private int[] progressGeneration(final int[] generation) {
        final int[] nextGeneration = cloneGeneration(generation);
        int nextState;
        // Decide the fate of each cell
        for (int i = 0; i < generation.length; i++) {
            nextState = checkRules(generation, i);
            nextGeneration[i] = nextState;
        }

        return nextGeneration;
    }

    /**
     * Update total automaton based on new generation
     * @param automaton     old 2d array of generations
     * @return              new 2d array with new generation copied in
     */
    private int[][] progressAutomaton(final int[][] automaton) {
        final int[][] nextAutomaton;
        if (this.scrollOn == false) {
            nextAutomaton = cloneAutomaton(automaton);
            nextAutomaton[this.currentGenerationNumber] = cloneGeneration(this.currentGeneration);
        } else {
            nextAutomaton = shiftAutomaton(automaton);
            nextAutomaton[nextAutomaton.length - 1] = cloneGeneration(this.currentGeneration);
        }

        return nextAutomaton;
    }

    /**
     * Clone 1D generation array and return it
     * @param originalGeneration    original 1d array to clone
     * @return                      cloned 1d array
     */
    private int[] cloneGeneration(final int originalGeneration[]) {
        final int[] newGeneration = originalGeneration.clone();
        return newGeneration;
    }

    /**
     * Clone 2d automaton array and return it
     * @param originalAutomaton     original 2d array to clone
     * @return                      cloned 2d array
     */
    private int[][] cloneAutomaton(final int originalAutomaton[][]) {
        final int[][] newAutomaton = new int[originalAutomaton.length][];
        for (int row = 0; row < originalAutomaton.length; ++row) {
            newAutomaton[row] = Arrays.copyOf(originalAutomaton[row], originalAutomaton[row].length);
        }
        return newAutomaton;
    }

    /**
     * Clones and shifts 2d automaton array and return it
     * @param originalAutomaton     original 2d array to clone
     * @return                      cloned and shifted 2d array
     */
    private int[][] shiftAutomaton(final int originalAutomaton[][]) {
        final int[][] newAutomaton = new int[originalAutomaton.length][];
        for (int row = 0; row < originalAutomaton.length - 1; ++row) {
            newAutomaton[row] = Arrays.copyOf(originalAutomaton[row + 1], originalAutomaton[row + 1].length);
        }
        return newAutomaton;
    }

    /**
     * Check one location in generation per ruleset to find next state
     * @param generation    1d array of current generation
     * @param loc           index of center of where to look in generation
     * @return              either 0 or 1 for value in loc for next generation
     */
    private int checkRules(final int[] generation, final int loc) {
        int a;
        int b;
        int c;

        if (loc == 0) {
            a = generation[generation.length - 1];
        } else {
            a = generation[loc - 1];
        }
        if (loc == generation.length - 1) {
            c = generation[0];
        } else {
            c = generation[loc + 1];
        }
        b = generation[loc];

        String neighborhood = "" + a + b + c;

        int index = Integer.parseInt(neighborhood,2);

        return this.ruleSet[index];
    }
}