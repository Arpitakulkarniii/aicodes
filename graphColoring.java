import java.util.Scanner;

public class graphColoring {
    private int V;
    private int[][] graph;
    private int[] colors;

    public graphColoring(int[][] graph, int V) {
        this.graph = graph;
        this.V = V;
        this.colors = new int[V];
    }

    // Check if it's safe to assign color to vertex
    private boolean isSafe(int v, int c) {
        for (int i = 0; i < V; i++) {
            if (graph[v][i] == 1 && colors[i] == c) {
                return false; // Adjacent vertex has the same color
            }
        }
        return true;
    }

    // Backtracking function to solve graph coloring
    private boolean solveGraphColoring(int v, int M) {
        if (v == V) {
            printSolution();
            return true;
        }

        for (int c = 1; c <= M; c++) {
            if (isSafe(v, c)) {
                colors[v] = c;
                if (solveGraphColoring(v + 1, M)) {
                    return true;
                }
                colors[v] = 0; // Backtrack
            }
        }
        return false;
    }

    // Print the assigned colors
    private void printSolution() {
        System.out.println("Vertex Coloring:");
        for (int i = 0; i < V; i++) {
            System.out.println("Vertex " + i + " = Color " + colors[i]);
        }
    }

    // Public function to start coloring
    public void solve(int M) {
        if (!solveGraphColoring(0, M)) {
            System.out.println("Solution does not exist");
        }
    }

    // Main method with user input
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input number of vertices
        System.out.print("Enter number of vertices: ");
        int V = scanner.nextInt();

        // Input the adjacency matrix
        int[][] graph = new int[V][V];
        System.out.println("Enter the adjacency matrix (row by row):");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                graph[i][j] = scanner.nextInt();
            }
        }

        // Input number of colors
        System.out.print("Enter the number of colors: ");
        int M = scanner.nextInt();

        scanner.close();

        // Solve the problem
        graphColoring gc = new graphColoring(graph, V);
        gc.solve(M);
    }
}
