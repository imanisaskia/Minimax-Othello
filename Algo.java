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
    public static tuple genMinimaxMove(String[][] board, boolean isPlayer) {
        tuple move = new tuple(0,0);
        return move;
    }
}