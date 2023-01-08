package warcaby;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Square extends Rectangle {
    
    private Piece piece = null;

    public Square(int x, int y, int w, int h, Color color) {
        super(x,y,w,h);
        setFill(color);
    }

    public boolean hasPiece() {
        if(piece==null)
            return false;
        else
            return true;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}