public class State {
    /** ----- VARIABLES ----- */
    private boolean SKIPPED_ONCE;
    private boolean GAME_OVER;                      /** game state */
    private int turn;                               /** 1 = white, 0 = black */
    private String board[][] = new String[9][9];
    /** matrix of 8x8 board, index from (1,1) 
    *    "1" = white pieces
    *    "0" = black pieces
    *    "-" = empty tile
    */
    
    /** ----- CONSTRUCTOR ----- */
    public State() {
        GAME_OVER = false;
        turn = 0;
        /** All tiles are blank */
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                board[i][j] = "-";
            }
        }
        /** Insert black and white tiles in middle */
        board[4][4] = "0";
        board[5][5] = "0";
        board[4][5] = "1";
        board[5][4] = "1";
    }

    public State(State s) {
        GAME_OVER = s.GAME_OVER;
        turn = s.turn;
        board = s.board;
    }

    /** ----- GETTERS ----- */
    
    public boolean isGameOver() {
        return GAME_OVER;
    }

    public int getTurn() {
        return turn;
    }

    public String[][] getBoard() {
        return board;
    }

    public String getBoardIJ(int i, int j) {
        return board[i][j];
    }

    /** ----- METHODS ----- */
    /** Returns true if there is a piece next to the tuple */
    public boolean isConnected(tuple t) {
        boolean i_min, i_plus, j_min, j_plus = false;

        if (t.i-1 != 0) {
            i_min = true;
        } else {
            i_min = false;
        }
        if (t.i+1 != 9) {
            i_plus = true;
        } else {
            i_plus = false;
        }
        if (t.j-1 != 0) {
            j_min = true;
        } else {
            j_min = false;
        }
        if (t.j+1 != 9) {
            j_plus = true;
        } else {
            j_plus = false;
        }

        if (i_min) {
            if (!board[t.i-1][t.j].equals("-")) {
                return true;
            }
            if (j_plus) {
                if (!board[t.i-1][t.j+1].equals("-")) {
                    return true;
                }
            }
            if (j_min) {
                if (!board[t.i-1][t.j-1].equals("-")) {
                    return true;
                }
            }
        }
        
        if (i_plus) {
            if (!board[t.i+1][t.j].equals("-")) {
                return true;
            }
            if (j_plus) {
                if (!board[t.i+1][t.j+1].equals("-")) {
                    return true;
                }
            }
            if (j_min) {
                if (!board[t.i+1][t.j-1].equals("-")) {
                    return true;
                }
            }
        }

        if (j_plus) {
            if (!board[t.i][t.j+1].equals("-")) {
                return true;
            }
        }
        if (j_min) {
            if (!board[t.i][t.j-1].equals("-")) {
                return true;
            }
        }

        return false;

    }

    /** Finds piece's complement from a tuple to a certain direction; returns 0,0 if none */
    public tuple findALine(tuple t, String direction) {
        int inc_i, inc_j;

        System.out.println("Find a line from " + Integer.toString(t.i) + Integer.toString(t.j) + " direction " + direction);

        if (direction == "up" || direction == "up-right" || direction == "up-left") {
            inc_i = -1;
        } else if (direction == "down" || direction == "down-right" || direction == "down-left") {
            inc_i = 1;
        } else {
            inc_i = 0;
        }

        if (direction == "right" || direction == "up-right" || direction == "down-right") {
            inc_j = 1;
        } else if (direction == "left" || direction == "up-left" || direction == "down-left") {
            inc_j = -1;
        } else {
            inc_j = 0;
        }

        tuple result = new tuple(0,0);
        int i = t.i + inc_i;
        int j = t.j + inc_j;
        //System.out.println(Integer.toString(turn));
        while ((i > 0 && i < 9) && (j > 0 && j < 9)) {
            if (board[i][j].equals(Integer.toString(turn))) {
                //System.out.println("match");
                result.i = i;
                result.j = j;
                break;
            } else if (board[i][j].equals("-")) {
                break;
            } else {
                //System.out.println("Expected " + Integer.toString(turn) + " but get " + board[i][j]);
                i += inc_i;
                j += inc_j;
            }
        }

        //System.console().readLine();
        System.out.println("Result: (" + Integer.toString(result.i) + "," + Integer.toString(result.j) + ")");
        return result;
    }
    
    private void flipPieces(tuple from, tuple to) {
        int inc_i, inc_j;

        //System.out.println("Flipping from (" + Integer.toString(from.i) + "," + Integer.toString(from.j) + ") to (" + Integer.toString(to.i) + "," + Integer.toString(to.j) + ")");

        if (from.i > to.i) {
            inc_i = -1;
        } else if (from.i < to.i) {
            inc_i = 1;
        } else {
            inc_i = 0;
        }

        if (from.j > to.j) {
            inc_j = -1;
        } else if (from.j < to.j) {
            inc_j = 1;
        } else {
            inc_j = 0;
        }

        int i = from.i + inc_i;
        int j = from.j + inc_j;
        while (i != to.i || j != to.j) {
            //System.out.println("(" + Integer.toString(i) + "," + Integer.toString(j) + ")");

            if (board[i][j].equals("0")) {
                //System.out.println("Flipped to 1");
                board[i][j] = "1";
            } else {
                //System.out.println("Flipped to 0");
                board[i][j] = "0";
            }
            i += inc_i;
            j += inc_j;
        }
    }
    
    /** Adds the new piece to tile i,j and pass turn to next player*/
    public void changeState(tuple t) {
        /** If board index, add new piece to i,j */
        if ((t.i > 0 && t.i < 9) && (t.j > 0 && t.j < 9)) {
            board[t.i][t.j] = Integer.toString(turn);

            if (SKIPPED_ONCE) {
                SKIPPED_ONCE = false;
            }

            tuple up_end = findALine(t, "up");
            if (up_end.i != 0 && up_end.j != 0) {
                flipPieces(t, up_end);
            }
            
            tuple upright_end = findALine(t, "up-right");
            if (upright_end.i != 0 && upright_end.j != 0) {
                flipPieces(t, upright_end);
            }

            tuple upleft_end = findALine(t, "up-left");
            if (upleft_end.i != 0 && upleft_end.j != 0) {
                flipPieces(t, upleft_end);
            }

            tuple right_end = findALine(t, "right");
            if (right_end.i != 0 && right_end.j != 0) {
                flipPieces(t, right_end);
            }

            tuple left_end = findALine(t, "left");
            if (left_end.i != 0 && left_end.j != 0) {
                flipPieces(t, left_end);
            }

            tuple down_end = findALine(t, "down");
            if (down_end.i != 0 && down_end.j != 0) {
                flipPieces(t, down_end);
            }

            tuple downright_end = findALine(t, "down-right");
            if (downright_end.i != 0 && downright_end.j != 0) {
                flipPieces(t, downright_end);
            }

            tuple downleft_end = findALine(t, "down-left");
            if (downleft_end.i != 0 && downleft_end.j != 0) {
                flipPieces(t, downleft_end);
            }
        } else {
            if (!SKIPPED_ONCE) {
                SKIPPED_ONCE = true;
            } else {
                GAME_OVER = true;
            }
        }

        /** Pass turn to next player */
        if (turn == 0) {
            turn = 1;
        } else {
            turn = 0;
        }
    }

    /** ----- DEBUGGING FUNCTIONS ----- */
    public void setGameOver() {
        GAME_OVER = true;
    }
    
    public void setTurn(int t) {
        turn = t;
    }

    public void setBoardIJ(int i, int j, String value) {
        board[i][j] = value;
    }

    public void setBoard(String[][] newBoard) {
        board = newBoard;
    }

}