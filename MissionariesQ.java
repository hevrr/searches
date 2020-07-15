
public class MissionariesQ {

    /* number of cannibals and missionaries */
    private int cannibals;
    private int missionaries;

    /* the final board position used for backtracking later */
    private Position finalBoard;

    /* constructor */
    public MissionariesQ(int cLeft, int mLeft) {
        this.cannibals = cLeft;
        this.missionaries = mLeft;
        this.finalBoard = solve();
    }

    /* returns whether problem is solvable */
    public boolean isSolvable() {
        return finalBoard != null;
    }

    /* solves problem with given cannibals and missionaries, returns null if unable */
    /* uses queue / BFS to solve */
    public Position solve() {
        Queue<Position> queue = new Queue<>();
        LinkedList<Position> tracker = new LinkedList<>();

        /* enqueue original state */
        queue.enqueue(new Position(cannibals, 0, missionaries, 0, Position.Boat.LEFT_POSITION, "Original state."));

        /* keep going until either queue is empty or solution has been reached */
        while (!queue.isEmpty() && !queue.peek().reachedGoal()) {
            /* dequeue current position and add to tracker */
            Position position = queue.dequeue();
            tracker.add(position);

            /* go through all move possibilities */
            for (Position p : position.movePossibilities())
                /* to avoid repeats and infinite looping */
                if (position.isValid() && !queue.contains(p) && !tracker.contains(p))
                    queue.enqueue(p);
        }
        /* return result */
        return queue.peek();
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
