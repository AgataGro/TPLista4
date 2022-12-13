package warcaby;

public class Man1 extends Piece {

    public Man1(boolean white) {
        super(white);
    }

    @Override
    public int killablePieces() {
        return 0;
    }
}
