import java.awt.*;
import java.util.Arrays;
import java.util.Scanner;

public class ElementaryCAApp {

    /**
     * Creates new ElementaryCAApp
     * @param width The width to use for the application window. Cannot be negative or zero.
     * @param height The height to use for the application window. Cannot be negative or zero.
     */
    public ElementaryCAApp(final int width, final int height, final int maxW, final int maxH, final int[] ruleSet) {
        final ElementaryCAView elemCAView = new ElementaryCAView(width, height, maxW, maxH);
        final ElementaryCAPresenter elemCAPresenter = new ElementaryCAPresenter(elemCAView, ruleSet);
        elemCAPresenter.start();
    }

    /**
     * Gets dimensions of automaton from user
     * @param sc        Scanner object for user input
     * @param dim       String indicating which dimension currently parsing
     * @param maxDim    Max value of chosen dimension possible
     * @return          int input of chosen dimension
     */
    public static int inputDimensions(Scanner sc, String dim, int maxDim) {
        int num;
        do {
            System.out.printf("\nPlease Enter Automaton %s (must not be more than %d): ", dim, maxDim);
            while (!sc.hasNextInt()) {
                System.out.println("Invalid response!");
                System.out.printf("\nPlease Enter Automaton %s (must not be more than %d): ", dim, maxDim);
                sc.next();
            }
            num = sc.nextInt();
            if (num <= 0 || num > maxDim) {
                System.out.println("Invalid response!");
            }
        } while (num <= 0 || num > maxDim);
        System.out.printf("%s confirmed!\n", dim);
        return num;
    }

    /**
     * Gets rule set for simulation from user
     * @param sc    Scanner object for user input
     * @return      int array of binary representation of decimal rule number
     */
    public static int[] inputRulenumber(Scanner sc) {
        int num;
        int[] ruleSet = new int[8];
        String binaryRule;

        do {
            System.out.print("\nPlease Enter Rule Number in Decimal Form (0 to 255): ");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid response!");
                System.out.print("\nPlease Enter Rule Number in Decimal Form (0 to 255): ");
                sc.next();
            }
            num = sc.nextInt();
            if (num < 0 || num > 255) {
                System.out.println("Must be between 0 and 255!");
            }
        } while (num < 0 || num > 255);

        Arrays.fill(ruleSet, 0);
        binaryRule = Integer.toBinaryString(num);

        int val;
        int offset = 8 - binaryRule.length();
        for (int i = 0; i < binaryRule.length(); i++) {
            val = Integer.parseInt(String.valueOf(binaryRule.charAt(i)));
            ruleSet[i + offset] = val;
        }
        System.out.println("Ruleset Confirmed!");
        System.out.println(Arrays.toString(ruleSet));
        return ruleSet;
    }

    /**
     * Main method
     * @param args The runtime args.
     */
    public static void main(final String... args) {
        int width;
        int height;
        int[] ruleSet;

        Scanner sc = new Scanner(System.in);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int maxW = (int) screenSize.getWidth();
        int maxH = (int) screenSize.getHeight() - 70;

        width = inputDimensions(sc, "Width", maxW);
        height = inputDimensions(sc, "Height", maxH);

        ruleSet = inputRulenumber(sc);

        new ElementaryCAApp(width, height, maxW, maxH, ruleSet);
    }
}
