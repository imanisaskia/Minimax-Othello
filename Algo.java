import java.util.*; 

public class Algo {

    /** ----- PRIVATE METHODS ----- */

    /** ----- PUBLIC METHODS ----- */
    /** Generates legal moves based on board matrix */
    public static tuple[] genLegalMoves(State s) {
        List<tuple> list = new ArrayList<tuple>(); //no fixed size mentioned
        //System.out.println(Integer.toString(s.getTurn()));
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                if (s.getBoardIJ(i, j).equals("-")) {
                    tuple t = new tuple(i, j);

                    if (s.isConnected(t)) {
                        tuple up_end = s.findALine(t, "up");
                        tuple upright_end = s.findALine(t, "up-right");
                        tuple upleft_end = s.findALine(t, "up-left");
                        tuple right_end = s.findALine(t, "right");
                        tuple left_end = s.findALine(t, "left");
                        tuple down_end = s.findALine(t, "down");
                        tuple downright_end = s.findALine(t, "down-right");
                        tuple downleft_end = s.findALine(t, "down-left");

                        //System.out.println("(" + Integer.toString(t.i) + "," + Integer.toString(t.j) +") is connected");
                        /** If there is a line from t to any direction, but not directly next to t */
                        if (((up_end.i != 0 && up_end.j != 0) && (up_end.i - t.i != 1 && up_end.j - t.j != 1 && up_end.i - t.i != -1 && up_end.j - t.j != -1))
                        || ((upright_end.i != 0 && upright_end.j != 0) && (upright_end.i - t.i != 1 && upright_end.j - t.j != 1 && upright_end.i - t.i != -1 && upright_end.j - t.j != -1))
                        || ((upleft_end.i != 0 && upleft_end.j != 0) && (upleft_end.i - t.i != 1 && upleft_end.j - t.j != 1 && upleft_end.i - t.i != -1 && upleft_end.j - t.j != -1))
                        || ((right_end.i != 0 && right_end.j != 0) && (right_end.i - t.i != 1 && right_end.j - t.j != 1 && right_end.i - t.i != -1 && right_end.j - t.j != -1))
                        || ((left_end.i != 0 && left_end.j != 0) && (left_end.i - t.i != 1 && left_end.j - t.j != 1 && left_end.i - t.i != -1 && left_end.j - t.j != -1))
                        || ((down_end.i != 0 && down_end.j != 0) && (down_end.i - t.i != 1 && down_end.j - t.j != 1 && down_end.i - t.i != -1 && down_end.j - t.j != -1))
                        || ((downright_end.i != 0 && downright_end.j != 0) && (downright_end.i - t.i != 1 && downright_end.j - t.j != 1 && downright_end.i - t.i != -1 && downright_end.j - t.j != -1))
                        || ((downleft_end.i != 0 && downleft_end.j != 0) && (downleft_end.i - t.i != 1 && downleft_end.j - t.j != 1 && downleft_end.i - t.i != -1 && downleft_end.j - t.j != -1))
                        ) {
                            //System.out.println("Added (" + Integer.toString(t.i) + "," + Integer.toString(t.j) +")");
                            //System.console().readLine();
                            list.add(t);
                        }
                    } else {
                        //System.out.println("(" + Integer.toString(t.i) + "," + Integer.toString(t.j) +") is not connected");
                    }
                }
            }
        }

        tuple[] legal_moves = new tuple[list.size()];
        for (int x = 0; x < list.size(); x++) {
            legal_moves[x] = list.get(x);
        }
        return legal_moves;
    }

    /** Generates random move from set of legal moves */
    public static tuple genRandomMove(State s) {
        tuple legal_moves[] = genLegalMoves(s);
        if (legal_moves.length > 0) {
            int random_index = new Random().nextInt(legal_moves.length);
            return legal_moves[random_index];
        } else {
            tuple over = new tuple(9,9);
            return over;
        }
    }

    /** Generates minimax move from set of legal moves */
    public static tuple genMinimaxMove(State s, boolean isPlayer) {
      tuple legal_moves[] = genLegalMoves(s);
      
      if (legal_moves.length == 0) {
        tuple hasil = new tuple(9,9);
        return hasil;
      }

      int alpha = Integer.MIN_VALUE;
      int beta = Integer.MAX_VALUE;
      // System.out.println(" ");  
      // System.out.println("Skor max : " + minimax(s, 3, isPlayer, alpha, beta).j);
      return legal_moves[minimax(s, 5, isPlayer, alpha, beta).i];
    }

    // private static void printBoard(String[][] board) {
    //   for (int i = 1; i <= 8; i++){
    //     for (int j = 1; j <= 8; j++){
    //       //System.out.println(Integer.toString(i) + " " + Integer.toString(j) + " " + state[i][j]);
    //       System.out.print(board[i][j] + " ");
    //     }
    //     System.out.println();
    //   }
    // }

    private static tuple minimax(State curr_state, int depth, boolean isPlayer, int alpha, int beta) {
        tuple move = new tuple(0,0);
        tuple hasil = new tuple(0,0);
        // System.out.println("-----------------------------------------------");
        // System.out.println("panjang legal move = " + n_legal_moves);
        //System.out.println(Integer.toString(move.i) + "," + Integer.toString(move.j));

        if (depth == 0) { /**initiation**/
          hasil.j = evaluation(curr_state, isPlayer);
          return hasil;
        }
        
        // if (n_legal_moves == 0) {
        //   if (isPlayer) {
        //     hasil.j = Integer.MIN_VALUE;
        //     return hasil;
        //   }
        //   else {
        //     hasil.j = Integer.MAX_VALUE;
        //     return hasil;
        //   }
        // }

        if (isPlayer) { /** opponent's move **/
          hasil.j = Integer.MIN_VALUE;
          tuple legal_moves[] = genLegalMoves(curr_state);
          int n_legal_moves = legal_moves.length;
          for (int i = 0; i < n_legal_moves; i++) {
            move = legal_moves[i];
            State new_state = new State(curr_state);
            // System.out.println("NEW STATE BOARD ======>>>");
            // printBoard(new_state.getBoard());
            new_state.changeState(move);
            // System.out.println("NEW STATE BOARD CHANGED ======>>>");
            // printBoard(new_state.getBoard());
            int score = minimax(new_state, depth-1, !isPlayer, alpha, beta).j;
            if (score > hasil.j) {
              hasil.j = score;
              hasil.i = i; 
            }
            // System.out.println("best_move_score = " + best_move_score);
            set_alpha(alpha, hasil.j); /** alpha beta pruning */
            // System.out.println("Player : " + Integer.toString(move.i) + "," + Integer.toString(move.j));
            if (beta <= alpha) {
              break; /** pruning */
            }
          }
        }
        else if (!isPlayer) { /** bot's move **/
          hasil.j = Integer.MAX_VALUE;
          tuple legal_moves[] = genLegalMoves(curr_state);
          int n_legal_moves = legal_moves.length;
          for (int i = 0; i < n_legal_moves; i++) {
            move = legal_moves[i];
            State new_state = new State(curr_state);
            new_state.changeState(move);
            int score = minimax(new_state, depth-1, !isPlayer, alpha, beta).j;
            if (score < hasil.j) {
              hasil.j = score;
              hasil.i = i;         
            }
            // System.out.println("best_move_score = " + best_move_score);
            set_beta(beta, hasil.j); /** alpha beta pruning */
            // System.out.println("Bot : " + Integer.toString(move.i) + "," + Integer.toString(move.j));
            if (beta <= alpha) {
              break; /** pruning */
            }
          }
        }
        return hasil;
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
      
      for (int i = 1; i <= 8; i++) {
        for (int j = 1; j <= 8; j++) {
          coordinate.i = i;
          coordinate.j = j;
          if (curr_state.getBoardIJ(i,j).equals(checking_color)) {  /* Cek apakah warnanya hitam/putih */
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
