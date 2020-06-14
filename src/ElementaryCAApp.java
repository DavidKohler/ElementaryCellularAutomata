import java.awt.*;
import java.util.Arrays;
import java.util.Scanner;

public class ElementaryCAApp {

    /**
     * Creates new ElementaryCAApp
     * @param width The width to use for the application window. Cannot be negative or zero.
     * @param height The height to use for the application window. Cannot be negative or zero.
     */
    public ElementaryCAApp(final int width, final int height, final int maxW, final int maxH, final int[] ruleSet, final boolean simpleStart, final boolean[] hasAnimation) {
        final ElementaryCAView elemCAView = new ElementaryCAView(width, height, maxW, maxH);
        final ElementaryCAPresenter elemCAPresenter = new ElementaryCAPresenter(elemCAView, ruleSet, simpleStart, hasAnimation);
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
        return ruleSet;
    }

    /**
     * Prompts user for simple or random initial conditions
     * @param sc    Scanner object for user input
     * @return      false if random, true if simple condition chosen
     */
    public static boolean inputSimpleStart(Scanner sc) {
        int choice;
        do {
            System.out.println("\nPlease Choose One Of The Following Options (Enter Choice # Below)");
            System.out.println("1) Start with simple initial condition");
            System.out.println("2) Start with random initial condition");
            System.out.print("Choice: ");
            while (!sc.hasNextInt()) {
                System.out.println("Please only enter 1 or 2!");
                System.out.println("\nPlease Choose One Of The Following Options (Enter Choice # Below)");
                System.out.println("1) Start with simple initial condition");
                System.out.println("2) Start with random initial condition");
                System.out.print("Choice: ");
                sc.next();
            }
            choice = sc.nextInt();
            if (choice < 1 || choice > 2) {
                System.out.println("Please only enter 1 or 2!");
            }
        } while (choice < 1 || choice > 2);

        System.out.println("Initial Condition Confirmed!");
        if (choice == 1) {
            return true;
        }
        return false;
    }

    /**
     * Prompts user for animation preferences (and scroll preferences if animation enabled)
     * @param sc    Scanner object for user input
     * @return      boolean array (F,F) if no animation, (T,T/F) if animation enabled
     */
    public static boolean[] inputAnimationSelect(Scanner sc) {
        int choice;
        do {
            System.out.println("\nPlease Choose One Of The Following Options (Enter Choice # Below)");
            System.out.println("1) No Animation (Faster)");
            System.out.println("2) Yes Animation (Slower)");
            System.out.print("Choice: ");
            while (!sc.hasNextInt()) {
                System.out.println("Please only enter 1 or 2!");
                System.out.println("\nPlease Choose One Of The Following Options (Enter Choice # Below)");
                System.out.println("1) No Animation (Faster)");
                System.out.println("2) Yes Animation (Slower)");
                System.out.print("Choice: ");
                sc.next();
            }
            choice = sc.nextInt();
            if (choice < 1 || choice > 2) {
                System.out.println("Please only enter 1 or 2!");
            }
        } while (choice < 1 || choice > 2);

        System.out.println("Animation Choice Confirmed!");

        if (choice == 1) {
            return new boolean[]{false, false};
        }

        int aniChoice;
        do {
            System.out.println("\nPlease Choose One Of The Following Options (Enter Choice # Below)");
            System.out.println("1) Scroll Off (Finite)");
            System.out.println("2) Scroll On (Infinite)");
            System.out.print("Choice: ");
            while (!sc.hasNextInt()) {
                System.out.println("Please only enter 1 or 2!");
                System.out.println("\nPlease Choose One Of The Following Options (Enter Choice # Below)");
                System.out.println("1) Scroll Off (Finite)");
                System.out.println("2) Scroll On (Infinite)");
                System.out.print("Choice: ");
                sc.next();
            }
            aniChoice = sc.nextInt();
            if (aniChoice < 1 || aniChoice > 2) {
                System.out.println("Please only enter 1 or 2!");
            }
        } while (aniChoice < 1 || aniChoice > 2);

        System.out.println("Scroll Choice Confirmed!");

        if (aniChoice == 1) {
            return new boolean[]{true, false};
        }
        return new boolean[]{true, true};
    }

    /**
     * Main method
     * @param args The runtime args.
     */
    public static void main(final String... args) {
        int width;
        int height;
        int[] ruleSet;
        boolean simpleStart;
        boolean[] hasAnimation;

        Scanner sc = new Scanner(System.in);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int maxW = (int) screenSize.getWidth();
        int maxH = (int) screenSize.getHeight() - 70;

        width = inputDimensions(sc, "Width", maxW);
        height = inputDimensions(sc, "Height", maxH);

        ruleSet = inputRulenumber(sc);
        simpleStart = inputSimpleStart(sc);
        hasAnimation = inputAnimationSelect(sc);

        new ElementaryCAApp(width, height, maxW, maxH, ruleSet, simpleStart, hasAnimation);
    }
}
