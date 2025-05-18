import java.util.*;

public class WaterJugDFS {
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

    static Set<State> visited = new HashSet<>();
    static List<State> solutionPath = new ArrayList<>();
    static int capacity1, capacity2, goal;

    public static boolean dfsWaterJug(State current) {
        if (visited.contains(current)) return false;
        visited.add(current);
        solutionPath.add(current);

        // Check goal condition
        if (current.jug1 == goal || current.jug2 == goal) {
            return true;
        }

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

        // Shuffle to explore moves in random order
        Collections.shuffle(nextMoves);

        for (State next : nextMoves) {
            if (dfsWaterJug(next)) return true;
        }

        // Backtrack
        solutionPath.remove(solutionPath.size() - 1);
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter capacity of jug 1: ");
        capacity1 = sc.nextInt();

        System.out.print("Enter capacity of jug 2: ");
        capacity2 = sc.nextInt();

        System.out.print("Enter goal amount: ");
        goal = sc.nextInt();

        State initial = new State(0, 0);

        if (dfsWaterJug(initial)) {
            System.out.println("Solution found! Path:");
            for (State s : solutionPath) {
                System.out.println("(" + s.jug1 + ", " + s.jug2 + ")");
            }
        } else {
            System.out.println("No solution found.");
        }

        sc.close();
    }
}
