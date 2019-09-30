public class State {
    /** ----- VARIABLES ----- */
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

    /** ----- METHODS ----- */
    /** Adds the new piece to tile i,j and pass turn to next player*/
    public void changeState(int i, int j) {
        /** If board index, add new piece to i,j */
        if ((i > 0 && i < 9) && (j > 0 && j < 9)) {
            board[i][j] = Integer.toString(turn);
        }
        /** If 9,9 game is over */
        if (i == 9 && j == 9) {
            GAME_OVER = true;
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