import java.util.*;

public class MissionaryCannibalBFS {

    static class State {
        int mLeft, cLeft, boat; // boat=0 means left, 1 means right
        State parent;

        State(int mLeft, int cLeft, int boat, State parent) {
            this.mLeft = mLeft;
            this.cLeft = cLeft;
            this.boat = boat;
            this.parent = parent;
        }

        boolean isGoal() {
            return mLeft == 0 && cLeft == 0 && boat == 1;
        }

        boolean isValid() {
            if (mLeft < 0 || cLeft < 0 || mLeft > 3 || cLeft > 3) return false;
            int mRight = 3 - mLeft;
            int cRight = 3 - cLeft;
            if ((mLeft > 0 && mLeft < cLeft) || (mRight > 0 && mRight < cRight)) return false;
            return true;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof State)) return false;
            State s = (State) o;
            return s.mLeft == mLeft && s.cLeft == cLeft && s.boat == boat;
        }

        @Override
        public int hashCode() {
            return mLeft * 31 + cLeft * 17 + boat;
        }
    }

    public static List<State> bfs() {
        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();

        State start = new State(3, 3, 0, null);
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            State curr = queue.poll();

            if (curr.isGoal()) {
                return constructPath(curr);
            }

            for (State next : getNextStates(curr)) {
                if (next.isValid() && !visited.contains(next)) {
                    queue.add(next);
                    visited.add(next);
                }
            }
        }
        return null;
    }

    static List<State> getNextStates(State curr) {
        List<State> nextStates = new ArrayList<>();
        int dir = curr.boat == 0 ? -1 : 1; // boat moves left->right (-1) or right->left (1)

        int[][] moves = {
            {1, 0}, {2, 0}, {0, 1}, {0, 2}, {1, 1}
        };

        for (int[] move : moves) {
            int m = curr.mLeft + move[0] * dir;
            int c = curr.cLeft + move[1] * dir;
            State next = new State(m, c, 1 - curr.boat, curr);
            if (next.isValid()) nextStates.add(next);
        }

        return nextStates;
    }

    static List<State> constructPath(State state) {
        List<State> path = new ArrayList<>();
        while (state != null) {
            path.add(state);
            state = state.parent;
        }
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        List<State> solution = bfs();

        if (solution != null) {
            System.out.println("Solution found in " + (solution.size() - 1) + " moves:");
            for (State s : solution) {
                System.out.printf("Left Bank -> M: %d, C: %d | Boat on %s\n",
                        s.mLeft, s.cLeft, s.boat == 0 ? "Left" : "Right");
            }
        } else {
            System.out.println("No solution found.");
        }
    }
}
