import java.util.*;

public class Algo {
    /** ----- PRIVATE METHODS ----- */
    /** Generates legal moves based on board matrix */
    private static tuple[] genLegalMoves(State s) {
        List<tuple> list = new ArrayList<tuple>(); //no fixed size mentioned
        
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                tuple t = new tuple(i, j);    
                /** If there is a line from t to any direction */
                if ((s.findALine(t, "up").i != 0 && s.findALine(t, "up").j != 0)
                || (s.findALine(t, "up-right").i != 0 && s.findALine(t, "up-right").j != 0)
                || (s.findALine(t, "up-left").i != 0 && s.findALine(t, "up-left").j != 0)
                || (s.findALine(t, "right").i != 0 && s.findALine(t, "right").j != 0)
                || (s.findALine(t, "left").i != 0 && s.findALine(t, "left").j != 0)
                || (s.findALine(t, "down").i != 0 && s.findALine(t, "down").j != 0)
                || (s.findALine(t, "down-right").i != 0 && s.findALine(t, "down-right").j != 0)
                || (s.findALine(t, "down-left").i != 0 && s.findALine(t, "down-left").j != 0)
                ) {
                    list.add(t);
                }
            }
        }

        tuple[] legal_moves = new tuple[list.size()];
        for (int x = 0; x < list.size(); x++) {
            legal_moves[x] = list.get(x);
        }
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