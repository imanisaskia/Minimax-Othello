import java.util.*; 

public class Algo {
    /** ----- PRIVATE METHODS ----- */
    /** Generates legal moves based on board matrix */
    private static tuple[] genLegalMoves(State s) {
        tuple legal_moves[] = new tuple[60];
        return legal_moves;
    }

    /** ----- PUBLIC METHODS ----- */
    /** Generates random move from set of legal moves */
    public static tuple genRandomMove(State s) {
        tuple legal_moves[] = genLegalMoves(s);
        int random_index = new Random().nextInt(legal_moves.length);
        return legal_moves[random_index];
    }

    /** Generates minimax move from set of legal moves */

    private static int minimax(State curr_state, int depth, boolean isPlayer, int alpha, int beta) {
        tuple move = new tuple(0,0);
        tuple legal_moves[] = genLegalMoves(curr_state);
        int n_legal_moves = legal_moves.length;
        int best_move_score;
        tuple best_move_coordinate;

        if (depth == 0) { /**initiation**/
          return evaluation(curr_state.getBoard());
        }
        else if (isPlayer) { /** opponent's move **/
          best_move_score = Integer.MIN_VALUE;
          for (int i = 0; i < n_legal_moves; i++) {
            move = legal_moves[i];
            State new_state = new State(curr_state);
            new_state.changeState(move);
            int score = minimax(new_state, depth-1, !isPlayer, alpha, beta);
            if (score > best_move_score) {
              best_move_score = score;
              best_move_coordinate = move;
            }
            set_alpha(alpha, best_move_score); /** alpha beta pruning */
            if (beta <= alpha) {
              break; /** pruning */
            }
          }
        }
        else { /** bot's move **/
          best_move_score = Integer.MAX_VALUE;
          for (int i = 0; i < n_legal_moves; i++) {
            move = legal_moves[i];
            State new_state = new State(curr_state);
            new_state.changeState(move);
            int score = minimax(new_state, depth-1, !isPlayer, alpha, beta);
            if (score < best_move_score) {
              best_move_score = score;
              best_move_coordinate = move;
            }
            set_beta(beta, best_move_score); /** alpha beta pruning */
            if (beta <= alpha) {
              break; /** pruning */
            }
          }
        }

        return best_move_score;
    }

    private static void set_beta(int beta, int best_score) {
      if (beta > best_score) {
        beta = best_score; 
      }
    }

    private static void set_alpha(int alpha, int best_score) {
      if (alpha < best_score) {
        alpha = best_score; 
      }
    }

    /** EVALUATION **/

    private static int evaluation(State curr_state) {
      int score = 0;
      
      if(is_corner(coordinate)) {
        score += 50;
      }

      return score;
    }

    private static boolean is_corner(tuple coordinate) {
      if ((coordinate.i == 1) && (coordinate.j == 1)) {
        return true;
      }
      else if ((coordinate.i == 8) && (coordinate.j == 1)) {
        return true;
      }
      else if ((coordinate.i == 1) && (coordinate.j == 8)) {
        return true;
      }
      else if ((coordinate.i == 8) && (coordinate.j == 8)) {
        return true;
      }
      else {
        return false;
      }
    }

    private static int countFlipped(String[][] board) {
      int flipped = 0;

      return flipped;
    }


}
