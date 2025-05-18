import java.util.Scanner;

public class TicTacToe {
    static char[][] board = {
        {'_', '_', '_'},
        {'_', '_', '_'},
        {'_', '_', '_'}
    };

    // Display the board
    static void display() {
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Check for winner
    static char checkWin() {
        for (int i = 0; i < 3; i++) {
            // Rows
            if (board[i][0] != '_' && board[i][0] == board[i][1] && board[i][1] == board[i][2])
                return board[i][0];
            // Columns
            if (board[0][i] != '_' && board[0][i] == board[1][i] && board[1][i] == board[2][i])
                return board[0][i];
        }

        // Diagonals
        if (board[0][0] != '_' && board[0][0] == board[1][1] && board[1][1] == board[2][2])
            return board[0][0];
        if (board[0][2] != '_' && board[0][2] == board[1][1] && board[1][1] == board[2][0])
            return board[0][2];

        return '_'; // No winner
    }

    // Check if board is full
    static boolean isBoardFull() {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == '_') return false;
            }
        }
        return true;
    }

    // Computer move
    static void computerMove() {
        // Try to win
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '_') {
                    board[i][j] = 'O';
                    if (checkWin() == 'O') return;
                    board[i][j] = '_'; // Undo
                }
            }
        }

        // Try to block player
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '_') {
                    board[i][j] = 'X';
                    if (checkWin() == 'X') {
                        board[i][j] = 'O';
                        return;
                    }
                    board[i][j] = '_'; // Undo
                }
            }
        }

        // Best move: center
        if (board[1][1] == '_') {
            board[1][1] = 'O';
            return;
        }

        // Then corners
        int[][] corners = {{0, 0}, {0, 2}, {2, 0}, {2, 2}};
        for (int[] move : corners) {
            int i = move[0], j = move[1];
            if (board[i][j] == '_') {
                board[i][j] = 'O';
                return;
            }
        }

        // Then edges
        int[][] edges = {{0, 1}, {1, 0}, {1, 2}, {2, 1}};
        for (int[] move : edges) {
            int i = move[0], j = move[1];
            if (board[i][j] == '_') {
                board[i][j] = 'O';
                return;
            }
        }
    }

    // Player move
    static void playerMove(Scanner scanner) {
        int move;
        while (true) {
            System.out.print("Enter a position (1-9): ");
            move = scanner.nextInt();

            if (move < 1 || move > 9) {
                System.out.println("Invalid input! Enter a number between 1 and 9.");
                continue;
            }

            int row = (move - 1) / 3;
            int col = (move - 1) % 3;

            if (board[row][col] == '_') {
                board[row][col] = 'X';
                break;
            } else {
                System.out.println("Cell already occupied! Choose another position.");
            }
        }
    }

    // Game play
    static void playGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Tic-Tac-Toe!");
        System.out.println("1 2 3\n4 5 6\n7 8 9");
        System.out.print("Do you want to play first? (y/n): ");
        char playFirst = scanner.next().charAt(0);

        int moveCount = 0;
        if (playFirst == 'n') {
            System.out.println("Computer's move...");
            computerMove();
            display();
            moveCount++;
        }

        while (moveCount < 9) {
            playerMove(scanner);
            moveCount++;
            display();

            if (checkWin() == 'X') {
                System.out.println("You win!");
                return;
            }
            if (isBoardFull()) {
                System.out.println("It's a tie!");
                return;
            }

            System.out.println("Computer's move...");
            computerMove();
            moveCount++;
            display();

            if (checkWin() == 'O') {
                System.out.println("Computer wins!");
                return;
            }
            if (isBoardFull()) {
                System.out.println("It's a tie!");
                return;
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        playGame();
    }
}
