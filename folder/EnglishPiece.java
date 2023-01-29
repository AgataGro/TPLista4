package warcaby;

import javafx.scene.paint.Color;

public class EnglishPiece extends Piece {

    /**
     * @param x one of the coordinates of the middle of the circle
     * @param y one of the coordinates of the middle of the circle
     * @param r the value of circle radius
     * @param color color of a piece - can be black or white
     * @param state state of a piece - piece can become a king
     */
    EnglishPiece(int x, int y, int r, Color color, State state) {
        super(x,y,r);
        setFill(color);
        oldX = x-35;
        oldY = y-35;
        if(color == Color.WHITE)
            moveDirection = -1;
        else
            moveDirection = 1;

        this.state = state;
    }

    @Override
    public void move(int newX, int newY) {
        oldX = newX*70;
        oldY = newY*70;
        setCenterX(oldX+35);
        setCenterY(oldY+35);
        if((this.getColor()==Color.WHITE && newY==0) || (this.getColor()==Color.BLACK && newY==7)) {
            state = state.changeState();
            setStroke(Color.GOLD);
            setStrokeWidth(5);
        }
    }
}