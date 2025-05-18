import java.util.*;

class AStarRouteFinding {
    static class Node implements Comparable<Node> {
        int x, y;
        int gCost, hCost;
        Node parent;

        Node(int x, int y, int gCost, int hCost, Node parent) {
            this.x = x;
            this.y = y;
            this.gCost = gCost;
            this.hCost = hCost;
            this.parent = parent;
        }

        int fCost() {
            return gCost + hCost;
        }

        public int compareTo(Node other) {
            return this.fCost() - other.fCost();
        }
    }

    static int[][] directions = { {0, 1}, {1, 0}, {0, -1}, {-1, 0} }; // Right, Down, Left, Up

    public static void main(String[] args) {
        int[][] grid = {
            { 0, 0, 0, 0, 0 },
            { 1, 1, 0, 1, 0 },
            { 0, 0, 0, 1, 0 },
            { 0, 1, 1, 1, 0 },
            { 0, 0, 0, 0, 0 }
        };

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Start X and Y:");
        int startX = sc.nextInt();
        int startY = sc.nextInt();

        System.out.println("Enter Goal X and Y:");
        int goalX = sc.nextInt();
        int goalY = sc.nextInt();

        List<Node> path = aStar(grid, startX, startY, goalX, goalY);

        if (path != null) {
            System.out.println("Path:");
            for (Node node : path) {
                System.out.println("(" + node.x + ", " + node.y + ")");
            }
        } else {
            System.out.println("No path found.");
        }
    }

    static List<Node> aStar(int[][] grid, int startX, int startY, int goalX, int goalY) {
        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];

        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Node start = new Node(startX, startY, 0, heuristic(startX, startY, goalX, goalY), null);
        openSet.add(start);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.x == goalX && current.y == goalY) {
                return reconstructPath(current);
            }

            visited[current.x][current.y] = true;

            for (int[] dir : directions) {
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];

                if (isValid(newX, newY, rows, cols) && grid[newX][newY] == 0 && !visited[newX][newY]) {
                    int gCost = current.gCost + 1;
                    int hCost = heuristic(newX, newY, goalX, goalY);
                    Node neighbor = new Node(newX, newY, gCost, hCost, current);
                    openSet.add(neighbor);
                }
            }
        }

        return null;
    }

    static List<Node> reconstructPath(Node current) {
        List<Node> path = new ArrayList<>();
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }

    static boolean isValid(int x, int y, int rows, int cols) {
        return x >= 0 && y >= 0 && x < rows && y < cols;
    }

    static int heuristic(int x, int y, int goalX, int goalY) {
        // Manhattan distance
        return Math.abs(x - goalX) + Math.abs(y - goalY);
    }
}
