public class ElementaryCAView {
    private final int width;
    private final int height;
    private final double cellSize;
    private final int cellMultiplier;

    /**
     * Creates new view
     * @param width     width of application window. Must be >0
     * @param height    height of application window. Must be >0
     */
    public ElementaryCAView(final int width, final int height, final int maxW, final int maxH) {
        this.width = width;
        this.height = height;
        this.cellMultiplier = Math.min(maxW / width, maxH / height);
        this.cellSize = cellMultiplier / 2;

        StdDraw.setCanvasSize(width * cellMultiplier, height * cellMultiplier);
        StdDraw.setYscale(0, height * cellMultiplier);
        StdDraw.setXscale(0, width * cellMultiplier);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    /**
     * Get width of window
     * @return  int width of window
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get height of window
     * @return  int height of window
     */
    public int getHeight() {
        return height;
    }

    /**
     * Draw generations to screen
     * @param automaton    2d array of ints
     */
    public void drawGeneration(final int[][] automaton) {
        StdDraw.clear();
        for (int row = 0; row < automaton.length; row++) {
            for (int col = 0; col < automaton[row].length; col++) {
                if (automaton[row][col] == 1) {
                    StdDraw.filledSquare(
                            (col * this.cellMultiplier) + (this.cellMultiplier / 2),
                            ((automaton.length - 1 - row) * this.cellMultiplier) + (this.cellMultiplier / 2),
                            this.cellSize);
                }
            }
        }
        StdDraw.show();
    }
}
