import java.util.*;

public class EightPuzzleBFS {
    static final int[] GOAL = {1, 2, 3, 4, 5, 6, 7, 8, 0};
    static final int[][] MOVES = {
        {1, 3},          // 0
        {0, 2, 4},       // 1
        {1, 5},          // 2
        {0, 4, 6},       // 3
        {1, 3, 5, 7},    // 4
        {2, 4, 8},       // 5
        {3, 7},          // 6
        {4, 6, 8},       // 7
        {5, 7}           // 8
    };

    static class Node {
        int[] state;
        int zeroIndex;
        String path;

        Node(int[] state, int zeroIndex, String path) {
            this.state = state;
            this.zeroIndex = zeroIndex;
            this.path = path;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter initial puzzle state (0 for empty):");
        int[] start = new int[9];
        int zeroIndex = -1;
        for (int i = 0; i < 9; i++) {
            start[i] = sc.nextInt();
            if (start[i] == 0) zeroIndex = i;
        }

        if (!isSolvable(start)) {
            System.out.println("This puzzle is unsolvable.");
            return;
        }

        bfs(start, zeroIndex);
    }

    static void bfs(int[] start, int zeroIndex) {
        Queue<Node> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        Node root = new Node(start, zeroIndex, "");
        queue.add(root);
        visited.add(Arrays.toString(start));

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (Arrays.equals(current.state, GOAL)) {
                System.out.println("Solved in " + current.path.length() + " moves.");
                replayMoves(start, current.path);
                return;
            }

            for (int move : MOVES[current.zeroIndex]) {
                int[] newState = current.state.clone();
                newState[current.zeroIndex] = newState[move];
                newState[move] = 0;

                String key = Arrays.toString(newState);
                if (!visited.contains(key)) {
                    visited.add(key);
                    queue.add(new Node(newState, move, current.path + moveDirection(current.zeroIndex, move)));
                }
            }
        }

        System.out.println("No solution found.");
    }

    static void replayMoves(int[] start, String path) {
        int[] board = start.clone();
        int zero = -1;
        for (int i = 0; i < 9; i++) if (board[i] == 0) zero = i;

        System.out.println("\nInitial State:");
        printBoard(board);

        for (char dir : path.toCharArray()) {
            int move = moveIndex(zero, dir);
            board[zero] = board[move];
            board[move] = 0;
            zero = move;
            System.out.println("Move: " + dir);
            printBoard(board);
        }
    }

    static String moveDirection(int from, int to) {
        int diff = to - from;
        if (diff == -3) return "U";
        if (diff == 3) return "D";
        if (diff == -1) return "L";
        return "R";
    }

    static int moveIndex(int zero, char dir) {
        switch (dir) {
            case 'U': return zero - 3;
            case 'D': return zero + 3;
            case 'L': return zero - 1;
            case 'R': return zero + 1;
        }
        return zero;
    }

    static void printBoard(int[] b) {
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) System.out.println("+---+---+---+");
            System.out.print("| " + (b[i] == 0 ? " " : b[i]) + " ");
            if (i % 3 == 2) System.out.println("|");
        }
        System.out.println("+---+---+---+\n");
    }

    static boolean isSolvable(int[] puzzle) {
        int inv = 0;
        for (int i = 0; i < 8; i++)
            for (int j = i + 1; j < 9; j++)
                if (puzzle[i] != 0 && puzzle[j] != 0 && puzzle[i] > puzzle[j])
                    inv++;
        return inv % 2 == 0;
    }
}
