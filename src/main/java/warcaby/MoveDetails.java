
public class MoveDetails {
    private MoveType type;
    private Piece piece;

    /*
     * w tym konstruktorze jest przekazywany typ ruchu, jaki został wykonany
     * i pionek, który został zbity albo null, jeżeli nie było bicia
     */
    MoveDetails(MoveType type, Piece killedPiece) {
        this.type = type;
        this.piece = killedPiece;
    }

    public MoveType getMoveType() {
        return this.type;
    }

    public Piece getPiece() {
        return this.piece;
    }
}