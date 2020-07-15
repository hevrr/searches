import java.util.Iterator;

public class Position {
    /* store information for number of cannibals, missionaries, and boat position */
    int cannibalsLeft;
    int cannibalsRight;
    int missionariesLeft;
    int missionariesRight;
    Boat boatPosition;

    /* for backtracking */
    Position parentPosition;

    /* records position in string */
    String displayedText;

    /* constructor */
    Position(int cLeft, int cRight, int mLeft, int mRight, Boat boatPosition, String displayedText) {
        cannibalsLeft = cLeft;
        cannibalsRight = cRight;
        missionariesLeft = mLeft;
        missionariesRight = mRight;
        parentPosition = null;
        this.boatPosition = boatPosition;
        this.displayedText = displayedText;
    }

    /* constructor with parent position */
    Position(int cLeft, int cRight, int mLeft, int mRight, Boat boatPosition, String displayedText, Position parentPosition) {
        cannibalsLeft = cLeft;
        cannibalsRight = cRight;
        missionariesLeft = mLeft;
        missionariesRight = mRight;
        this.boatPosition = boatPosition;
        this.displayedText = displayedText;
        this.parentPosition = parentPosition;
    }

    /* whether all cannibals and all missionaries have been moved to the right */
    boolean reachedGoal() {
        return cannibalsLeft == 0 && missionariesLeft == 0;
    }

    /* whether the position is valid and follows rules */
    /* must have more missionaries than cannibals in both areas */
    boolean isValid() {
        return missionariesLeft >= 0 && missionariesRight >= 0 && cannibalsLeft >= 0 && cannibalsRight >= 0
                && (missionariesLeft == 0 || missionariesLeft >= cannibalsLeft)
                && (missionariesRight == 0 || missionariesRight >= cannibalsRight);
    }

    /* returns LinkedList containing all valid moves moving missionaries and/or cannibals */
    /* did not implement with iterable because I needed to set the parent position here, and we cannot reference "this" Position in iterable */
    public LinkedList<Position> movePossibilities() {
        LinkedList<Position> positions = new LinkedList<>();

        /* if boat position is left of bank */
        if (boatPosition == Boat.LEFT_POSITION) {
            positions.add(new Position(cannibalsLeft - 2, cannibalsRight + 2, missionariesLeft, missionariesRight, Boat.RIGHT_POSITION, "2 cannibals travel to the right of the river.", this));
            positions.add(new Position(cannibalsLeft, cannibalsRight, missionariesLeft - 2, missionariesRight + 2, Boat.RIGHT_POSITION, "2 missionaries travel to the right of the river.", this));
            positions.add(new Position(cannibalsLeft - 1, cannibalsRight + 1, missionariesLeft - 1, missionariesRight + 1, Boat.RIGHT_POSITION, "1 missionary and 1 cannibal travel to the right of the river.", this));
            positions.add(new Position(cannibalsLeft - 1, cannibalsRight + 1, missionariesLeft, missionariesRight, Boat.RIGHT_POSITION, "1 cannibal travels to the right of the river.", this));
            positions.add(new Position(cannibalsLeft, cannibalsRight, missionariesLeft - 1, missionariesRight + 1, Boat.RIGHT_POSITION, "1 missionary travels to the right of the river.", this));

            /* if boat position is right of bank */
        } else {
            positions.add(new Position(cannibalsLeft + 2, cannibalsRight - 2, missionariesLeft, missionariesRight, Boat.LEFT_POSITION, "2 cannibals travel to the left of the river.", this));
            positions.add(new Position(cannibalsLeft, cannibalsRight, missionariesLeft + 2, missionariesRight - 2, Boat.LEFT_POSITION, "2 missionaries travel to the left of the river.", this));
            positions.add(new Position(cannibalsLeft + 1, cannibalsRight - 1, missionariesLeft + 1, missionariesRight - 1, Boat.LEFT_POSITION, "1 missionary and 1 cannibal travel to the left of the river.", this));
            positions.add(new Position(cannibalsLeft + 1, cannibalsRight - 1, missionariesLeft, missionariesRight, Boat.LEFT_POSITION, "1 cannibal travels to the left of the river.", this));
            positions.add(new Position(cannibalsLeft, cannibalsRight, missionariesLeft + 1, missionariesRight - 1, Boat.LEFT_POSITION, "1 missionary travels to the left of the river.", this));
        }
        return positions;
    }

    /* needed for contains function in solver */
    @Override
    public boolean equals(Object obj) {
        Position p = (Position) obj;
        return p.cannibalsLeft == cannibalsLeft && p.missionariesLeft == missionariesLeft && p.cannibalsRight == cannibalsRight
                && p.missionariesRight == missionariesRight && p.boatPosition == boatPosition;
    }

    /* toString method */
    public String toString() {
        return "Missionaries: " + missionariesLeft + "\t~~ Missionaries: " + missionariesRight + "\nCannibals: " + cannibalsLeft + "\t~~ Cannibals: " + cannibalsRight + "\n";
    }

    /* boat position: left/right */
    enum Boat {
        LEFT_POSITION, RIGHT_POSITION
    }
}
