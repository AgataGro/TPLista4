package warcaby;

public class Piece1 extends Piece {

    public Piece1(boolean white) {
        super(white);
        this.setState(new Man1State());
    }

    @Override
    public void setCurrentKillablePieces(int currentKillablePieces) {

    }
}
