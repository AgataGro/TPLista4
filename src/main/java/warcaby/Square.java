package warcaby;

public class Square {
    private Piece piece;
    private int x;
    private int y;
    private boolean usable;

    public Square(int x, int y, Piece piece, boolean usable)
    {
        this.setPiece(piece);
        this.setX(x);
        this.setY(y);
        this.usable=usable;
    }

    public boolean isUsable() {
        return usable;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
