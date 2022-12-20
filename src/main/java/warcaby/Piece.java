package warcaby;

public abstract class Piece {
    private boolean white = false;
    private State state;
    private int currentKillablePieces;

    public Piece(boolean white) {
        this.white = white;
    }

    public boolean isWhite() {
        return white;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setCurrentKillablePieces(int currentKillablePieces) {
        this.currentKillablePieces = currentKillablePieces;
    }

    public int getCurrentKillablePieces() {
        return currentKillablePieces;
    }
    public void doMove(Board board, String sequence){
        this.state.Move(this, board, sequence);
    }
    public abstract String jump(Board board, Square start, Direction d);
    public abstract String move(Board board, Square start, Direction d);
    public abstract void crown(Square start);
}
