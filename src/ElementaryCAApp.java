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
     * Main method
     * @param args The runtime args.
     */
    public static void main(final String... args) {
        // TODO: make this customizable
        new ElementaryCAApp(400, 200);
    }
}
