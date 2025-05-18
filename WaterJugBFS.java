import java.util.*;

public class WaterJugBFS {
    static class State {
        int jug1, jug2;

        State(int jug1, int jug2) {
            this.jug1 = jug1;
            this.jug2 = jug2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof State)) return false;
            State state = (State) o;
            return jug1 == state.jug1 && jug2 == state.jug2;
        }

        @Override
        public int hashCode() {
            return Objects.hash(jug1, jug2);
        }
    }

    public static void bfsWaterJug(int capacity1, int capacity2, int goal) {
        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();

        queue.add(new State(0, 0));

        while (!queue.isEmpty()) {
            State current = queue.poll();

            if (visited.contains(current)) continue;
            visited.add(current);

            System.out.println("(" + current.jug1 + ", " + current.jug2 + ")");

            // Check goal condition
            if (current.jug1 == goal || current.jug2 == goal) {
                System.out.println("Solution found!");
                return;
            }

            // Generate all possible moves
            List<State> nextMoves = new ArrayList<>();

            // Fill jug1
            nextMoves.add(new State(capacity1, current.jug2));
            // Fill jug2
            nextMoves.add(new State(current.jug1, capacity2));
            // Empty jug1
            nextMoves.add(new State(0, current.jug2));
            // Empty jug2
            nextMoves.add(new State(current.jug1, 0));
            // Pour jug1 -> jug2
            int pourToJug2 = Math.min(current.jug1, capacity2 - current.jug2);
            nextMoves.add(new State(current.jug1 - pourToJug2, current.jug2 + pourToJug2));
            // Pour jug2 -> jug1
            int pourToJug1 = Math.min(current.jug2, capacity1 - current.jug1);
            nextMoves.add(new State(current.jug1 + pourToJug1, current.jug2 - pourToJug1));

            for (State next : nextMoves) {
                if (!visited.contains(next)) {
                    queue.add(next);
                }
            }
        }

        System.out.println("No solution found.");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter capacity of jug 1: ");
        int capacity1 = sc.nextInt();

        System.out.print("Enter capacity of jug 2: ");
        int capacity2 = sc.nextInt();

        System.out.print("Enter goal amount: ");
        int goal = sc.nextInt();

        bfsWaterJug(capacity1, capacity2, goal);

        sc.close();
    }
}
