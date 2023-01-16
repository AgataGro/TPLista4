package warcaby;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Square extends Rectangle implements Cloneable{
    
    private Piece piece = null;
    boolean isKilled=false;

    /**
     * @param x a x coordinate of the left top corner of a square
     * @param y a y coordinate of the left top corner of a square
     * @param w width of a square
     * @param h height of a square
     * @param color color of a square - can be brown or wheat
     */
    public Square(int x, int y, int w, int h, Color color) {
        super(x,y,w,h);
        setFill(color);
    }

    /**
     * @param killed true if we killed the piece from this square, false otherwise
     */
    public void setKilled(boolean killed) {
        isKilled = killed;
    }

    /**
     * @return true if a square is holding the square or false in opposite case
     */
    public boolean hasPiece() {
        if(piece==null)
            return false;
        else
            return true;
    }

    /**
     * @return piece which belongs to the square
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * @param piece a piece to set on this square
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    
    /**
     * @return color of this square
     */
    public Color getColor(){return (Color) getFill();}

    @Override
    public Square clone() throws CloneNotSupportedException{
        return (Square) super.clone();
    }

}
