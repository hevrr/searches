
public class MissionariesS {

    /* number of cannibals and missionaries */
    private int cannibals;
    private int missionaries;

    /* the final board position used for backtracking later */
    private Position finalBoard;

    /* constructor */
    public MissionariesS(int cLeft, int mLeft) {
        this.cannibals = cLeft;
        this.missionaries = mLeft;
        this.finalBoard = solve();
    }

    /* returns whether problem is solvable */
    public boolean isSolvable() {
        return finalBoard != null;
    }

    /* solves problem with given cannibals and missionaries, returns null if unable */
    /* uses stack / DFS to solve */
    public Position solve() {
        Stack<Position> stack = new Stack<>();
        LinkedList<Position> tracker = new LinkedList<>();

        /* push original state */
        stack.push(new Position(cannibals, 0, missionaries, 0, Position.Boat.LEFT_POSITION, "Original state."));

        /* keep going until either stack is empty or solution has been reached */
        while (!stack.isEmpty() && !stack.peek().reachedGoal()) {

            /* boolean for whether a move is possible */
            boolean nextMove = false;

            /* goes through all move possibilities */
            for (Position position : stack.peek().movePossibilities())

                /* if stack does not already contain position */
                /* to avoid repeats and infinite looping */
                if (position.isValid() && !stack.contains(position) && !tracker.contains(position)) {
                    /* valid move found, pushed */
                    nextMove = true;
                    stack.push(position);
                    break;
                }

            /* if no valid move found, pop from stack */
            if (!nextMove)
                tracker.add(stack.pop());
        }
        /* return result */
        /* avoid null pointer */
        if (stack.isEmpty())
            return null;
        else
            return stack.peek();
    }

    /* returns solution in form of linked list of positions in order */
    public LinkedList<Position> solution() {
        /* initialize */
        LinkedList<Position> list = new LinkedList<>();
        Stack<Position> stack = new Stack<>();

        /* create temporary Position node */
        Position solution = finalBoard;

        /* while position exists */
        while (solution != null) {
            /* push solution */
            stack.push(solution);
            /* go to parent */
            solution = solution.parentPosition;
        }

        /* pop off into list */
        while (!stack.isEmpty())
            list.add(stack.pop());

        /* return list */
        return list;
    }

    /* prints solution */
    public void printSolution() {
        /* initialize */
        Stack<Position> stack = new Stack<>();

        /* create temporary Position node */
        Position solution = finalBoard;

        /* while position exists */
        while (solution != null) {
            /* push solution */
            stack.push(solution);
            /* go to parent */
            solution = solution.parentPosition;
        }

        /* step tracker */
        int step = 1;

        /* pop and print */
        while (!stack.isEmpty()) {
            System.out.println("Step: " + step);
            System.out.println(stack.pop());
        }
    }
}
