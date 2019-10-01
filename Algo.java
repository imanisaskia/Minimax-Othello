import java.util.Random;

public class Algo {
    /** ----- PRIVATE METHODS ----- */
    /** Generates legal moves based on board matrix */
    private static tuple[] genLegalMoves(String[][] board) {
        tuple legal_moves[] = new tuple[60];
        return legal_moves;
    }

    /** ----- PUBLIC METHODS ----- */
    /** Generates random move from set of legal moves */
    public static tuple genRandomMove(String[][] board) {
        tuple legal_moves[] = genLegalMoves(board);
        int random_index = new Random().nextInt(legal_moves.length);
        return legal_moves[random_index];
    }

    /** Generates minimax move from set of legal moves */

    public static int minimax(State curr_state, int depth, boolean isPlayer, int alpha, int beta) {
        tuple move = new tuple(0,0);
        tuple legal_moves[] = genLegalMoves(board);
        int best_move;

        if (depth == 0) { /**initiation**/
          return evaluation(board);
        }
        else if (isPlayer) { /**initiation**/
          best_move = Integer.MIN_VALUE;
          for (int i = 0; i < length(legal_moves); i++) {
            move = legal_moves[i];
            State new_state = new State(curr_state);
            new_state.changeState(move);
            int score = minimax(new_state, depth-1, !isPlayer, alpha, beta)

        }
        else {
          best_move = Integer.MIN_VALUE;

        }

        return best_move;
    }

    /** EVALUATION **/

    private static int evaluation(String[][] board) {
      int best_move;
      int corner_score = 10;
      int vertices_score = 5;



      return;
    }

    private static int score_corner(String[][] board) {
      return 50;
    }

    private static int score_vertices(String[][] board) {
      return 20;
    }

    private static int countFlipped(String[][] board) {
      int flipped = 0;

      return flipped;
    }


}
