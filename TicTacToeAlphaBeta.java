import java.util.Scanner;

public class TicTacToeAlphaBeta {
    static char[][] board = {
        {' ', ' ', ' '},
        {' ', ' ', ' '},
        {' ', ' ', ' '}
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Tic Tac Toe with Alpha-Beta Pruning!");
        printBoard();

        while (true) {
            // Human move
            playerMove(scanner);
            if (checkWin('X')) {
                printBoard();
                System.out.println("You win!");
                break;
            }
            if (isBoardFull()) {
                printBoard();
                System.out.println("It's a draw!");
                break;
            }

            // Computer move
            System.out.println("Computer is making a move...");
            computerMove();
            printBoard();
            if (checkWin('O')) {
                System.out.println("Computer wins!");
                break;
            }
            if (isBoardFull()) {
                System.out.println("It's a draw!");
                break;
            }
        }
        scanner.close();
    }

    // Prints the current board
    public static void printBoard() {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println("\n-------------");
        }
    }

    // Human move – input row and column (1-indexed) then convert to 0-indexed.
    public static void playerMove(Scanner scanner) {
        int row, col;
        while (true) {
            System.out.print("Enter your move (row [1-3] and column [1-3]): ");
            row = scanner.nextInt() - 1;
            col = scanner.nextInt() - 1;
            if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ') {
                board[row][col] = 'X';
                break;
            } else {
                System.out.println("Invalid move! Try again.");
            }
        }
    }

    // Computer move using minimax with alpha-beta pruning.
    public static void computerMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = 'O';
                    int score = minimax(0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    board[i][j] = ' ';  // Undo move
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new int[]{i, j};
                    }
                }
            }
        }
        board[bestMove[0]][bestMove[1]] = 'O';
        System.out.println("Computer chose position: (" + (bestMove[0] + 1) + ", " + (bestMove[1] + 1) + ")");
    }

    // Minimax algorithm with Alpha-Beta pruning.
    public static int minimax(int depth, boolean isMaximizing, int alpha, int beta) {
        if (checkWin('O')) {
            return 10 - depth;
        }
        if (checkWin('X')) {
            return depth - 10;
        }
        if (isBoardFull()) {
            return 0;
        }

        if (isMaximizing) { // Computer's turn: try to maximize score
            int maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'O';
                        int eval = minimax(depth + 1, false, alpha, beta);
                        board[i][j] = ' ';  // Undo move
                        maxEval = Math.max(maxEval, eval);
                        alpha = Math.max(alpha, eval);
                        if (beta <= alpha) {
                            return maxEval;  // Beta cut-off
                        }
                    }
                }
            }
            return maxEval;
        } else { // Human's turn: try to minimize score
            int minEval = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'X';
                        int eval = minimax(depth + 1, true, alpha, beta);
                        board[i][j] = ' ';  // Undo move
                        minEval = Math.min(minEval, eval);
                        beta = Math.min(beta, eval);
                        if (beta <= alpha) {
                            return minEval;  // Alpha cut-off
                        }
                    }
                }
            }
            return minEval;
        }
    }

    // Checks for a win condition for a given player.
    public static boolean checkWin(char player) {
        // Check rows and columns.
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
                return true;
            }
        }
        // Check diagonals.
        if ((board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
            (board[0][2] == player && board[1][1] == player && board[2][0] == player)) {
            return true;
        }
        return false;
    }

    // Returns true if the board is full.
    public static boolean isBoardFull() {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == ' ') return false;
            }
        }
        return true;
    }
}
