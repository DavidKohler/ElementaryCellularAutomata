import java.util.Scanner;

public class ElementaryCAApp {

    /**
     * Creates new ElementaryCAApp
     * @param width The width to use for the application window. Cannot be negative or zero.
     * @param height The height to use for the application window. Cannot be negative or zero.
     */
    public ElementaryCAApp(final int width, final int height) {
        if (width < 1) {
            throw new IllegalArgumentException("Width must be positive");
        }
        if (height < 1) {
            throw new IllegalArgumentException("Height must be positive");
        }
        final ElementaryCAView elemCAView = new ElementaryCAView(width, height);
        final ElementaryCAPresenter elemCAPresenter = new ElementaryCAPresenter(elemCAView);
        elemCAPresenter.start();
    }

    /**
     * Gets dimensions of window from user
     * @param sc    Scanner object for user input
     * @param dim   String indicating which dimension currently parsing
     * @return      int input of chosen dimension
     */
    public static int inputDimensions(Scanner sc, String dim) {
        int num;
        do {
            System.out.printf("\nPlease Enter Window %s (must not be more than 1280): ", dim);
            while (!sc.hasNextInt()) {
                System.out.println("Invalid response!");
                System.out.printf("\nPlease Enter Window %s (must not be more than 1280): ", dim);
                sc.next();
            }
            num = sc.nextInt();
            if (num <= 0 || num > 1280) {
                System.out.println("Invalid response!");
            }
        } while (num <= 0 || num > 1280);
        System.out.printf("%s confirmed!\n", dim);
        return num;
    }

    /**
     * Main method
     * @param args The runtime args.
     */
    public static void main(final String... args) {
        int width;
        int height;
        Scanner sc = new Scanner(System.in);
        width = inputDimensions(sc, "Width");
        height = inputDimensions(sc, "Height");

        new ElementaryCAApp(width, height);
    }
}
