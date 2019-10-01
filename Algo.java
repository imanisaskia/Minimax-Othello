import java.util.*; 

public class Algo {

    private static tuple best_coordinate = new tuple(0, 0);   

    /** ----- PRIVATE METHODS ----- */

    private static void setBestCoordinate(int i, int j) {
      best_coordinate.i = i;
      best_coordinate.j = j;
    }

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
    public static tuple genMinimaxMove(State s, boolean isPlayer) {
      tuple legal_moves[] = genLegalMoves(s);
      int alpha = Integer.MIN_VALUE;
      int beta = Integer.MAX_VALUE;

      int score = minimax(s, 3, isPlayer, alpha, beta);

      return legal_moves[0];
    }

    private static int minimax(State curr_state, int depth, boolean isPlayer, int alpha, int beta) {
        tuple move = new tuple(0,0);
        tuple legal_moves[] = genLegalMoves(curr_state);
        int n_legal_moves = legal_moves.length;
        int best_move_score;

        if (depth == 0) { /**initiation**/
          return evaluation(curr_state, isPlayer);
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
              setBestCoordinate(move.i, move.j);
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
              setBestCoordinate(move.i, move.j);
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

    private static int evaluation(State curr_state, Boolean isPlayer) {
      tuple coordinate = new tuple(0,0);
      String checking_color;
      int score = 0;

      if (isPlayer) {
        checking_color = "0";
      }
      else {
        checking_color = "1";
      }
      
      for (int i = 1; i < 8; i++) {
        for (int j = 1; j < 8; j++) {
          coordinate.i = i;
          coordinate.j = j;
          if (curr_state.getBoard(i,j) == checking_color) { /* Cek apakah warnanya hitam/putih */
            if (is_corner(coordinate)) {
              score += 50;
            }
            else if (is_vertices(coordinate)) {
              score += 20;
            }
            else {
              score += 1;
            }
          }
        }
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

    private static boolean is_vertices(tuple coordinate) {
      if ((coordinate.i == 1)) {
        return true;
      }
      else if ((coordinate.i == 8)) {
        return true;
      }
      else if ((coordinate.j == 1)) {
        return true;
      }
      else if ((coordinate.j == 8)) {
        return true;
      }
      else {
        return false;
      }
    }

}
