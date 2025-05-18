import java.util.*;

public class BestFirst8Puzzle {
    static class PuzzleNode implements Comparable<PuzzleNode> {
        int[][] board;
        int h; // heuristic cost to goal
        PuzzleNode parent;

        PuzzleNode(int[][] board, PuzzleNode parent) {
            this.board = board;
            this.h = calculateHeuristic(board);
            this.parent = parent;
        }

        private int calculateHeuristic(int[][] board) {
            int distance = 0;
            int[][] goal = {{1,2,3},{4,5,6},{7,8,0}};
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int val = board[i][j];
                    if (val != 0) {
                        int goalX = (val - 1) / 3;
                        int goalY = (val - 1) % 3;
                        distance += Math.abs(i - goalX) + Math.abs(j - goalY);
                    }
                }
            }
            return distance;
        }

        @Override
        public int compareTo(PuzzleNode o) {
            return Integer.compare(this.h, o.h);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof PuzzleNode) {
                return Arrays.deepEquals(board, ((PuzzleNode) obj).board);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Arrays.deepHashCode(board);
        }
    }

    private static final int[][] GOAL = {
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 0}
    };

    private static List<PuzzleNode> getNeighbors(PuzzleNode node) {
        List<PuzzleNode> neighbors = new ArrayList<>();
        int[][] board = node.board;
        int zeroX = 0, zeroY = 0;

        // Find zero position
        outer:
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == 0) {
                    zeroX = i;
                    zeroY = j;
                    break outer;
                }

        int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
        for (int[] dir : dirs) {
            int newX = zeroX + dir[0], newY = zeroY + dir[1];
            if (newX >= 0 && newX < 3 && newY >= 0 && newY < 3) {
                int[][] newBoard = deepCopy(board);
                newBoard[zeroX][zeroY] = newBoard[newX][newY];
                newBoard[newX][newY] = 0;
                neighbors.add(new PuzzleNode(newBoard, node));
            }
        }

        return neighbors;
    }

    private static int[][] deepCopy(int[][] board) {
        int[][] copy = new int[3][3];
        for (int i = 0; i < 3; i++)
            copy[i] = board[i].clone();
        return copy;
    }

    public static void solve(int[][] start) {
        PriorityQueue<PuzzleNode> open = new PriorityQueue<>();
        Set<PuzzleNode> closed = new HashSet<>();

        PuzzleNode startNode = new PuzzleNode(start, null);
        open.add(startNode);

        while (!open.isEmpty()) {
            PuzzleNode current = open.poll();

            if (Arrays.deepEquals(current.board, GOAL)) {
                printSolution(current);
                return;
            }

            closed.add(current);

            for (PuzzleNode neighbor : getNeighbors(current)) {
                if (!closed.contains(neighbor)) {
                    open.add(neighbor);
                }
            }
        }

        System.out.println("No solution found.");
    }

    private static void printSolution(PuzzleNode node) {
        List<PuzzleNode> path = new ArrayList<>();
        while (node != null) {
            path.add(node);
            node = node.parent;
        }
        Collections.reverse(path);
        int step = 0;
        for (PuzzleNode n : path) {
            System.out.println("Step " + step++ + ":");
            printBoard(n.board);
        }
        System.out.println("Total moves: " + (path.size() - 1));
    }

    private static void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int num : row) {
                System.out.print((num == 0 ? " " : num) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[][] start = new int[3][3];
        System.out.println("Enter the 8-puzzle (use 0 for blank):");
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                start[i][j] = sc.nextInt();
        solve(start);
    }
}
