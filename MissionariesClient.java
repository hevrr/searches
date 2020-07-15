import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import javax.swing.*;

public class MissionariesClient {

    public static void main(String[] args) {
        StdOut.println("- Solves the missionaries and cannibals problem");
        StdOut.println("- For print solution: ~~ represents river");

        StdOut.print("\nEnter number of cannibals: ");
        int cannibals = StdIn.readInt();
        StdOut.print("Enter number of missionaries: ");
        int missionaries = StdIn.readInt();

        StdOut.println("\nSolve through:\n[0]: DFS\n[1]: BFS");
        StdOut.print("\n?: ");

        int input = StdIn.readInt();

        if (input == 0) {
            MissionariesS missionariesStack = new MissionariesS(cannibals, missionaries);
            /* if solvable, set solution and run graphics */
            if (missionariesStack.isSolvable()) {
                missionariesStack.printSolution();
            } else {
                StdOut.println("Not solvable.");
            }
        } else {
            MissionariesQ missionariesQueue = new MissionariesQ(cannibals, missionaries);
            /* if solvable, set solution and run graphics */
            if (missionariesQueue.isSolvable()) {
               missionariesQueue.printSolution();
            } else {
                StdOut.println("Not solvable.");
            }
        }
    }
}
