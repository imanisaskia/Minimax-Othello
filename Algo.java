import java.util.Random;

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
    public static tuple genMinimaxMove(State s, boolean isPlayer) {
        tuple move = new tuple(0,0);
        return move;
    }
}