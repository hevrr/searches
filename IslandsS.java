import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class IslandsS {

    /* text location */
    private static final String TEXT_LOCATION = "src/Island.txt";

    /* boards */
    private static int[][] map;
    private static boolean[][] traversed;

    /* directions */
    private static final Location[] DIRECTIONS = {new Location(-1, 0), new Location(-1, 1), new Location(0, 1), new Location(1, 1), new Location(1, 0), new Location(1, -1), new Location(0, -1), new Location(-1, -1)};

    /* mains */
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("- Counts the number of islands in the map through DFS");
        System.out.println("- '1' denotes land, '0' denotes water: any land linked together is an island");

        /* initialize and load */
        loadMap();
        System.out.println("\n- Map -");
        printMap();

        int islandCount = 0;

        /* go through all coordinates in map */
        for (int i = 0; i < map.length; i++)
            for (int j = 0; j < map.length; j++) {

                /* check if it is land and if it hasn't been visited */
                if (map[i][j] == 1 && !traversed[i][j]) {
                    /* travel through linked land and update island count */
                    depth(new Location(i, j, -1));
                    islandCount++;
                }
            }
        System.out.println("Number of islands: " + islandCount);
    }

    /* depth through stack */
    private static void depth(Location location) {
        Stack<Location> stack = new Stack<>();
        stack.push(location);

        /* until stack is empty */
        while (!stack.isEmpty()) {
            Location current = stack.peek();
            /* tracks whether a move is possible */
            boolean movePossible = false;
            /* update traversed tracker */
            traversed[current.x][current.y] = true;

            /* iterate through the different directions */
            for (int i = current.direction + 1; i < DIRECTIONS.length; i++) {
                /* if move is valid */
                if (isValid(current.x + DIRECTIONS[i].x, current.y + DIRECTIONS[i].y)) {
                    /* update current direction to eliminate repeats */
                    stack.peek().direction = i;
                    /* push new location */
                    stack.push(new Location(current.x + DIRECTIONS[i].x, current.y + DIRECTIONS[i].y, -1));
                    /* update that a move has been made */
                    movePossible = true;
                    break;
                }
            }
            /* if no moves possible, pop */
            if (!movePossible)
                stack.pop();
        }
    }

    /* if given coordinates are within bounds, have not been traveled to, and is a land piece */
    private static boolean isValid(int x, int y) {
        return x >= 0 && x < map.length && y >= 0 && y < map.length && !traversed[x][y] && map[x][y] == 1;
    }

    /* prints map */
    private static void printMap() {
        for (int i = 0; i < map.length; i++) {
            for (int[] ints : map) System.out.print(ints[i] + " ");
            System.out.println();
        }
        System.out.println();
    }

    /* loads map */
    private static void loadMap() throws FileNotFoundException {
        Scanner sc = new Scanner(new FileReader(TEXT_LOCATION));
        int dimension = sc.nextInt();
        map = new int[dimension][dimension];
        traversed = new boolean[map.length][map.length];

        int rowCounter = 0;
        int i = 0;
        while (sc.hasNext() && rowCounter < dimension) {
            map[i][rowCounter] = sc.nextInt();
            i++;
            if (i == dimension) {
                i = 0;
                rowCounter++;
            }
        }
    }

    /* location class */
    private static class Location {
        int x;
        int y;
        int direction;

        Location(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Location(int x, int y, int direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }
    }
}
