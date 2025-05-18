import java.util.*;

public class AStarGridSearch {

    static class Node {
        int x, y;
        int gCost = Integer.MAX_VALUE;
        int hCost = 0;
        int fCost = 0;
        Node parent = null;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static final int ROWS = 5;
    static final int COLS = 5;

    static int[][] grid = {
        { 0, 1, 0, 0, 0 },
        { 0, 1, 0, 1, 0 },
        { 0, 0, 0, 1, 0 },
        { 1, 1, 0, 0, 0 },
        { 0, 0, 0, 0, 0 }
    };

    static final int[][] directions = {
        {1, 0}, {0, 1}, {-1, 0}, {0, -1}
    };

    static boolean isValid(int x, int y) {
        return x >= 0 && y >= 0 && x < ROWS && y < COLS;
    }

    static int heuristic(Node a, Node b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
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

    static List<Node> findPath(Node start, Node goal) {
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(n -> n.fCost));
        boolean[][] closedList = new boolean[ROWS][COLS];
        Node[][] allNodes = new Node[ROWS][COLS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                allNodes[i][j] = new Node(i, j);
            }
        }

        start = allNodes[start.x][start.y];
        goal = allNodes[goal.x][goal.y];

        start.gCost = 0;
        start.hCost = heuristic(start, goal);
        start.fCost = start.gCost + start.hCost;
        openList.add(start);

        while (!openList.isEmpty()) {
            Node current = openList.poll();

            if (current.x == goal.x && current.y == goal.y) {
                return reconstructPath(current);
            }

            closedList[current.x][current.y] = true;

            for (int[] dir : directions) {
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];

                if (!isValid(newX, newY) || grid[newX][newY] == 1 || closedList[newX][newY]) {
                    continue;
                }

                Node neighbor = allNodes[newX][newY];
                int tentativeG = current.gCost + 1;

                if (tentativeG < neighbor.gCost) {
                    neighbor.gCost = tentativeG;
                    neighbor.hCost = heuristic(neighbor, goal);
                    neighbor.fCost = neighbor.gCost + neighbor.hCost;
                    neighbor.parent = current;

                    // Only add to openList if it's not already there
                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                    }
                }
            }
        }

        return new ArrayList<>(); // No path found
    }

    public static void main(String[] args) {
        Node start = new Node(0, 0);
        Node goal = new Node(4, 4);

        List<Node> path = findPath(start, goal);

        if (!path.isEmpty()) {
            System.out.println("Path found:");
            for (Node node : path) {
                System.out.println("(" + node.x + ", " + node.y + ")");
            }
        } else {
            System.out.println("No path found.");
        }
    }
}
