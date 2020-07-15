import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;
import java.util.Scanner;

public class SudokuQ extends JPanel {

    /* location of sudoku */
    private static final String TEXT_LOCATION = "src/Sudoku.txt";

    /* graphics */
    private static final String TITLE = "View.java";
    private static final int APPLICATION_WIDTH = 900;
    private static final int APPLICATION_HEIGHT = 900;
    private static final int BOX_DIMENSIONS = 100;
    private static JFrame f;

    /* boards */
    private static int[][] initialSudoku = new int[9][9];
    private static int[][] board = new int[9][9];

    /* to test for completion */
    private static Location lastSpaceToFill;

    /* whether to show graphics */
    private static boolean showGraphics;

    /* main */
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        System.out.println("- Can solve a sudoku puzzle through stack\n");

        StdOut.print("Show graphics?\nTrue/false: ");
        showGraphics = StdIn.readBoolean();

        if (showGraphics)
            start();

        /* load sudoku and create queue */
        loadRandomlySelectedSudoku();

        /* initialize queue and enqueue initial board */
        Queue<int[][]> queue = new Queue<>();
        queue.enqueue(initialSudoku);

        /* initial sudoku */
        System.out.println("- Initial Sudoku -");
        printSudoku(initialSudoku);

        /* tracks current start to avoid repeats */
        int currentStart = 0;

        /* keep going all spaces filled */
        do {
            /* dequeue board */
            board = queue.dequeue();

            /* get next unfilled location */
            Location next = nextLocation(board, currentStart);

            /* fill in all possible numbers in next location */
            while (next.num != -1) {
                /* enqueue updated board */
                queue.enqueue(updateSudoku(copyBoard(board), next));
                /* update current start */
                currentStart = next.num;
                /* find next possible number */
                next = nextLocation(board, currentStart);
            }

            /* reset current start */
            currentStart = 0;

            /* graphics */
            if (showGraphics) {
                Thread.sleep(10);
                f.getContentPane().repaint();
            }

            /* keep going until all spaces filled */
        } while (board[lastSpaceToFill.x][lastSpaceToFill.y] == 0);

        /* shows final solved sudoku */
        if (board[lastSpaceToFill.x][lastSpaceToFill.y] != 0) {
            System.out.println("- Final Sudoku -");
            printSudoku(board);
        } else{
            System.out.println("No solution found.");
        }
    }

    /* graphics settings */
    private SudokuQ() {
        setPreferredSize(new Dimension(APPLICATION_WIDTH, APPLICATION_HEIGHT));
        setFocusable(true);
    }

    /* frame settings */
    private static void start() {
        f = new JFrame();
        f.setTitle(TITLE);
        f.setResizable(false);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        f.add(new SudokuQ(), null);
        f.pack();
        f.setVisible(true);
        f.setLocationRelativeTo(null);
    }

    /* utility function for copying board */
    private static int[][] copyBoard(int[][] board) {
        int[][] board2 = new int[9][9];
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board.length; j++)
                board2[i][j] = board[i][j];
        return board2;
    }

    /* updates sudoku */
    private static int[][] updateSudoku(int[][] board, Location loc) {
        board[loc.x][loc.y] = loc.num;
        return board;
    }

    /* finds next Location with a number bigger than specified minimum */
    private static Location nextLocation(int[][] sudoku, int min) {
        for (int j = 0; j < 9; j++)
            for (int i = 0; i < 9; i++) {
                /* if empty space */
                if (sudoku[i][j] == 0) {
                    /* if there's a number possible bigger than minimum, return */
                    if (canMove(sudoku, i, j, min) != -1)
                        return new Location(i, j, canMove(sudoku, i, j, min));
                    /* else return -1s */
                    return new Location(-1, -1, -1);
                }
            }
        /* else return -1s */
        return new Location(-1, -1, -1);
    }

    /* checks whether there is a possible number bigger than minimum in location (i, j) */
    private static int canMove(int[][] sudoku, int i, int j, int min) {
        for (int q = min + 1; q <= 9; q++)
            if (checkCol(sudoku, i, q) && checkRow(sudoku, j, q) && checkBox(sudoku, (i / 3) * 3, (j / 3) * 3, q))
                return q;
        return -1;
    }

    /* checks if column placement is valid */
    private static boolean checkCol(int[][] sudoku, int c, int num) {
        for (int i = 0; i < 9; i++)
            if (sudoku[c][i] == num)
                return false;
        return true;
    }

    /* checks if row placement is valid */
    private static boolean checkRow(int[][] sudoku, int r, int num) {
        for (int i = 0; i < 9; i++)
            if (sudoku[i][r] == num)
                return false;
        return true;
    }

    /* checks if box placement is valid */
    private static boolean checkBox(int[][] sudoku, int r, int c, int num) {
        for (int i = r; i < r + 3; i++)
            for (int j = c; j < c + 3; j++)
                if (sudoku[i][j] == num)
                    return false;
        return true;
    }

    /* prints sudoku */
    private static void printSudoku(int[][] sudoku) {
        for (int i = 0; i < sudoku.length; i++) {
            for (int[] ints : sudoku) System.out.print(ints[i] + " ");
            System.out.println();
        }
        System.out.println();
    }

    /* loads the text */
    private static void loadRandomlySelectedSudoku() throws FileNotFoundException {
        Scanner sc = new Scanner(new FileReader(TEXT_LOCATION));
        int total = sc.nextInt();

        Random random = new Random();
        int r = random.nextInt(total) / 9 * 9;

        for (int i = 0; i < r; i++)
            sc.nextLine();

        int rowCounter = 0;
        int i = 0;
        while (sc.hasNext() && rowCounter < 9) {
            initialSudoku[i][rowCounter] = sc.nextInt();
            i++;
            if (i == 9) {
                i = 0;
                rowCounter++;
            }
        }

        for (int z = 0; z < initialSudoku.length; z++)
            for (int j = 0; j < initialSudoku.length; j++) {
                if (initialSudoku[z][j] == 0)
                    lastSpaceToFill = new Location(z, j, 0);
            }
        sc.close();
    }

    /* location class */
    private static class Location {
        int x;
        int y;
        int num;

        Location(int x, int y, int num) {
            this.x = x;
            this.y = y;
            this.num = num;
        }
    }

    /* graphics */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board.length; j++) {
                /* modifiable tiles */
                if (initialSudoku[i][j] == 0) {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(BOX_DIMENSIONS * i, BOX_DIMENSIONS * j, BOX_DIMENSIONS, BOX_DIMENSIONS);
                    int display = 1;
                    /* mini squares for viewing purposes */
                    for (int l = 0; l < 3; l++)
                        for (int k = 0; k < 3; k++) {
                            g.setColor(Color.BLACK);
                            g.drawRect(BOX_DIMENSIONS * i + k * BOX_DIMENSIONS / 3, BOX_DIMENSIONS * j + l * BOX_DIMENSIONS / 3, BOX_DIMENSIONS / 3, BOX_DIMENSIONS / 3);
                            if (display == board[i][j]) {
                                drawString(g, Integer.toString(display++), new Rectangle(BOX_DIMENSIONS * i + k * BOX_DIMENSIONS / 3, BOX_DIMENSIONS * j + l * BOX_DIMENSIONS / 3, BOX_DIMENSIONS / 3, BOX_DIMENSIONS / 3), new Font("Helvetica", Font.BOLD, 30), Color.RED);
                            } else
                                drawString(g, Integer.toString(display++), new Rectangle(BOX_DIMENSIONS * i + k * BOX_DIMENSIONS / 3, BOX_DIMENSIONS * j + l * BOX_DIMENSIONS / 3, BOX_DIMENSIONS / 3, BOX_DIMENSIONS / 3), new Font("Helvetica", Font.PLAIN, 30), Color.BLACK);
                        }
                    /* unmodifiable tiles */
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(BOX_DIMENSIONS * i, BOX_DIMENSIONS * j, BOX_DIMENSIONS, BOX_DIMENSIONS);
                    drawString(g, Integer.toString(board[i][j]), new Rectangle(BOX_DIMENSIONS * i, BOX_DIMENSIONS * j, BOX_DIMENSIONS, BOX_DIMENSIONS), new Font("Helvetica", Font.PLAIN, 30), Color.black);
                }
                g.setColor(Color.BLACK);
                g.drawRect(BOX_DIMENSIONS * i, BOX_DIMENSIONS * j, BOX_DIMENSIONS, BOX_DIMENSIONS);
            }

        /* thick lines */
        g.setColor(Color.BLACK);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(10));
        g.drawLine(3 * BOX_DIMENSIONS, 0, 3 * BOX_DIMENSIONS, 9 * BOX_DIMENSIONS);
        g.drawLine(6 * BOX_DIMENSIONS, 0, 6 * BOX_DIMENSIONS, 9 * BOX_DIMENSIONS);
        g.drawLine(0, 3 * BOX_DIMENSIONS, 9 * BOX_DIMENSIONS, 3 * BOX_DIMENSIONS);
        g.drawLine(0, 6 * BOX_DIMENSIONS, 9 * BOX_DIMENSIONS, 6 * BOX_DIMENSIONS);
    }

    /* drawing the text with right size */
    private void drawString(Graphics g, String text, Rectangle rect, Font font, Color color) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.setColor(color);
        g.drawString(text, x, y);
    }
}
