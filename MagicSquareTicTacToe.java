import java.util.*;

public class MagicSquareTicTacToe {
    static char[][] board = {
        {'_', '_', '_'},
        {'_', '_', '_'},
        {'_', '_', '_'}
    };

    static int[][] magicSquare = {
        {8, 1, 6},
        {3, 5, 7},
        {4, 9, 2}
    };

    static List<Integer> playerMoves = new ArrayList<>();
    static List<Integer> computerMoves = new ArrayList<>();
    static Random rand = new Random();

    static void displayBoard() {
        for (char[] row : board) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    static int[] getRowCol(int move) {
        int row = (move - 1) / 3;
        int col = (move - 1) % 3;
        return new int[]{row, col};
    }

    static int getMagicNumber(int row, int col) {
        return magicSquare[row][col];
    }

    static boolean checkWin(List<Integer> moves) {
        int size = moves.size();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                for (int k = j + 1; k < size; k++) {
                    if (moves.get(i) + moves.get(j) + moves.get(k) == 15) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    static boolean isFull() {
        for (char[] row : board) {
            for (char c : row) {
                if (c == '_') return false;
            }
        }
        return true;
    }

    static List<Integer> getAvailableMoves() {
        List<Integer> available = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            int[] rc = getRowCol(i);
            if (board[rc[0]][rc[1]] == '_') {
                available.add(i);
            }
        }
        return available;
    }

    static void playerMove(Scanner scanner) {
        while (true) {
            System.out.print("Enter your move (1-9): ");
            int move = scanner.nextInt();
            if (move < 1 || move > 9) {
                System.out.println("Invalid input!");
                continue;
            }
            int[] rc = getRowCol(move);
            if (board[rc[0]][rc[1]] == '_') {
                board[rc[0]][rc[1]] = 'X';
                playerMoves.add(getMagicNumber(rc[0], rc[1]));
                break;
            } else {
                System.out.println("Cell already taken. Try again.");
            }
        }
    }

    static void computerMove() {
        List<Integer> available = getAvailableMoves();

        // 1. Try to win
        for (int move : available) {
            int[] rc = getRowCol(move);
            int magic = getMagicNumber(rc[0], rc[1]);
            computerMoves.add(magic);
            if (checkWin(computerMoves)) {
                board[rc[0]][rc[1]] = 'O';
                return;
            }
            computerMoves.remove(computerMoves.size() - 1); // undo
        }

        // 2. Try to block player
        for (int move : available) {
            int[] rc = getRowCol(move);
            int magic = getMagicNumber(rc[0], rc[1]);
            playerMoves.add(magic);
            if (checkWin(playerMoves)) {
                playerMoves.remove(playerMoves.size() - 1);
                board[rc[0]][rc[1]] = 'O';
                computerMoves.add(magic);
                return;
            }
            playerMoves.remove(playerMoves.size() - 1); // undo
        }

        // 3. Smart placement: center, corners, sides
        int[] preferredMoves = {5, 1, 3, 7, 9, 2, 4, 6, 8}; // magic square positions
        for (int move : preferredMoves) {
            int[] rc = getRowCol(move);
            if (board[rc[0]][rc[1]] == '_') {
                board[rc[0]][rc[1]] = 'O';
                computerMoves.add(getMagicNumber(rc[0], rc[1]));
                return;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Tic Tac Toe using Magic Square!");
        System.out.println("Board positions:");
        System.out.println("1 2 3\n4 5 6\n7 8 9\n");

        while (true) {
            displayBoard();
            playerMove(scanner);

            if (checkWin(playerMoves)) {
                displayBoard();
                System.out.println("🎉 You win!");
                break;
            }
            if (isFull()) {
                displayBoard();
                System.out.println("🤝 It's a tie!");
                break;
            }

            computerMove();
            if (checkWin(computerMoves)) {
                displayBoard();
                System.out.println("💻 Computer wins!");
                break;
            }
            if (isFull()) {
                displayBoard();
                System.out.println("🤝 It's a tie!");
                break;
            }
        }
        scanner.close();
    }
}
