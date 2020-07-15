import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MissionariesView extends JPanel implements Runnable {

    /* graphics */
    private static final String TITLE = "MissionariesView.java";
    private static final int APPLICATION_WIDTH = 640;
    private static final int APPLICATION_HEIGHT = 230;
    private static JLabel backgroundImage;
    private static JFrame f;

    /* solution and index tracker for displaying */
    private static LinkedList<Position> solution;
    private static int current = 0;

    /* main */
    public static void main(String[] args) {
        StdOut.println("- Solves the missionaries and cannibals problem");
        StdOut.println("- Please enter < 4 for either missionary/cannibal due to graphic constraints");

        StdOut.print("\nEnter number of cannibals: ");
        int cannibals = StdIn.readInt();
        StdOut.print("Enter number of missionaries: ");
        int missionaries = StdIn.readInt();

        StdOut.println("\nSolve through:\n[0]: DFS\n[1]: BFS");
        StdOut.print("\n?: ");

        int input = StdIn.readInt();

        /* solve using specified method */
        if (cannibals < 4 && missionaries < 4) {
            if (input == 0) {
                MissionariesS missionariesStack = new MissionariesS(cannibals, missionaries);
                /* if solvable, set solution and run graphics */
                if (missionariesStack.isSolvable()) {
                    solution = missionariesStack.solution();
                    SwingUtilities.invokeLater(MissionariesView::start);
                } else {
                    StdOut.println("Not solvable.");
                }
            } else {
                MissionariesQ missionariesQueue = new MissionariesQ(cannibals, missionaries);
                /* if solvable, set solution and run graphics */
                if (missionariesQueue.isSolvable()) {
                    solution = missionariesQueue.solution();
                    SwingUtilities.invokeLater(MissionariesView::start);
                } else {
                    StdOut.println("Not solvable.");
                }
            }
        } else {
            StdOut.println("Not < 4.");
        }
    }

    /* initialize */
    private MissionariesView() {
        setPreferredSize(new Dimension(APPLICATION_WIDTH, APPLICATION_HEIGHT));
        setFocusable(true);
        initializeJObjects();
    }

    /* start graphcis */
    private static void start() {
        f = new JFrame();
        f.setTitle(TITLE);
        f.setResizable(false);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        f.add(new MissionariesView(), BorderLayout.CENTER);
        f.pack();
        f.setVisible(true);
        f.setLocationRelativeTo(null);
    }

    /* initialize JObjects */
    private void initializeJObjects() {
        /* displays text explaining movement */
        JTextPane displayText = new JTextPane();
        displayText.setBounds(60, 195, 520, 23);
        SimpleAttributeSet attribute = new SimpleAttributeSet();
        StyleConstants.setAlignment(attribute, StyleConstants.ALIGN_CENTER);
        displayText.setParagraphAttributes(attribute, true);
        displayText.setText(solution.get(current).displayedText);
        displayText.setEditable(false);
        f.add(displayText);

        /* left button */
        JButton left = new JButton("<");
        left.setBounds(20, 190, 35, 33);
        left.addActionListener(e -> {
            /* change display if valid */
            if (valid(current - 1))
                current--;
            drawCanvas(solution.get(current));
            displayText.setText(solution.get(current).displayedText);
        });
        f.add(left);

        /* right button */
        JButton right = new JButton(">");
        right.setBounds(585, 190, 35, 33);
        right.addActionListener(e -> {
            /* change display if valid */
            if (valid(current + 1))
                current++;
            drawCanvas(solution.get(current));
            displayText.setText(solution.get(current).displayedText);
        });
        f.add(right);

        /* all image JLabels */
        cannibalsLeft = new JLabel();
        f.add(cannibalsLeft);
        missionariesLeft = new JLabel();
        f.add(missionariesLeft);
        cannibalsRight = new JLabel();
        f.add(cannibalsRight);
        missionariesRight = new JLabel();
        f.add(missionariesRight);

        /* background image JLabel */
        backgroundImage = new JLabel();
        backgroundImage.setBounds(20, 20, 600, 166);
        f.add(backgroundImage);

        /* draws canvas */
        drawCanvas(solution.get(current));
    }

    /* checks whether index is within bounds of solution */
    private static boolean valid(int index) {
        return index >= 0 && index < solution.size();
    }

    private static JLabel cannibalsLeft;
    private static JLabel missionariesLeft;
    private static JLabel cannibalsRight;
    private static JLabel missionariesRight;

    /* draws graphics given position */
    private static void drawCanvas(Position position) {
        /* gets and updates background image depending on left/right boat position */
        String path;
        if (position.boatPosition == Position.Boat.LEFT_POSITION)
            path = "src/images/Left.png";
        else
            path = "src/images/Right.png";
        backgroundImage.setIcon(new ImageIcon(path));

        /* cannibals left drawing */
        String path2 = "src/images/C" + position.cannibalsLeft + ".png";
        cannibalsLeft.setBounds(40, 40, position.cannibalsLeft * 50, 50);
        cannibalsLeft.setIcon(new ImageIcon(path2));

        /* missionaries left drawing */
        String path3 = "src/images/M" + position.missionariesLeft + ".png";
        missionariesLeft.setBounds(40, 100, position.missionariesLeft * 50, 50);
        missionariesLeft.setIcon(new ImageIcon(path3));

        /* cannibals right drawing */
        String path4 = "src/images/C" + position.cannibalsRight + ".png";
        cannibalsRight.setBounds(360, 30, position.cannibalsRight * 50, 50);
        cannibalsRight.setIcon(new ImageIcon(path4));

        /* missionaries right drawing */
        String path5 = "src/images/M" + position.missionariesRight + ".png";
        missionariesRight.setBounds(450, 85, position.missionariesRight * 50, 50);
        missionariesRight.setIcon(new ImageIcon(path5));
    }

    @Override
    public void run() {
    }
}
